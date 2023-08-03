package days;

import days.day06.Directory;
import org.javatuples.Pair;

import java.util.List;

public class Day07 extends Day{
    private Directory createDirectoryStructure(String input){
        // split the input into the terminal lines
        String[] lines = input.split("\n");

        // create the root directory and set it as the current directory
        Directory root = new Directory("/", null);
        Directory currentDir = root;

        // iterate through all commands to create the directory structure
        for (String line : lines) {
            if (line.startsWith("$ cd")) {
                String targetDirName = line.split(" ")[2];
                switch (targetDirName) {
                    case "/" -> currentDir = root;
                    case ".." -> currentDir = currentDir.getParent();
                    default -> currentDir = currentDir.getChildDirectoryByName(targetDirName);
                }
            } else if (line.startsWith("$ ls")) {
                // we can ignore the "ls" lines
            } else {
                // this is the "ls" output
                String[] elementDescription = line.split(" ");
                if (elementDescription[0].equals("dir")) {
                    currentDir.addDirectory(elementDescription[1]);
                } else {
                    currentDir.addFile(elementDescription[1], Long.parseLong(elementDescription[0]));
                }
            }
        }

        return root;
    }

    @Override
    public String getName() {
        return "Day07";
    }

    @Override
    public int part1(String input) {
        Directory root = createDirectoryStructure(input);

        // get the descriptions of the sizes of each directory
        List<Pair<String, Long>> sizesList = root.getAllSubdirectorySizes();

        // compute the output number
        Long sumOfSmallDirSizes = sizesList.stream()
                .map(pair -> pair.getValue1())
                .filter(val -> val <= 100000L)
                .reduce(0L, Long::sum);

        return Math.toIntExact(sumOfSmallDirSizes);
    }

    @Override
    public int part2(String input) {
        Directory root = createDirectoryStructure(input);

        // determine the amount of space that needs to be freed
        long availableDiskSpace = 70000000 - root.getSize();
        long requiredToFree = 30000000 - availableDiskSpace;

        // get the descriptions of the sizes of each directory
        List<Pair<String, Long>> sizesList = root.getAllSubdirectorySizes();

        // find the smallest directory that would free up enough space
        Long deletedDirectorySize = sizesList.stream()
                .map(pair -> pair.getValue1())
                .filter(val -> val >= requiredToFree)
                .reduce(Long.MAX_VALUE, Long::min);

        return Math.toIntExact(deletedDirectorySize);
    }
}
