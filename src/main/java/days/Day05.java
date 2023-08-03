package days;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Day05 extends Day{

    private List<Deque<String>> stacks;

    private void createStacks(String stackCounterString){
        // extract the final number in the string
        String[] stackNumbers = stackCounterString.split(" ");
        int nStacks = Integer.parseInt(stackNumbers[stackNumbers.length-1]);

        // create this many stacks
        stacks = new ArrayList<Deque<String>>(nStacks);
        for (int i = 0; i < nStacks; i++){
            stacks.add(new ArrayDeque<String>());
        }
    }

    private void addRowToStacks(String crateRow){
        for (int charIdx = 1; charIdx < crateRow.length(); charIdx += 4){
            // get the letter at the position of the next crate
            String crateLetter = crateRow.substring(charIdx, charIdx+1);
            // if it's not just a whitespace, add it to the next stack
            if (!crateLetter.isBlank()){
                int stackIdx = (charIdx-1)/4;
                stacks.get(stackIdx).push(crateLetter);
            }
        }
    }

    private void setupCrates(String stackDrawing){
        // split the instruction into lines
        String[] stackDrawingRows = stackDrawing.split("\n");
        int nRowsInStackDrawing = stackDrawingRows.length;

        // the bottom row tells us how many stacks there are
        createStacks(stackDrawingRows[nRowsInStackDrawing-1]);

        // the other rows contain the crates - go through them from bottom to top to set up the stacks
        for (int i = nRowsInStackDrawing-2; i >= 0; i--){
            addRowToStacks(stackDrawingRows[i]);
        }
    }

    private void printStacks(){
        for (Deque<String> stack : stacks){
            System.out.println("Next stack");
            for (String crate : stack){
                System.out.println(crate);
            }
        }
        System.out.println(""); // empty line to separate this from the next output
    }

    private void moveCrates(String move){
        // parse the move directions (adjust "from" and "to" for zero-based indexing)
        String[] moveComponents = move.split(" ");
        int count = Integer.parseInt(moveComponents[1]);
        int fromStack = Integer.parseInt(moveComponents[3]) - 1;
        int toStack = Integer.parseInt(moveComponents[5]) - 1;

        // move the crates
        for (int i = 0; i < count; i++){
            String crate = stacks.get(fromStack).pop();
            stacks.get(toStack).push(crate);
        }
    }


    @Override
    public String getName() {
        return "Day05";
    }

    @Override
    public int part1(String input) {
        // split at the empty line (\n\n)
        String[] inputParts = input.split("\n\n");

        // use the top part to set up the initial stacks
        setupCrates(inputParts[0]);

        // iterate through the lines in the bottom part, using push and pop to move the crates
        String[] moves = inputParts[1].split("\n");
        for (String move : moves){
            // perform the move
            moveCrates(move);
        }

        // pop the first item from each stack and concatenate them into the final output
        String output = "";
        for (Deque<String> stack : stacks){
            output = output.concat(stack.pop());
        }
        // print the output here because this time it's not an int
        System.out.println("Output of part 1: " + output);

        return 0;
    }


    private void moveMultipleCratesAtOnce(String move){
        // parse the move directions (adjust "from" and "to" for zero-based indexing)
        String[] moveComponents = move.split(" ");
        int count = Integer.parseInt(moveComponents[1]);
        int fromStack = Integer.parseInt(moveComponents[3]) - 1;
        int toStack = Integer.parseInt(moveComponents[5]) - 1;

        // initialize the "temp" Deque
        Deque<String> temp = new ArrayDeque<String>();

        // move the crates into the temp queue (this will reverse their order)
        for (int i = 0; i < count; i++){
            String crate = stacks.get(fromStack).pop();
            temp.push(crate);
        }
        // push the crates from temp onto the final stack (this will reverse their order again so they are in the original order)
        for (int i = 0; i < count; i++){
            String crate = temp.pop();
            stacks.get(toStack).push(crate);
        }
    }

    @Override
    public int part2(String input) {
        // split at the empty line (\n\n)
        String[] inputParts = input.split("\n\n");

        // use the top part to set up the initial stacks
        setupCrates(inputParts[0]);

        // iterate through the lines in the bottom part, using push and pop to move the crates
        String[] moves = inputParts[1].split("\n");
        for (String move : moves){
            // perform the move
            moveMultipleCratesAtOnce(move);
        }

        // pop the first item from each stack and concatenate them into the final output
        String output = "";
        for (Deque<String> stack : stacks){
            output = output.concat(stack.pop());
        }
        // print the output here because this time it's not an int
        System.out.println("Output of part 2: " + output);

        return 0;
    }
}
