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

    // A static active instance is needed for the reRoll button to be able call reRollMenu()
    private static KogaGenerator activeInstance;
    public static KogaGenerator getActiveInstance() {
        return activeInstance;
    }

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
            if (!menus.isEmpty()) {
                allMenus = menus;
                generateMenus(parentActivity);
            }
        });
        activeInstance = this;
    }

    public LiveData<List<Menu>> getSelection () {
        return selection;
    }

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
            allMenus.add(toRemove);
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
                else {
                    break; // No more valid menus
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
            carbohydrates.put(carbo, carbohydrates.getOrDefault(carbo, 0) + 1);
        }
        // Count duplicates
        duplicates.put(select.getName(), duplicates.getOrDefault(select.getName(), 0) + 1);
        return select;
    }

    // Returns a pair of the selected menu and its position in the given menus list
    private Pair<Menu, Integer> selectRandomMenuWithContraints(List<Menu> menus, Options options, int selectedSize) {
        List<Menu> menuCopy = new LinkedList<>(menus);
        while (!menuCopy.isEmpty()) {
            int position = rand.nextInt(menuCopy.size()); // Round 1 of selections: equals chances
            Menu select = menuCopy.get(position);
            // Now check eligibility of selected menu
            // Check for veggie eligibility
            if (!select.isVeggie()) {
                if (numMeat.get() + 1 > options.getNumberMeat()) {
                    menuCopy.remove(position);
                    continue;
                }
            }
            // Check for carbohydrate eligibility
            if ((carbohydrates.getOrDefault(select.getCarbohydrate(), 0) + 1 > options.getMaxCarbDuplicates()) && select.getCarbohydrate() != Carbohydrate.NONE) {
                menuCopy.remove(position);
                continue;
            }
            // Check for health score eligibility
            double sum = selectedHealthSum.get() + select.getHealthScore().getRating();
            double numberEntries = selectedSize + 1.0;
            double average = sum / numberEntries;
            if (average < options.getMinHealthScore()) {
                menuCopy.remove(position);
                continue;
            }
            // Check for duplicate eligibility
            if (options.getMaxDuplicate() < (duplicates.getOrDefault(select.getName(), 0) + 1)) {
                menuCopy.remove(position);
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
