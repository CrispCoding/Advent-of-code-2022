package days;
import java.util.Arrays;

public class Day08 extends Day{

    private int height;
    private int width;
    private int[][] grid;
    private int[][] visibility;
    private int[][] scenicScores;

    @Override
    public String getName() {
        return "Day08";
    }

    private void printArray(int[][] arr){
        // test output
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[0].length; j++){
                System.out.print(arr[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private void parseTreeHeights(String[] lines){
        // parse the height of each tree
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                grid[i][j] = Character.getNumericValue(lines[i].charAt(j));
            }
        }
    }

    private void updateVisibility(int xStart, int xEnd, int xInc, int yStart, int yEnd, int yInc){
        int maxVal = -1;

        for (int i = yStart; i != yEnd; i += yInc){
            for (int j = xStart; j != xEnd; j += xInc){
                if (grid[i][j] > maxVal){
                    maxVal = grid[i][j];
                    visibility[i][j] = 1;
                }
            }
        }
    }

    @Override
    public int part1(String input) {
        // split the input into lines
        String[] lines = input.split("\n");

        // get the height and width of the tree grid
        height = lines.length;
        width = lines[0].length();

        // allocate the grid and visibility arrays
        grid = new int[height][width];
        visibility = new int[height][width];

        // parse the tree heights
        parseTreeHeights(lines);

        // scan the grid in all directions to determine visibility
        // rows
        for (int rowIdx = 0; rowIdx < height; rowIdx++){
            // left to right
            updateVisibility(0, width, 1, rowIdx, rowIdx+1, 1);
            // right to left
            updateVisibility(width-1, -1, -1, rowIdx, rowIdx+1, 1);
        }
        // columns
        for (int colIdx = 0; colIdx < width; colIdx ++){
            // top down
            updateVisibility(colIdx, colIdx+1, 1, 0, height, 1);
            // bottom up
            updateVisibility(colIdx, colIdx+1, 1, height-1, -1, -1);
        }

        // count the number of visible trees
        int sumVisible = Arrays.stream(visibility).flatMapToInt(Arrays::stream)
                .reduce(0, Integer::sum);

        return sumVisible;
    }

    private int scenicScore(int xStart, int xEnd, int xInc, int yStart, int yEnd, int yInc, int currentHeight){
        int scenicScore = 0;

        for (int i = yStart; i != yEnd; i += yInc){
            for (int j = xStart; j != xEnd; j += xInc){
                scenicScore++;
                if (grid[i][j] >= currentHeight)
                    return scenicScore;
            }
        }

        return scenicScore;
    }

    private void findScenicScores(){
        // is there a more efficient way to do this?
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                int currentHeight = grid[i][j];
                int score =
                        scenicScore(j+1, width, 1, i, i+1, 1, currentHeight) // to the right
                        * scenicScore(j-1, -1, -1, i, i+1, 1, currentHeight) // to the left
                        * scenicScore(j, j+1, 1, i+1, height, 1, currentHeight) // to the bottom
                        * scenicScore(j, j+1, 1, i-1, -1, -1, currentHeight) // to the top
                        ;
                scenicScores[i][j] = score;
            }
        }
    }

    @Override
    public int part2(String input) {
        // split the input into lines
        String[] lines = input.split("\n");

        // get the height and width of the tree grid
        height = lines.length;
        width = lines[0].length();

        // allocate the grid and scenic score arrays
        grid = new int[height][width];
        scenicScores = new int[height][width];

        // parse the tree heights
        parseTreeHeights(lines);

        // find the scenic score for each tree
        findScenicScores();

        // find the highest scenic score
        int highestScore = Arrays.stream(scenicScores).flatMapToInt(Arrays::stream)
                .reduce(0, Integer::max);
        return highestScore;
    }
}
