package days.day12;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Node {

    private char heightMarker;
    private Integer distance;
    private boolean visited;
    private boolean destination;

    @Setter(AccessLevel.NONE) // we don't want a setter for the neighbors list
    private List<Node> neighbors;

    public Node(){
        this.neighbors = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
    }

    public void addNeighborIfEligible(Node other){
        if (other.getHeightMarker() <= this.heightMarker+1) {
            this.neighbors.add(other);
        }
    }

    public void addDownwardNeighborIfEligible(Node other){
        // constructing the reverse path
        if (this.getHeightMarker() <= other.heightMarker+1) {
            this.neighbors.add(other);
        }
    }
}
