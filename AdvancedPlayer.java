
public class AdvancedPlayer extends Player {
    
    public AdvancedPlayer() {
        super();  // Calling the constructor of the parent class Player
	}
	
    // Constructor for AdvancedXPlayer
    public AdvancedPlayer(int playerId, String name, Board board, int score, int x, int y) {
        super(playerId, name, board, score, x, y);  // Calling the constructor of the parent class Player
    }

    private int[] moveAwayFromMinotaur(int currentX, int currentY, Tile currentTile, String avoidDirection) {
        int[] newPosition = new int[2];
        newPosition[0] = currentX;
        newPosition[1] = currentY;
    
        switch (avoidDirection) {
            case "down":
                if (currentTile.up == false) {
                    newPosition[0]++;
                    System.out.println("Moving upwards. Madown");
                } else if (currentTile.left == false) {
                    newPosition[1]--;
                    System.out.println("Moving to the left. Madown");
                } else if (currentTile.right == false) {
                    newPosition[1]++;
                    System.out.println("Moving to the right. Madown");
                }
                break;
            case "up":
                if (currentTile.down == false) {
                    newPosition[0]--;
                    System.out.println("Moving downwards. MaUp");
                } else if (currentTile.left == false) {
                    newPosition[1]--;
                    System.out.println("Moving to the left. MaUp");
                } else if (currentTile.right == false) {
                    newPosition[1]++;
                    System.out.println("Moving to the right. MaUp");
                }
                break;
            case "left":
                if (currentTile.right == false) {
                    newPosition[1]++;
                    System.out.println("Moving to the right. MaLeft");
                } else if (currentTile.up == false) {
                    newPosition[0]++;
                    System.out.println("Moving upwards. MaLeft");
                } else if (currentTile.down == false) {
                    newPosition[0]--;
                    System.out.println("Moving downwards. MaLeft");
                }
                break;
            case "right":
                if (currentTile.left == false) {
                    newPosition[1]--;
                    System.out.println("Moving to the left. MaRight");
                } else if (currentTile.up == false) {
                    newPosition[0]++;
                    System.out.println("Moving upwards. MaRight");
                } else if (currentTile.down == false) {
                    newPosition[0]--;
                    System.out.println("Moving downwards. MaRight");
                }
                break;
            
            case "upRight":
                if (currentTile.left == false) {
                    newPosition[1]--;
                    System.out.println("Moving to the left. MaUpRight");
                } else if (currentTile.down == false) {
                    newPosition[0]--;
                    System.out.println("Moving downwards. MaUpRight");
                }
                break;
            case "upLeft":
                if (currentTile.right == false) {
                    newPosition[1]++;
                    System.out.println("Moving to the right. MaUpLeft");
                } else if (currentTile.down == false) {
                    newPosition[0]--;
                    System.out.println("Moving downwards. MaUpLeft");
                }
                break;
            case "downRight":
                if (currentTile.up == false) {
                    newPosition[0]++;
                    System.out.println("Moving upwards. MaDownRight");
                } else if (currentTile.left == false) {
                    newPosition[1]--;
                    System.out.println("Moving to the left. MaDownRight");
                }
                break;
            case "downLeft":
                if (currentTile.up == false) {
                    newPosition[0]++;
                    System.out.println("Moving upwards. MaDownLeft");
                } else if (currentTile.right == false) {
                    newPosition[1]++;
                    System.out.println("Moving to the right. MaDownLeft");
                }
                break;
        }
    
        return newPosition;
    }
    
    private String getMinotaurDirection(Board board, int x, int y, int minotaurX, int minotaurY) {
        int tileId = board.N*x+y;
        
        if (board.tiles[tileId].up == false && minotaurX - 1 == x) return "up";
        if (board.tiles[tileId].up == false && minotaurX - 1 == x && minotaurY - 1 == y) return "upRight";
        if (board.tiles[tileId].up == false && minotaurX - 1 == x && minotaurY + 1 == y) return "upLeft";
        if (board.tiles[tileId].down == false && minotaurX + 1 == x) return "down";
        if (board.tiles[tileId].down == false && minotaurX + 1 == x && minotaurY - 1 == y) return "downRight";
        if (board.tiles[tileId].down == false && minotaurX + 1 == x && minotaurY + 1 == y) return "downLeft";
        if (board.tiles[tileId].right == false && minotaurY - 1 == y) return "right";
        if (board.tiles[tileId].left == false && minotaurY + 1 == y) return "left";

        return "unknown"; // If Minotaur's position isn't determined by any of the conditions.
    }
    
    public int[] advancedMove(int id, Player minotaur) {
        int[] idp = new int[4];
        idp[0] = id;
        double minDistance = Double.MAX_VALUE;
        int nearestSupplyIndex = -1;
        boolean minotaurUp, minotaurRight, minotaurDown, minotaurLeft, 
        minotaurUpRight, minotaurUpLeft, minotaurDownRight, minotaurDownLeft;
        String minotaurDirection;
    
        // Print the current position of Theseus
        System.out.printf("Theseus is currently at (%d, %d).\n", getx(), gety());
    
        // Find the nearest supply.
        for(int i = 0; i < board.S; i++) {
            double distance = Math.hypot(board.supplies[i].x - getx(), board.supplies[i].y - gety());
            if(distance < minDistance && board.supplies[i].isAvailable()) {
                minDistance = distance;
                nearestSupplyIndex = i;
            }
        }
    
        // If a nearest supply was found.
        if(board.supplies[nearestSupplyIndex].isAvailable()) {
            int supplyX = board.supplies[nearestSupplyIndex].x;
            int supplyY = board.supplies[nearestSupplyIndex].y;
            System.out.printf("Nearest supply found at (%d, %d).\n", supplyX, supplyY);
    
            // Get the coordinates of the Minotaur.
            int minotaurX = minotaur.getx();
            int minotaurY = minotaur.gety();
            System.out.printf("Minotaur is currently at (%d, %d).\n", minotaurX, minotaurY);
    
            // Check each direction for the best move.
            for(int i = 0; i < board.N * board.N; i++) {
                if(board.tiles[i].x == getx() && board.tiles[i].y == gety()) {
                    // Check if the Minotaur is near. If yes, move in the opposite direction.
                    if(Math.abs(minotaurX - getx()) <= 1 && Math.abs(minotaurY - gety()) <= 1) {
                        System.out.println("Minotaur is near. Moving away.");
    
                        minotaurDirection = getMinotaurDirection(board, getx(), gety(), minotaurX, minotaurY);
                        
                        if (minotaurDirection != "unknown") {
                            int[] newPosition = moveAwayFromMinotaur(getx(), gety(), board.tiles[i], minotaurDirection);
                            idp[1] = newPosition[0];
                            idp[2] = newPosition[1];
                        }
                        if(idp[1] == getx() && idp[2] == gety()){
                            System.out.println("Trapped by Minotaur! No move possible.");
                            break;
                        }
                    }
                    // If the Minotaur is not near, move towards the supply.
                    // If the Minotaur is not near, move towards the supply.
                    else {
                        System.out.println("Minotaur is not near. Moving towards the supply.");
                        // Try to move directly towards the supply if possible.
                        if(board.tiles[i].left == false  && getx() == supplyX && gety() > supplyY) {
                            idp[1] = getx();
                            idp[2] = gety() - 1;
                            System.out.println("Moving to the left.MnnS");
                            break;
                        } else if(board.tiles[i].up == false && gety() == supplyY && getx() < supplyX) {
                            idp[1] = getx() + 1;
                            idp[2] = gety();
                            System.out.println("Moving upwards.MnnS");
                            break;
                        } else if(board.tiles[i].right == false && getx() == supplyX && gety() < supplyY) {
                            idp[1] = getx();
                            idp[2] = gety() + 1;
                            System.out.println("Moving to the right.MnnS");
                            break;
                        } else if(board.tiles[i].down == false && gety() == supplyY && getx() > supplyX) {
                            idp[1] = getx() - 1;
                            idp[2] = gety();
                            System.out.println("Moving downwards.MnnS");
                            break;
                        }
                        
                        else if(board.tiles[i].left == false && gety() > supplyY) {
                            idp[1] = getx();
                            idp[2] = gety() - 1;
                            System.out.println("Moving to the left.S");
                            break;
                        } else if(board.tiles[i].up == false && getx() < supplyX) {
                            idp[1] = getx() + 1;
                            idp[2] = gety();
                            System.out.println("Moving upwards.S");
                            break;
                        } else if(board.tiles[i].right == false && gety() < supplyY) {
                            idp[1] = getx();
                            idp[2] = gety() + 1;
                            System.out.println("Moving to the right.S");
                            break;
                        } else if(board.tiles[i].down == false && getx() > supplyX) {
                            idp[1] = getx() - 1;
                            idp[2] = gety();
                            System.out.println("Moving downwards.S");
                            break;
                        }
                    
                        // If can't move directly towards the supply, try to move around the wall.
                        else if(board.tiles[i].left == false) {
                            idp[1] = getx();
                            idp[2] = gety() - 1;
                            System.out.println("Moving to the left.");
                            break;
                        } else if(board.tiles[i].up == false) {
                            idp[1] = getx() + 1;
                            idp[2] = gety();
                            System.out.println("Moving upwards.");
                            break;
                        } else if(board.tiles[i].right == false) {
                            idp[1] = getx();
                            idp[2] = gety() + 1;
                            System.out.println("Moving to the right.");
                            break;
                        } else if(board.tiles[i].down == false) {
                            idp[1] = getx() - 1;
                            idp[2] = gety();
                            System.out.println("Moving downwards.");
                            break;
                        }
                    }
    
                }
            }
        }
        // If no suitable supply was found, stay put.   
        else {
            idp[1] = getx();
            idp[2] = gety();
            System.out.println("No suitable supply found. Staying put.");
        }
    
        // Check if a supply has been picked up.
        if(id == 1) {
            for(int i = 0; i < board.S; i++) {
                if(board.supplies[i].x == idp[1] && board.supplies[i].y == idp[2] && board.supplies[i].isAvailable()) {
                    idp[3] = board.supplies[i].supplyId;
                    board.supplies[i].x = -1;  // Make the supply unavailable.
                    board.supplies[i].y = -1;  // Make the supply unavailable.
                    board.supplies[i].isAvailable(false);
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
        for(int i = 0; i <= 3; i++){
            System.out.println( "idp" + i + "= " + idp[i] );
        }
        return idp;
    }
}