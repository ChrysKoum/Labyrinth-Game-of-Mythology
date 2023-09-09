import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Collections;

public class AdvancedXPlayer extends Player {
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
        
    public static final int[][] directions = {
        {-1, 0},  // UP
        {0, 1},   // RIGHT
        {1, 0},   // DOWN
        {0, -1}   // LEFT
    };
    
    private int getDirection(Point current, Point next) {
        if (next.x == current.x - 1 && next.y == current.y) return UP;
        if (next.x == current.x && next.y == current.y + 1) return RIGHT;
        if (next.x == current.x + 1 && next.y == current.y) return DOWN;
        if (next.x == current.x && next.y == current.y - 1) return LEFT;
        return -1;  // This should not happen if the points are adjacent
    }
    
    private boolean canMoveBetween(Point a, Point b, Board board) {
        Tile tileA = board.getTile(a.x, a.y);
        int direction = getDirection(a, b);
        switch (direction) {
            case UP:
                return !tileA.up;
            case DOWN:
                return !tileA.down;
            case LEFT:
                return !tileA.left;
            case RIGHT:
                return !tileA.right;
        }
        return false;
    }

    public AdvancedXPlayer() {
        super();
    }
    
    public AdvancedXPlayer(int playerId, String name, Board board, int score, int x, int y) {
        super(playerId, name, board, score, x, y);
    }

    public List<Point> findPath(int startX, int startY, int endX, int endY, Board board, int attempt){
        if (attempt > 5) {
            return null;  // Too many attempts, return null
        }
        
        List<Point> path = new ArrayList<>();
    
        if (startX == endX && startY == endY) {
            path.add(new Point(startX, startY));
            return path;
        }
    
        Point[][] prev = new Point[board.N][board.N];
        boolean[][] visited = new boolean[board.N][board.N];
        Queue<Point> queue = new LinkedList<>();
        
        queue.add(new Point(startX, startY));
        visited[startX][startY] = true;
        
        while (!queue.isEmpty()) {
            Point current = queue.poll();
    
            Tile tile = board.getTile(current.x, current.y);  // Assuming a method to get the Tile object
    
            for (int[] direction : directions) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];
    
                if (newX >= 0 && newX < board.N && newY >= 0 && newY < board.N) {
                    if (!visited[newX][newY] && tile.canMove(getDirection(current, new Point(newX, newY)))) {
                        Point nextPoint = new Point(newX, newY);
                        queue.add(nextPoint);
                        visited[newX][newY] = true;
                        prev[newX][newY] = current;
                    }
                }
            }
        }
    
        // Construct the path from end to start
        Point current = new Point(endX, endY);
        while (current != null) {
            path.add(current);
            current = prev[current.x][current.y];
        }
        
        // Check if the path contains walls
        boolean hasWalls = false;
        for (int i = 0; i < path.size() - 1; i++) {
            if (!canMoveBetween(path.get(i), path.get(i+1), board)) {
                hasWalls = true;
                break;
            }
        }
        
        if (hasWalls) {
            // Invalid path; try to find another one that is longer
            int maxAttempts = 5;  // Limit the number of attempts to avoid infinite loops
            int attempts = 0;
            while (hasWalls && attempts < maxAttempts) {
                attempts++;
                path = findPath(startX, startY, endX, endY, board, attempt + 1);  // Recursive call
                if (path == null) {
                    return null;
                }
                hasWalls = false;
                for (int i = 0; i < path.size() - 1; i++) {
                    if (!canMoveBetween(path.get(i), path.get(i+1), board)) {
                        hasWalls = true;
                        break;
                    }
                }
            }
            if (attempts == maxAttempts) {
                return null;  // If still invalid after max attempts, return null
            }
        }

        Collections.reverse(path); // Reverse to get the path from start to end
    
        return path;
    }

    // Method to calculate the safety score of a path based on the Minotaur's position
    private double calculateSafetyScore(List<Point> path, Player minotaur) {
        double safetyScore = 0;
    
        for (Point position : path) {
            int distanceToMinotaur = Math.abs(position.x - minotaur.getx()) + Math.abs(position.y - minotaur.gety());
            safetyScore += distanceToMinotaur;
        }
    
        return safetyScore;
    }

    // The enhanced advancedMove() method
    public int[] advancedMove(int id, Player minotaur) {
        int[] idp = new int[4];
        idp[0] = id;
        List<Point> bestPath = null;
        double bestPathScore = Double.MAX_VALUE;  // Lower is better
        
        // Print the current position of Theseus
        System.out.printf("Theseus is currently at (%d, %d).\n", getx(), gety());
        
        // Get the coordinates of the Minotaur.
        int minotaurX = minotaur.getx();
        int minotaurY = minotaur.gety();
        System.out.printf("Minotaur is currently at (%d, %d).\n", minotaurX, minotaurY);
    
        // Iterate through all supplies to find the best path
        for (Supply supply : board.supplies) {
            if (!supply.isAvailable()) continue;
            List<Point> pathToSupply = findPath(getx(), gety(), supply.x, supply.y, board, 0);
            
            if (pathToSupply != null) {
                // We have found a path to this supply
                // Calculate its score based on length and safety from Minotaur
                double pathLength = pathToSupply.size();
                double safetyScore = calculateSafetyScore(pathToSupply, minotaur);  // TODO: Implement this function
                double pathScore = pathLength - safetyScore*10;  // Subtracting to ensure that safer paths (higher safety scores) get lower total scores
                
                if (pathScore < bestPathScore) {
                    bestPathScore = pathScore;
                    bestPath = pathToSupply;
                }
            }
        }
    
        // If we found a best path, make the move in the direction of the first step
        if (bestPath != null && bestPath.size() > 1) {
            Point nextStep = bestPath.get(1);  // Index 0 is the current position, so we look at index 1 for the next step
            idp[1] = nextStep.x;
            idp[2] = nextStep.y;
        } else {
            // If no suitable path was found, stay put
            idp[1] = getx();
            idp[2] = gety();
            System.out.println("No suitable path found. Staying put.");
        }
    
        // Check if a supply has been picked up (similar to original logic)
        if (id == 1) {
            for (Supply supply : board.supplies) {
                if (supply.x == idp[1] && supply.y == idp[2] && supply.isAvailable()) {
                    idp[3] = supply.supplyId;
                    supply.pickUp();  // Assuming a method pickUp() that marks the supply as unavailable
                    System.out.printf("Picked up supply with ID %d.\n", idp[3]);
                    break;
                } else {
                    idp[3] = -1;
                }
            }
        }
    
        if (idp[3] == -1) {
            System.out.println("No supply picked up.");
        }
    
        for (int i = 0; i <= 3; i++) {
            System.out.println("idp" + i + "= " + idp[i]);
        }
    
        return idp;
    }

}
