package days;

public class Day02 extends Day{

    private int computeScoreForLine(String line){
        // get characters representing the played shapes
        char myShapeChar = line.charAt(2);
        char opponentChar = line.charAt(0);

        // compute the score for the shape that I played
        int score = myShapeChar - 'W';

        // add the score for the result of the game
        if (myShapeChar - opponentChar == 'X' - 'A'){
            // draw
            score += 3;
        }
        else if (line.equals("A Y") || line.equals("B Z") || line.equals("C X") ){
            // win
            score += 6;
        }
        // if none of those cases are true, I lost, so I don't add any points

        return score;
    }

    @Override
    public String getName() {
        return "Day02";
    }

    @Override
    public int part1(String input) {
        Integer score = input.lines()
                .map(line -> computeScoreForLine(line))
                .reduce(0, Integer::sum);
        return score;
    }

    private int computeSecondScoreForLine(String line){
        char opponentShape = line.charAt(0);
        char outcome = line.charAt(2);

        // compute the score for the outcome
        int score = (outcome - 'X') * 3;

        // figure out which shape I need to use and compute the score for this shape
        // some weird modulo operations to iterate through the numbers [1,2,3] in a circular fashion
        int opponentShapeScore = opponentShape - 'A' + 1;
        if (outcome == 'X'){ // lose
            score += (opponentShapeScore + 1) % 3 + 1; // take the number to the "left" of the opponent's score
        }
        else if (outcome == 'Y'){ // draw
            score += opponentShapeScore;
        }
        else { // win
            score += (opponentShapeScore % 3) + 1; // take the number to the "right" of the opponent's score
        }

        return score;
    }

    @Override
    public int part2(String input){
        Integer totalScore = input.lines()
                .map(line -> computeSecondScoreForLine(line))
                .reduce(0, Integer::sum);

        return totalScore;
    }
}
