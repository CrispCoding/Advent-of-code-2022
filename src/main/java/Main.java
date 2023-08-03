import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import days.*;

public class Main {

    public static String readInput(String filename) throws IOException {
        Path inputPath = Path.of("src/main/resources/" + filename);
        String input = Files.readString(inputPath);
        return input;
    }

    public static void main(String[] args) throws IOException{
        //change this line to get the result for a different day
        Day currentDay = new Day11();

        // read the input for this day
        String filename = currentDay.getName() + ".txt";
        String input = readInput(filename);

        // run part 1
        int result1 = currentDay.part1(input);
        System.out.println(currentDay.getName() + " part 1: result is " + result1);

        // run part 2
        try{
            int result2 = currentDay.part2(input);
            System.out.println(currentDay.getName() + " part 2: result is " + result2);
        }
        catch (NotImplementedException e){}
    }
}
