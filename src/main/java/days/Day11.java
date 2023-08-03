package days;

import days.day11.Monkey;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day11 extends Day{
    @Override
    public String getName() {
        return "Day11";
    }


    @Override
    public int part1(String input) {
        // split input by empty lines
        String[] inputBlocks = input.split("\n\n");

        // parse each block of text to create a new monkey
        List<Monkey> monkeys = new ArrayList<>();
        for (String inputBlock: inputBlocks){
            monkeys.add(new Monkey(inputBlock));
        }

        // run the throwing game for the specified number of rounds
        int nRounds = 20;
        for (int i = 1; i <= nRounds; i++){
            for (Monkey monkey: monkeys){
                monkey.processItems(monkeys);
            }
        }

        // find the two most active monkeys and multiply their values
        int monkeyBusinessLevel = monkeys.stream().map(Monkey::getInspectionCount) // get each monkey's inspection count
                .sorted(Comparator.reverseOrder()) // sort in descending order
                .limit(2) // get the first two items
                .reduce(1, (a, b) -> a*b); // multiply them

        return monkeyBusinessLevel;
    }

    public int part2(String input) {
        // split input by empty lines
        String[] inputBlocks = input.split("\n\n");

        // parse each block of text to create a new monkey
        List<Monkey> monkeys = new ArrayList<>();
        for (String inputBlock: inputBlocks){
            monkeys.add(new Monkey(inputBlock, false));
        }

        // compute the product of all division test values - this will be used as a modulo operator to keep the worry levels manageable
        long commonMultiple = monkeys.stream().map(Monkey::getDivisionTestValue).reduce(1L, (a, b) -> a*b);
        monkeys.stream().forEach(monkey -> monkey.setModuloReductionValue(commonMultiple));

        // run the throwing game for the specified number of rounds
        int nRounds = 10000;
        for (int i = 1; i <= nRounds; i++){
            for (Monkey monkey: monkeys){
                monkey.processItems(monkeys);
            }
            // test output
            //if (i==10000) monkeys.stream().map(Monkey::getInspectionCount).forEach(System.out::println);
        }

        // find the two most active monkeys and multiply their values
        long monkeyBusinessLevel = monkeys.stream().map(Monkey::getInspectionCount) // get each monkey's inspection count
                .sorted(Comparator.reverseOrder()) // sort in descending order
                .limit(2) // get the first two items
                .map(Long::valueOf) // map to long because the result of the multiplication will cause an integer overflow
                .reduce(1L, (a, b) -> a*b); // multiply them

        System.out.println("Part 2: result is "  + monkeyBusinessLevel);
        return 0;
    }

}
