package days;

import days.day12.Node;

import java.util.Arrays;
import java.util.Comparator;

public class Day12 extends Day{

    private Node[][] nodeGrid;
    private Node source;

    @Override
    public String getName() {
        return "Day12";
    }

    private void parseHeightGraph(String input){
        // find out how many rows and columns there are in the input
        String[] inputLines = input.split("\n");
        int nRows = inputLines.length;
        int nCols = inputLines[0].length();

        // allocate a grid to store all nodes
        nodeGrid = new Node[nRows][nCols];

        // allocate all nodes and set their height markers
        for (int rowIdx = 0; rowIdx < nRows; rowIdx++){
            for (int colIdx = 0; colIdx < nCols; colIdx++){
                nodeGrid[rowIdx][colIdx] = new Node();

                char heightMarker = inputLines[rowIdx].charAt(colIdx);
                if (heightMarker == 'S'){
                    this.source = nodeGrid[rowIdx][colIdx];
                    this.source.setDistance(0);
                    heightMarker = 'a';
                }
                else if (heightMarker == 'E'){
                    nodeGrid[rowIdx][colIdx].setDestination(true);
                    heightMarker = 'z';
                }
                nodeGrid[rowIdx][colIdx].setHeightMarker(heightMarker);
            }
        }

        // create the edges between the nodes
        for (int rowIdx = 0; rowIdx < nRows; rowIdx++) {
            for (int colIdx = 0; colIdx < nCols; colIdx++) {
                Node currentNode = nodeGrid[rowIdx][colIdx];
                if (rowIdx > 0) currentNode.addNeighborIfEligible(nodeGrid[rowIdx-1][colIdx]);
                if (rowIdx < nRows-1) currentNode.addNeighborIfEligible(nodeGrid[rowIdx+1][colIdx]);
                if (colIdx > 0) currentNode.addNeighborIfEligible(nodeGrid[rowIdx][colIdx-1]);
                if (colIdx < nCols-1) currentNode.addNeighborIfEligible(nodeGrid[rowIdx][colIdx+1]);
            }
        }
    }

    public int dijkstra(){

        Node currentNode = this.source;

        while(true){
            // mark the current node as visited
            currentNode.setVisited(true);

            // check if we found the destination:
            if (currentNode.isDestination()){
                return currentNode.getDistance();
            }

            // update the distances to all neighbors of the current node
            int newDistance = currentNode.getDistance() + 1; // all edges have weight 1
            for (Node neighbor: currentNode.getNeighbors()){
                if (neighbor.isVisited()) continue; // ignore all neighbors where the shortest path is already known
                if (neighbor.getDistance() > newDistance){
                    neighbor.setDistance(newDistance);
                }
            }

            // select the unvisited node with the shortest distance as the new current node
            currentNode = Arrays.stream(this.nodeGrid).flatMap(Arrays::stream)
                    .filter(node -> !node.isVisited())
                    .sorted(Comparator.comparing(Node::getDistance))
                    .findFirst().get();

            // sanity check
            if (currentNode.getDistance() == Integer.MAX_VALUE){
                throw new IllegalArgumentException("Source is not connected to destination");
            }
        }
    }

    @Override
    public int part1(String input) {

        parseHeightGraph(input);
        return dijkstra();

    }
}
