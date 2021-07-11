package com.farhan.case_study;

import com.farhan.case_study.packer.exception.FileException;
import com.farhan.case_study.packer.exception.ValidationException;
import com.farhan.case_study.models.Item;
import com.farhan.case_study.models.Package;
import com.farhan.case_study.packer.LineParser;
import com.farhan.case_study.packer.Solution;
import com.farhan.case_study.packer.Validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws FileException {


        System.out.println("Enter Input File Path");
        Scanner inputFile = new Scanner(System.in);
        String solution = pack(inputFile.nextLine());

        System.out.println(solution);

    }

    /**
     * @param inputFile is the path provided by the user to the input file.
     * @return a String containing solution for the given input.
     * @throws FileException if file path is wrong or has invalid input.
     */
    public static String pack(String inputFile) throws FileException {
        LineParser parser = new LineParser(buildScenarioValidators(), buildItemValidators());
        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            List<String> lines = stream.collect(Collectors.toList());
            List<Solution> scenarios = new ArrayList<>();
            for (String line : lines) {
                scenarios.add(parser.parse(line));
            }
            return scenarios.stream().map(Solution::solve).map(Package::output)
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new FileException("could not read file: " + e.getMessage(), e);
        } catch (ValidationException e) {
            throw new FileException("validation error: " + e.getMessage());
        }
    }

    /**
     * @return list of Validation on {@link Item}.
     */

    private static List<Validation<Item>> buildItemValidators() {
        return Arrays.asList(

                new Validation<>(item -> item.getWeight() > 100, "weight is greater than 100."),
                new Validation<>(item -> item.getWeight() <= 0, "weight is less than 0."),
                new Validation<>(item -> item.getCost() > 100, "cost is greater than 100.")
        );
    }

    /**
     * @return list of Validation on {@link Solution}.
     */
    private static List<Validation<Solution>> buildScenarioValidators() {
        return Arrays.asList(
                new Validation<>(pack -> pack.getCapacity() > 100, "Package size greater than 100."),
                new Validation<>(pack -> pack.getCapacity() <= 0, "Package size less than 0."),
                new Validation<>(pack -> pack.getItems().size() > 15, "more than 15 item.")
        );
    }

}
