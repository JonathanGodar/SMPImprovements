package com.ngodar.SMPImprovements.EasyPickaxe;

import java.util.PriorityQueue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class EasyPickaxe implements Listener {

    Location from;

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent evt) {
        if (from == null) {
            from = evt.getBlock().getLocation();
        } else {
            // AStar(from, evt.getBlock().getLocation());
            from = null;
        }
    }

    // private void AStar(Location start, Location goal) {
    // pq = new PriorityQueue<Node>();

    // pq.add(new Node(start, goal, 0, null));

    // // Expand
    // Node curr = pq.poll();

    // while (true) {

    // break;
    // }
    // }

    // private void expandNode(Node n) {

    // addIfPossible(n, 1, 0, 0);
    // addIfPossible(n, -1, 0, 0);
    // addIfPossible(n, 0, 0, 1);
    // addIfPossible(n, 0, 0, -1);
    // }

    // private void addIfPossible(Node n, int dx, int dy, int dz) {

    // Node n = new Node(n.getLocation(), , currentDistanceCost, currentPrev);

    // for(node)

    // }

    // private boolean NodeExists(Node n, PriorityQueue<Node> pq) {

    // for (Node inPq : pq) {
    // if (inPq.matches(n)) {
    // return true;
    // }
    // }

    // return false;
    // }

    private Location FindClosestOre(Location l) {

        int cY = l.getBlockY();

        int worldMax = l.getWorld().getMaxHeight();
        int worldMin = l.getWorld().getMinHeight();
        int radius = 1;

        while (radius < 25) {
            System.out.println(radius);
            for (int y = -radius; y <= radius; y++) {

                if (y < worldMin - cY || y > worldMax - cY) {
                    continue;
                }

                int xMax = radius - Math.abs(y);

                for (int x = -xMax; x <= xMax; x++) {
                    int xyDist = Math.abs(x) + Math.abs(y);

                    int z1 = radius - xyDist;
                    int z2 = xyDist - radius;

                    Location test = l.clone().add(x, y, z1);
                    if (BlockAtIsOre(test))
                        return test;

                    SetBlock(test);
                    test = l.clone().add(x, y, z2);

                    if (BlockAtIsOre(test))
                        return test;
                    SetBlock(test);
                }
            }
            radius++;
        }
        return null;
    }

    private boolean BlockIsOre(Material m) {
        switch (m) {
            case GOLD_ORE:
            case IRON_ORE:
            case EMERALD_ORE:
            case COAL_ORE:
            case DIAMOND_ORE:
            case COPPER_ORE:
                return true;
        }
        return false;

    }

    private boolean BlockAtIsOre(Location l) {
        return BlockIsOre(l.getBlock().getType());
    }

    private void SetBlock(Location l) {
        l.getWorld().getBlockAt(l).setType(Material.GLASS);
    }

    // private boolean AStar() {
    // return false;
    // }
}

// class Node implements Comparable {

// public Node(Location position, Location goal, int currentDistanceCost, Node
// currentPrev) {
// location = position;

// heuristic = (int) (Math.abs(position.getX() - goal.getX()) +
// Math.abs(position.getY() - goal.getY())
// + Math.abs(position.getY() - goal.getY()));

// setCurrentDistance(currentDistanceCost, currentPrev);

// }

// public void setCurrentDistance(int cost, Node through) {
// currentDistanceCost = cost;
// this.through = through;
// }

// public boolean matches(Node other) {
// if (other.location.equals(this.location))
// return true;
// return false;
// }

// private Location location;

// private Node through;

// private int currentDistanceCost;

// private int heuristic;

// public int getTotalHeuristic() {
// return -1;
// }

// public int getDistanceCost() {
// return -1;
// }

// public int getHeuristic() {
// return -1;
// }

// @Override
// public int compareTo(Object o) {
// Node n = (Node) o;
// return getTotalHeuristic() - n.getTotalHeuristic();
// }
// }
