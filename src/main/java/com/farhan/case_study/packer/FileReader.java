package com.farhan.case_study.packer;

import com.farhan.case_study.packer.exception.ValidationException;
import com.farhan.case_study.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@link FileReader} is for reading data from input file.
 * @author Farhan Fida
 */
public class FileReader {

    private List<Validation<Solution>> scenarioValidations;
    private List<Validation<Item>> itemValidations;

    /**
     * Pattern Matching with Regular Expressions
     */
    private Pattern linePattern = Pattern.compile("^(-?\\d+) : (.+)");
    private Pattern fieldsPattern = Pattern.compile("\\((-?\\d+),(-?\\d+\\.?\\d*?),€(-?\\d+)\\)");

    public FileReader(List<Validation<Solution>> scenarioValidations, List<Validation<Item>> itemValidations) {
        this.scenarioValidations = scenarioValidations;
        this.itemValidations = itemValidations;
    }

    /**
     * @param line input line.
     * @return the corresponding {@link Solution}
     * @throws ValidationException if the line doesn't match te provided pattern or contains an invalid input
     */
    public Solution parse(String line) throws ValidationException {
        Matcher lineMatcher = linePattern.matcher(line);
        if (!lineMatcher.find()) {
            throw new ValidationException("invalid input in line : [" + line + "]");
        }
        String capacityString = lineMatcher.group(1);
        int capacity = 0;
        try {
            capacity = Integer.parseInt(capacityString);
        } catch (NumberFormatException e) {
            throw new ValidationException("invalid capacity '" + capacityString + "' in line : [" + line + "]");
        }
        String itemsString = lineMatcher.group(2);
        List<Item> items = extractItems(itemsString);
        Solution scenario = new Solution(items, capacity);
        validateScenario(scenario);
        return scenario;
    }

    private void validateScenario(Solution scenario) throws ValidationException {
        for (Validation<Solution> validation : scenarioValidations) {
            validation.validate(scenario);
        }
    }

    private List<Item> extractItems(String itemsString) throws ValidationException {
        Matcher matcher = fieldsPattern.matcher(itemsString);
        List<Item> items = new ArrayList<>();
        try {
            while (matcher.find()) {
                int index = Integer.parseInt(matcher.group(1));
                double weight = Double.parseDouble(matcher.group(2));
                int cost = Integer.parseInt(matcher.group(3));
                Item item = new Item(index, weight, cost);
                validateItem(item);
                items.add(item);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("invalid number in item: [" + matcher.group() + "], " + e.getMessage());
        }
        return items;
    }

    private void validateItem(Item item) throws ValidationException {
        for (Validation<Item> validation : itemValidations) {
            validation.validate(item);
        }
    }

}
