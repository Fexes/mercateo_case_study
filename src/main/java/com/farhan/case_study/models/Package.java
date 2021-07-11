package com.farhan.case_study.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model class for Entire Package.
 * @author Farhan Fida
 */
public class Package {

    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * @return a String for the package item separated by ','.
     * If package is empty returns '-'.
     */
    public String output() {
        if (items.isEmpty()) {
            return "-";
        }
        String result="";
        for(int i=0;i<=items.size()-1;i++){

            if(result.isEmpty()) {
                result=result+items.get(i).getIndex();
            }else{
                result=result+","+items.get(i).getIndex();
            }
        }
        return result;

    }

}
