package com.ngodar.SMPImprovements.EasyPickaxe;

import java.util.PriorityQueue;
import java.util.Spliterator;

import org.bukkit.Location;

public class AStar {

    PriorityQueue<Node> pq;
    Location origin;
    Location goal;

    int goalX;
    int goalY;
    int goalZ;

    public AStar(Location origin, Location goal) {
        this.origin = origin;
        this.goal = goal;

        goalX = goal.getBlockX();
        goalY = goal.getBlockY();
        goalZ = goal.getBlockZ();

        pq = new PriorityQueue<Node>(100);
    }

    public Node[] calculate() {

        boolean goalReached = false;
        while (!goalReached && pq.size() > 0) {
            Node curr = pq.poll();

            expandNode(curr);
        }

        return null;
    }

    private void expandNode(Node toExpand) {

    }

    private void pushNewNodeIfPossible(Node prevNode, int dx, int dy, int dz) {

        Location newNodeLocation = prevNode.location.clone().add(dx, dy, dz);
        Node n = new Node(newNodeLocation, prevNode.distanceCost + dx + dy + dz, calculateEstCost(newNodeLocation));

        Node possibleOther = NodeExists(n);
        if (possibleOther != null) {

            if(n.distanceCost < possibleOther.distanceCost){
                
            }

            return;
        }

    }

    private Node DeleteIfNodeExists(Node n) {
        Spliterator spliterator = pq.spliterator();

        spliterator
        for (Node check : pq) {
            if (check.posMatches(n)) {
                return check;
            }
        }
        return null;
    }

    private int calculateEstCost(Location l) {
        return Math.abs(l.getBlockX() - goalX) + Math.abs(l.getBlockY() - goalY) + Math.abs(l.getBlockZ() - goalZ);
    }

}

class Node implements Comparable {

    public Node(Location location, int distnaceCost, int estCost) {
        this.location = location;
        this.distanceCost = distnaceCost;
        this.estCost = estCost;
    }

    public Location location;
    public int distanceCost;
    public int estCost;
    public Node via;

    public int getTotalCost() {
        return distanceCost + estCost;
    }

    @Override
    public int compareTo(Object o) {
        Node n = (Node) o;
        return getTotalCost() - n.getTotalCost();
    }

    public boolean posMatches(Node other) {
        return this.location.equals(other.location);
    }

}