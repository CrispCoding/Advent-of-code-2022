package days;

import java.util.Arrays;
import java.util.Collections;

public class Day01 extends Day {

    @Override
    public String getName() {
        return "Day01";
    }

    private static Integer calculateSumOfCalories(String input){
        return input.lines()
                .map(s->Integer.parseInt(s))
                .reduce(0, Integer::sum);
    }

    @Override
    public int part1(String input) {

        Integer maxCalories = Arrays.stream(input.split("\n\n")) // split the input by empty lines
                .map(s -> calculateSumOfCalories(s)) // sum up the calories for each elf
                .reduce(0, Integer::max); // find the maximum calories per elf

        return maxCalories;
    }

    @Override
    public int part2(String input){

        Integer maxCalories = Arrays.stream(input.split("\n\n")) // split the input at the empty lines
                .map(s -> calculateSumOfCalories(s)) // sum up the calories for each elf
                .sorted(Collections.reverseOrder()) // sort descending
                .limit(3) // only keep the first 3 elements
                .reduce(0, Integer::sum); // sum up those first 3 elements

        return maxCalories;
    }
}
