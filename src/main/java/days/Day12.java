package days;

import days.day12.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private int dijkstraWithDest(){

        // initialization: set the source's distance to 0
        this.source.setDistance(0);
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
        return dijkstraWithDest();
    }



    private void parseInverseHeightGraph(String input){
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

                // this time, we're considering 'E' as the source, and there is no destination
                char heightMarker = inputLines[rowIdx].charAt(colIdx);
                if (heightMarker == 'S'){
                    heightMarker = 'a';
                }
                else if (heightMarker == 'E'){
                    this.source = nodeGrid[rowIdx][colIdx];
                    heightMarker = 'z';
                }
                nodeGrid[rowIdx][colIdx].setHeightMarker(heightMarker);
            }
        }

        // create the edges between the nodes
        for (int rowIdx = 0; rowIdx < nRows; rowIdx++) {
            for (int colIdx = 0; colIdx < nCols; colIdx++) {
                Node currentNode = nodeGrid[rowIdx][colIdx];
                if (rowIdx > 0) currentNode.addDownwardNeighborIfEligible(nodeGrid[rowIdx-1][colIdx]);
                if (rowIdx < nRows-1) currentNode.addDownwardNeighborIfEligible(nodeGrid[rowIdx+1][colIdx]);
                if (colIdx > 0) currentNode.addDownwardNeighborIfEligible(nodeGrid[rowIdx][colIdx-1]);
                if (colIdx < nCols-1) currentNode.addDownwardNeighborIfEligible(nodeGrid[rowIdx][colIdx+1]);
            }
        }
    }

    private void dijkstra(){

        // initialization: set the source's distance to 0
        this.source.setDistance(0);
        Node currentNode = this.source;

        // create a list containing all unvisited nodes
        List<Node> nodesToVisit = Arrays.stream(this.nodeGrid).flatMap(Arrays::stream).collect(Collectors.toCollection(ArrayList::new));


        while(true){
            // mark the current node as visited
            currentNode.setVisited(true);

            // remove the current node from the list of unvisited nodes
            nodesToVisit.remove(currentNode);

            // if the remaining list of unvisited nodes is empty, then all nodes in the graph have been processed
            if (nodesToVisit.isEmpty()){
                return;
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
            currentNode = nodesToVisit.stream()
                    .sorted(Comparator.comparing(Node::getDistance))
                    .findFirst().get();

            // if this node currently has distance infinity, then the remaining unvisited nodes are not connected to the source
            if (currentNode.getDistance() == Integer.MAX_VALUE){
                return;
            }

        }
    }

    @Override
    public int part2(String input) {
        // construct the graph, this time with inverse connections
        parseInverseHeightGraph(input);

        // run dijkstra to find the distance from position 'E' to all other positions
        dijkstra();

        // go through all positions with elevation 'a' and find the one with the shortest distance
        Node closestNode = Arrays.stream(this.nodeGrid).flatMap(Arrays::stream)
                .filter(node -> node.getHeightMarker() == 'a') // node 'S' also has height marker 'a', no need to treat it separately
                .sorted(Comparator.comparing(Node::getDistance))
                .findFirst().get();

        return closestNode.getDistance();
    }
}
