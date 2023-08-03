package days.day09;

import org.javatuples.Pair;

import java.util.HashSet;
import java.util.Set;

public class Knot {
    private int x;
    private int y;
    private Set<Pair<Integer,Integer>> visitedPositions;


    // Constructor

    public Knot(){
        x = 0;
        y = 0;
        visitedPositions = new HashSet<>();
        updateVisitedPositions();
    }


    // Getter and setter

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Set<Pair<Integer, Integer>> getVisitedPositions() {
        return visitedPositions;
    }


    // following another knot around

    public void follow(int headX, int headY){
        // follow the head as appropriate
        if (Math.abs(headX-x) > 1 || Math.abs(headY-y) > 1){
            x += Math.signum(headX-x);
            y += Math.signum(headY-y);
        }

        // keep track of all visited positions
        updateVisitedPositions();
    }

    public void follow(Knot head){
        follow(head.getX(), head.getY());
    }


    // move this knot around independently (without following another knot)

    public void moveUp(){
        y++;
        updateVisitedPositions();
    }

    public void moveDown(){
        y--;
        updateVisitedPositions();
    }

    public void moveRight(){
        x++;
        updateVisitedPositions();
    }

    public void moveLeft(){
        x--;
        updateVisitedPositions();
    }


    // helper function for keeping track of visited positions

    private void updateVisitedPositions(){
        visitedPositions.add(new Pair<Integer,Integer>(x, y));
    }


    // info output

    public int countVisitedPositions(){
        return visitedPositions.size();
    }






}
