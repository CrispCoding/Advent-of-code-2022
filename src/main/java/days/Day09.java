package days;

import days.day09.Knot;

import java.util.ArrayList;
import java.util.List;

public class Day09 extends Day{
    @Override
    public int part1(String input) {
        List<String> lines = input.lines().toList();

        // initialize head and tail both on position 0/0
        int headX = 0;
        int headY = 0;
        Knot tail = new Knot();

        // send the head around and have the tail follow it
        for (String line : lines){
            String[] command = line.split(" ");
            String direction = command[0];
            Integer steps = Integer.parseInt(command[1]);
            for (int i = 0; i < steps; i++){
                switch(direction){
                    case "R" -> headX++;
                    case "L" -> headX--;
                    case "U" -> headY++;
                    case "D" -> headY--;
                }
                tail.follow(headX, headY);
            }
        }

        // ask the tail for the number of distinct positions it visited
        return tail.countVisitedPositions();
    }


    @Override
    public String getName() {
        return "Day09";
    }

    @Override
    public int part2(String input) {
        List<String> lines = input.lines().toList();

        // initialize a rope with 10 knots, all on position 0/0
        int nKnots = 10;
        List<Knot> rope = new ArrayList<>();
        for (int i = 0; i < nKnots; i++){
            rope.add(new Knot());
        }

        // send the head around and have all the other knots follow it
        for (String line : lines){
            String[] command = line.split(" ");
            String direction = command[0];
            Integer steps = Integer.parseInt(command[1]);
            for (int i = 0; i < steps; i++){
                // move the head (= the first knot in the rope)
                Knot head = rope.get(0);
                switch(direction){
                    case "R" -> head.moveRight();
                    case "L" -> head.moveLeft();
                    case "U" -> head.moveUp();
                    case "D" -> head.moveDown();
                }
                // every other knot in the rope follows its predecessor
                for (int knotIdx = 1; knotIdx < nKnots; knotIdx++){
                    rope.get(knotIdx).follow(rope.get(knotIdx-1));
                }
            }
        }

        // ask the tail for the number of distinct positions it visited
        return rope.get(nKnots-1).countVisitedPositions();
    }
}
