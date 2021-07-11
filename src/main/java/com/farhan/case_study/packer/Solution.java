package com.farhan.case_study.packer;

import com.farhan.case_study.models.Item;
import com.farhan.case_study.models.Package;

import java.util.Comparator;
import java.util.List;

/**
 * Solution of the given Scenario
 * @author Farhan Fida
 */
public class Solution {


    private List<Item> items;
    private int capacity;

    /**
     * @param items list of available items.
     * @param capacity maximum capacity of knapsack.
     */
    public Solution(List<Item> items, int capacity) {
        this.items = items;
        this.capacity = capacity;
    }


    public List<Item> getItems() {
        return items;
    }

    public double getCapacity() {
        return capacity;
    }

    /**
     * This method solves the Scenario
     * @return a {@link Package} that have maximum cost.
     */
    public Package solve() {
        int pFactor = precisionFactor();
        this.capacity = capacity * pFactor;
        int count = items.size();
        items.sort(Comparator.comparing(Item::getWeight));
        int[][] optimal = new int[count + 1][capacity + 1];
        for (int i = 0; i <= capacity; i++) {
            optimal[0][i] = 0;
        }
        for (int i = 1; i <= count; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (items.get(i - 1).getWeight() * pFactor > j) {
                    optimal[i][j] = optimal[i - 1][j];
                } else {
                    optimal[i][j] = Math.max(
                          optimal[i - 1][j],
                          optimal[i - 1][j - (int) (items.get(i - 1).getWeight() * pFactor)]
                                + items.get(i - 1).getCost());
                }
            }
        }
        int cost = optimal[count][capacity];
        int weight = capacity;
        Package output = new Package();
        for (int i = count; i > 0 && cost > 0; i--) {
            if (cost != optimal[i - 1][weight]) {
                output.addItem(items.get(i - 1));
                weight -= items.get(i - 1).getWeight() * pFactor;
                cost -= items.get(i - 1).getCost();
            }
        }
        return output;
    }

    /**
     * @return Find the maximum decimal place of items weights (say max) and returns 10^max.
     */
    private int precisionFactor() {
        int max = items.stream()
              .map(Item::getWeight)
              .mapToInt(w -> String.valueOf(w).split("\\.").length)
              .max().orElse(0);
        return (int) Math.pow(10, max);
    }

}
