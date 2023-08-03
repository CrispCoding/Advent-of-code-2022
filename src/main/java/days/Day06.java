package days;

import java.util.HashMap;

public class Day06 extends Day {

    @Override
    public String getName() {
        return "Day06";
    }

    private int detectMarker(String input, int markerSize){
        // very concise and readable, but also really inefficient

        long startTime = System.currentTimeMillis();

        int nCharsReceived = markerSize;
        for (; nCharsReceived <= input.length(); nCharsReceived++){
            String window = input.substring(nCharsReceived-markerSize, nCharsReceived);
            if (window.chars().distinct().count() == markerSize){
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Runtime of distinct-count-based version: " + (endTime-startTime) + " milliseconds");

        return nCharsReceived;
    }

    private int detectMarkerMoreEfficiently(String input, int markerSize){
        // less readable, much more efficient

        long startTime = System.currentTimeMillis();

        HashMap<Character, Integer> occurrences = new HashMap<Character, Integer>();

        int currentIndex = 0;

        // populate the hash map with the first markerSize-1 characters
        for (; currentIndex < markerSize; currentIndex++){
            char currentChar = input.charAt(currentIndex);
            Integer currentCount = occurrences.getOrDefault(currentChar, 0);
            occurrences.put(currentChar, currentCount+1);
        }

        for (; currentIndex <= input.length(); currentIndex++){
            // remove the character that just fell out of the sliding window
            char charToRemove = input.charAt(currentIndex-markerSize);
            int countAfterRemoval = occurrences.get(charToRemove)-1;
            if (countAfterRemoval == 0){
                occurrences.remove(charToRemove);
            }
            else{
                occurrences.put(charToRemove, countAfterRemoval);
            }

            // add the character that just entered the sliding window
            char charToAdd = input.charAt(currentIndex);
            int currentCount = occurrences.getOrDefault(charToAdd, 0);
            occurrences.put(charToAdd, currentCount+1);

            // count the number of keys (= the number of distinct characters)
            if (occurrences.size() == markerSize){
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Runtime of hashmap-based version: " + (endTime-startTime) + " milliseconds");

        // adjust for zero-based indexing
        return currentIndex+1;
    }

    @Override
    public int part1(String input) {
        int res1 = detectMarkerMoreEfficiently(input, 4);
        int res2 = detectMarker(input, 4);
        assert (res1 == res2);
        return res1;
    }

    @Override
    public int part2(String input) {
        int res1 = detectMarkerMoreEfficiently(input, 14);
        int res2 = detectMarker(input, 14);
        assert (res1 == res2);
        return res1;
    }
}
