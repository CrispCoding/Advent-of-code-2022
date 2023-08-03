package days;

import java.util.Arrays;

public class Day04 extends Day{
    private boolean fullyContained(String line){
        String[] assignments = line.split(",");
        int[] firstElfLimits = Arrays.stream(assignments[0].split("-")).mapToInt(Integer::parseInt).toArray();
        int[] secondElfLimits = Arrays.stream(assignments[1].split("-")).mapToInt(Integer::parseInt).toArray();

        boolean contained = (firstElfLimits[0] <= secondElfLimits[0] && firstElfLimits[1] >= secondElfLimits[1])
                || (firstElfLimits[0] >= secondElfLimits[0] && firstElfLimits[1] <= secondElfLimits[1]);
        return contained;
    }

    @Override
    public String getName() {
        return "Day04";
    }

    @Override
    public int part1(String input) {
        long containedCount = input.lines().filter(line -> fullyContained(line)).count();
        return Math.toIntExact(containedCount);
    }

    private boolean overlaps(String line){
        String[] assignments = line.split(",");
        int[] firstElfLimits = Arrays.stream(assignments[0].split("-")).mapToInt(Integer::parseInt).toArray();
        int[] secondElfLimits = Arrays.stream(assignments[1].split("-")).mapToInt(Integer::parseInt).toArray();

        boolean notOverlapping = secondElfLimits[1] < firstElfLimits[0] || secondElfLimits[0] > firstElfLimits[1];
        return !notOverlapping;
    }

    @Override
    public int part2(String input) {
        long overlapCount = input.lines().filter(line -> overlaps(line)).count();
        return Math.toIntExact(overlapCount);
    }
}
