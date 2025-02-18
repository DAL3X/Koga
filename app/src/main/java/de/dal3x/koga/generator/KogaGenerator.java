package de.dal3x.koga.generator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.menu.constants.Carbohydrate;
import de.dal3x.koga.menu.room.MenuRepository;
import de.dal3x.koga.options.Options;
import de.dal3x.koga.options.OptionsRepository;

/** @noinspection DataFlowIssue*/
public class KogaGenerator implements LifecycleOwner {

    // Max number of tries before no possibly selection is assumed
    private final int numberMaxTries = 1000;

    private final LifecycleRegistry lifecycleRegistry;
    private final OptionsRepository optionsRepository;
    private final MutableLiveData<LinkedList<Menu>> selection;
    private final Random rand;
    private final Map<String, Integer> duplicates;
    private final AtomicInteger numMeat;
    private final AtomicInteger selectedHealthSum;
    private final Queue<Integer> lastEdited;
    private final Map<Carbohydrate, Integer> carbohydrates;
    private List<Menu> allMenus;

    public KogaGenerator(Context context) {
        rand = new Random();
        duplicates = new HashMap<>();
        lastEdited = new LinkedList<>();
        numMeat = new AtomicInteger(0);
        selectedHealthSum = new AtomicInteger(0);
        carbohydrates = new HashMap<>();
        selection = new MutableLiveData<>();
        selection.postValue(new LinkedList<>());
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        optionsRepository = new OptionsRepository(context);
        MenuRepository menuRepository = new MenuRepository(context);
        LiveData<List<Menu>> allMenusLive = menuRepository.getAllMenus();
        allMenusLive.observe(this, menus -> {
            if (!menus.isEmpty()) {
                allMenus = menus;
                allMenusLive.removeObservers(this);
                generateMenus();
            }
        });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public LiveData<LinkedList<Menu>> getSelection () {
        return selection;
    }

    public void reRollMenu(int position) {
        Observer<LinkedList<Menu>> oneTimeSelectionObserver = selectList -> {
            Menu toRemove = selectList.get(position);
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
            selection.removeObservers(this);
            lastEdited.add(position);
            selectList.remove(position);
            selection.postValue(selectList);
            generateMenus();
        };
        selection.observe(this, oneTimeSelectionObserver);
    }

    private void generateMenus() {
        Options options = optionsRepository.getOptions();
        Observer<LinkedList<Menu>> oneTimeSelectionObserver = selectList -> {
            int counter = 0;
            while(selectList.size() < options.getNumberDays() && counter < numberMaxTries) {
                Menu menu = generateAndCountOneValidMenu(options, allMenus, selectList.size());
                if (menu != null) {
                    if (lastEdited.isEmpty()) {
                        lastEdited.add(selectList.size()); // just append if no position needs an update
                    }
                    selectList.add(lastEdited.remove(), menu);
                }
                else {
                    counter++;
                    selectList.clear(); // try again
                }
            }
            selection.removeObservers(this);
            selection.postValue(selectList);
        };
        selection.observe(this, oneTimeSelectionObserver);
    }

    private Menu generateAndCountOneValidMenu(Options options, List<Menu> menus, int selectedSize) {
        Menu select = selectRandomMenuWithContraints(menus, options, selectedSize);
        if (select == null) {
            return null;
        }
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

    private Menu selectRandomMenuWithContraints(List<Menu> menus, Options options, int selectedSize) {
        List<Menu> menuCopy = new LinkedList<>(menus);
        int counter = 0;
        while (!menuCopy.isEmpty()) {
            int position = rand.nextInt(menuCopy.size()); // Round 1 of selections: equals chances
            Menu select = menuCopy.get(position);
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
            int maxRating = 5;
            int random = rand.nextInt(maxRating);
            // Round 2 of selections: x likeness gets always taken in x/5 of cases, but at least every 5-th
            if ((counter == maxRating )|| select.getLikeness() + random >= maxRating) {
                return select;
            }
            else {
                counter++;
            }
        }
        return null; // No menu can possible be added bc of at least 1 constraint
    }

}
