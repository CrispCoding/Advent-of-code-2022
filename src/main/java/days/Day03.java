package days;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day03 extends Day{
    private char findCommonItemInCompartments(String rucksackContents){
        // divide the rucksack contents into the first and second half
        int cutoffIndex = rucksackContents.length() / 2;
        String firstCompartmentContents = rucksackContents.substring(0, cutoffIndex);
        String secondCompartmentContents = rucksackContents.substring(cutoffIndex);

        // search for the only common letter
        char commonChar = '0';
        for (char testChar : firstCompartmentContents.toCharArray()){
            if (secondCompartmentContents.indexOf(testChar) > -1){ // contains() does not work with char
                commonChar = testChar;
                break;
            }
        }

        return commonChar;
    }

    private int convertToPriority(char item){
        // convert to priority
        int priority;
        if (item >= 'a'){
            priority = item - 'a' + 1;
        }
        else {
            priority = item - 'A' + 27;
        }

        return priority;
    }

    @Override
    public String getName() {
        return "Day03";
    }

    @Override
    public int part1(String input) {
        Integer prioritySum = input.lines()
                .map(line -> findCommonItemInCompartments(line))
                .map(item -> convertToPriority(item))
                .reduce(0, Integer::sum);
        return prioritySum;
    }

    private char findCommonItemInRucksacks(List<String> rucksacks){
        // search for the only common letter
        char commonChar = '0';
        for (char testChar : rucksacks.get(0).toCharArray()){ // iterate over all items in the first rucksack
            // iterate over all other rucksacks and check if they contain testChar
            boolean foundCommonChar = true;
            for (int i = 1; i < rucksacks.size(); i++){
                if (rucksacks.get(i).indexOf(testChar) == -1){
                    foundCommonChar = false;
                    break;
                }
            }
            if (foundCommonChar){
                commonChar = testChar;
                break;
            }
        }

        return commonChar;
    }

    @Override
    public int part2(String input){
        // split the input into chunks of 3 lines each
        // taken from https://careydevelopment.us/blog/java-how-to-break-a-list-into-batches-using-streams
        AtomicInteger counter = new AtomicInteger();
        Collection<List<String>> elfGroups = input.lines().collect(Collectors.groupingBy(item -> counter.getAndIncrement() / 3)).values();
        Integer prioritySum = elfGroups.stream()
                .map(group -> findCommonItemInRucksacks(group))
                .map(item -> convertToPriority(item))
                .reduce(0, Integer::sum);
        return prioritySum;
    }
}
