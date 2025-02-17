package de.dal3x.koga.generator;

import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.menu.constants.Carbohydrate;
import de.dal3x.koga.menu.room.MenuRepository;
import de.dal3x.koga.options.Options;
import de.dal3x.koga.options.OptionsRepository;

/** @noinspection DataFlowIssue*/
public class KogaGenerator {

    private final OptionsRepository optionsRepository;
    private final MutableLiveData<List<Menu>> selection;
    private final Random rand;
    private final Map<String, Integer> duplicates;
    private final AtomicInteger numMeat;
    private final AtomicInteger selectedHealthSum;
    private final Map<Carbohydrate, Integer> carbohydrates;
    private List<Menu> allMenus;

    public KogaGenerator(AppCompatActivity parentActivity) {
        rand = new Random();
        duplicates = new HashMap<>();
        numMeat = new AtomicInteger();
        selectedHealthSum = new AtomicInteger();
        carbohydrates = new HashMap<>();
        selection = new MutableLiveData<>();
        optionsRepository = new OptionsRepository(parentActivity.getApplicationContext());
        MenuRepository menuRepository = new MenuRepository(parentActivity.getApplicationContext());
        LiveData<List<Menu>> allMenusLive = menuRepository.getAllMenus();
        allMenusLive.observe(parentActivity, menus -> {
            allMenus = menus;
            generateMenus(parentActivity);
        });
    }

    public LiveData<List<Menu>> getSelection () {
        return selection;
    }

    // Must not be called before allMenus is set.
    public void reRollMenu(AppCompatActivity parentActivity, int position) {
        selection.observe(parentActivity, selectList -> {
            Menu toRemove = selectList.get(position);
            // remove from list, health sum, carbohydrates, duplicates and veggie
            selectList.remove(position);
            selectedHealthSum.getAndSet(selectedHealthSum.get() - toRemove.getHealthScore().getRating());
            if (toRemove.getCarbohydrate() != Carbohydrate.NONE) {
                carbohydrates.remove(toRemove.getCarbohydrate());
            }
            int duplicate = duplicates.getOrDefault(toRemove.getName(), 1);
            if (duplicate == 1) {
                duplicates.remove(toRemove.getName());
            }
            else {
                duplicates.put(toRemove.getName(), duplicate - 1);
            }
            if (!toRemove.isVeggie()) {
                numMeat.getAndDecrement();
            }
            selection.postValue(selectList);
            generateMenus(parentActivity);
        });
    }

    private void generateMenus(AppCompatActivity parentActivity) {
        Options options = optionsRepository.getOptions();
        selection.postValue(new LinkedList<>());
        selection.observe(parentActivity, selectList -> {
            while(selectList.size() < options.getNumberDays()) {
                Menu menu = generateAndCountOneValidMenu(options, allMenus, selectList.size());
                if (menu != null) {
                    selectList.add(menu);
                }
            }
            selection.postValue(selectList);
        });
    }

    private Menu generateAndCountOneValidMenu(Options options, List<Menu> menus, int selectedSize) {
        Pair<Menu, Integer> selectPair = selectRandomMenuWithContraints(menus, options, selectedSize);
        if (selectPair == null) {
            return null;
        }
        Menu select = selectPair.first;
        selectedHealthSum.getAndAdd(select.getHealthScore().getRating());
        // Count meat
        if (!select.isVeggie()) {
            numMeat.getAndIncrement();
        }
        // Count carbohydrates
        Carbohydrate carbo = select.getCarbohydrate();
        if (carbo != Carbohydrate.NONE) {
            carbohydrates.put(carbo, carbohydrates.getOrDefault(carbo, 1));
        }
        // Count duplicates
        duplicates.put(select.getName(), duplicates.getOrDefault(select.getName(), 0) + 1);
        // Then check if duplicate number allows for more duplicates
        if (duplicates.get(select.getName()) >= options.getMaxDuplicate()) {
            menus.remove(selectPair.second.intValue());
        }
        return select;
    }


    // Returns a pair of the selected menu and its position in the given menus list
    private Pair<Menu, Integer> selectRandomMenuWithContraints(List<Menu> menus, Options options, int selectedSize) {
        while (!menus.isEmpty()) {
            int position = rand.nextInt(menus.size()); // Round 1 of selections: equals chances
            Menu select = menus.get(position);
            // Now check eligibility of selected menu
            // Check for veggie eligibility
            if (!select.isVeggie()) {
                if (numMeat.get() + 1 > options.getNumberMeat()) {
                    menus.remove(position);
                    continue;
                }
            }
            // Check for carbohydrate eligibility
            if (carbohydrates.getOrDefault(select.getCarbohydrate(), 0) + 1 >= options.getMaxCarbDuplicates()) {
                menus.remove(position);
                continue;
            }
            // Check for health score eligibility
            double average = (double) (selectedHealthSum.get() + select.getHealthScore().getRating()) / (double) (selectedSize + 1);
            if (average < options.getMinHealthScore()) {
                menus.remove(position);
                continue;
            }
            // All checks completed
            int random = rand.nextInt(5); // 5 is max ranking
            if (select.getLikeness() + random >= 5) { // Round 2 of selections: x likeness gets always taken in x/5 of cases
                return new Pair<>(select, position);
            }
        }
        // No menu can possible be added bc of at least 1 constraint
        return null;
    }

}
