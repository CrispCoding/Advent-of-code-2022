package days;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends Day {

    private int X;
    private List<Integer> registerValues;

    private void noop(){
        registerValues.add(X);
    }

    private void addx(int increment){
        // add the current value twice
        registerValues.add(X);
        registerValues.add(X);

        X += increment;
    }

    private int computeSumOfSignalStrengths(List<Integer> cycles){
        int sumOfSignalStrengths = 0;
        for (Integer cycleNumber: cycles){
            sumOfSignalStrengths += cycleNumber * registerValues.get(cycleNumber-1); // adjust for zero-based indexing

        }
        return sumOfSignalStrengths;
    }

    @Override
    public int part1(String input) {
        List<String> lines = input.lines().toList();

        // initialize the register
        X = 1;
        registerValues = new ArrayList<>();

        // execute the instructions
        for (String line : lines){
            if (line.equals("noop")){
                noop();
            }
            else {
                int increment = Integer.parseInt(line.split(" ")[1]);
                addx(increment);
            }
        }

        // compute the result
        List<Integer> relevantCycles = List.of(20, 60, 100, 140, 180, 220);
        return computeSumOfSignalStrengths(relevantCycles);
    }

    @Override
    public String getName() {
        return "Day10";
    }

    @Override
    public int part2(String input){
        List<String> lines = input.lines().toList();

        // initialize the register
        X = 1;
        registerValues = new ArrayList<>();

        // execute the instructions
        for (String line : lines){
            if (line.equals("noop")){
                noop();
            }
            else {
                int increment = Integer.parseInt(line.split(" ")[1]);
                addx(increment);
            }
        }

        // go through all pixels and draw them based on the value that X had in the corresponding cycle
        int nLines = 6;
        int nPixelsPerLine = 40;
        for (int lineIdx = 0; lineIdx < nLines; lineIdx++){
            // initialize the empty output line
            List<String> outputLine = new ArrayList<>();

            // draw all pixels in this line
            for (int pixelIdx = 0; pixelIdx < nPixelsPerLine; pixelIdx++){
                // get the value of the register during this cycle
                int cycleIdx = lineIdx * nPixelsPerLine + pixelIdx;
                int registerValue = registerValues.get(cycleIdx);

                // determine the correct value for this pixel
                if (Math.abs(registerValue - pixelIdx) <= 1){
                    outputLine.add("#");
                }
                else {
                    outputLine.add(".");
                }
            }

            // render the output line
            outputLine.stream().forEach(System.out::print);
            System.out.print("\n");
        }

        return 0;
    }


}
