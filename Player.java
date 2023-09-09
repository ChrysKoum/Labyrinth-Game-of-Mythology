public class Player {
	int PlayerId;
	String name;
	Board board;
	int score;
	int x;
	int y;

	public Player() {
		PlayerId = 0;
		name = "";
		board = null;
		score = 0;
		x = 0;
		y = 0;
	}
	
	public Player(int PlayerId,String name,Board board,int score,int x,int y) {
		this.PlayerId = PlayerId;
		this.name = name;
		this.board = board;
		this.score = score;
		this.x = x;
		this.y = y;
        System.out.printf("x,y (%d, %d) name=%s.\n", this.x, this.y, this.name);
	}
	
	public Player (Player a) {
		PlayerId = a.PlayerId;
		name = a.name;
		board = a.board;
		score = a.score;
		x = a.x;
		y = a.y;
	}
	
	public void setPlayerId(int playerId) {
		PlayerId=playerId;
	}
	
	public int getPlayerId() {
		return PlayerId;
	}
	
	public void setname(String name) {
		this.name=name;
	}
	
	public String getname() {
		return name;
	}
	
	public void setboard(Board board) {
		this.board=board;
	}
	
	public Board getboard() {
		return board;
	}
	
	public void setscore(int score) {
		this.score=score;
	}
	
	public int getscore() {
		return score;
	}
	
	public void setx(int x) {
		this.x=x;
	}
	
	public int getx() {
		return x;
	}
	
	public void sety(int y) {
		this.y=y;
	}
	
	public int gety() {
		return y;
	}
	
    private int[] getRandomMove(Board board) {
        int[] idp = new int[3];
        boolean canMove = false;
        while (!canMove) {
            int rand = (int)((Math.random() * 4) + 1);
            switch (rand) {
                case 1:  // Move upwards
                    for(int i=0; i<board.N*board.N; i++) {
                        if(board.tiles[i].x == getx() && board.tiles[i].y == gety()){
                            if(!board.tiles[i].up) {
                                System.out.println("Can move to the direction up");
                                idp[1] = getx() + 1;
                                idp[2] = gety();
                                canMove = true;
                                break;
                            } else {
                                System.out.println("Can't move to the direction up :) wall in the way");
                            }
                        }
                    }
                    break;
    
                case 2:  // Move right
                    for(int i=0; i<board.N*board.N; i++) {
                        if(board.tiles[i].x == getx() && board.tiles[i].y == gety()){
                            if(!board.tiles[i].right) {
                                System.out.println("Can move to the direction right");
                                idp[1] = getx();
                                idp[2] = gety() + 1;
                                canMove = true;
                                break;
                            } else {
                                System.out.println("Can't move to the direction right :) wall in the way");
                            }
                        }
                    }
                    break;
    
                case 3:  // Move downwards
                    for(int i=0; i<board.N*board.N; i++) {
                        if(board.tiles[i].x == getx() && board.tiles[i].y == gety()){
                            if(!board.tiles[i].down) {
                                System.out.println("Can move to the direction down");
                                idp[1] = getx() - 1;
                                idp[2] = gety();
                                canMove = true;
                                break;
                            } else {
                                System.out.println("Can't move to the direction down :) wall in the way");
                            }
                        }
                    }
                    break;
    
                case 4:  // Move left
                    for(int i=0; i<board.N*board.N; i++) {
                        if(board.tiles[i].x == getx() && board.tiles[i].y == gety()){
                            if(!board.tiles[i].left) {
                                System.out.println("Can move to the direction left");
                                idp[1] = getx();
                                idp[2] = gety() - 1;
                                canMove = true;
                                break;
                            } else {
                                System.out.println("Can't move to the direction left :) wall in the way");
                            }
                        }
                    }
                    break;
            }
        }
        return idp;
    }
	/* Simple Move fuction that make the game fuctioning*/
	public int[] move(int id) {	
		int[]idp = new int[4];
		idp[0] = id;
		
        int[] newPosition = getRandomMove(board);
        idp[1] = newPosition[1];
        idp[2] = newPosition[2];
        
		if(idp[1] == 0 && idp[2] == 0) { //if is not possible the just return the same x nad y
    		idp[1] = getx();
    		idp[2] = gety();
		}
		
		if(id == 1) {			// check if theseus and a supply is in the same Tile so 
			for(int i=0;i<board.S;i++) {     //theseus can grab it and raise his score
				if(board.supplies[i].x == getx() && board.supplies[i].y == gety()){
					System.out.println("getx()="+getx()+ " gety()="+gety() );
					idp[3] = board.supplies[i].supplyId;
					System.out.println("idp[3]="+idp[3] );
					System.out.println("s="+board.supplies[i].x+ " s="+board.supplies[i].y );
					board.supplies[i].x = 0;   //made the used supply with zero variables
					board.supplies[i].y = 0;	//so that can't get picked again 
					System.out.println("Theseas get a supply with id S"+board.supplies[i].supplyId+" !! ");
				}
			}
			
		}
		if(idp[3] == 0) {  //if theseus dont get a supply return a negative number
			idp[3] = -1;
		}
        for(int i = 0; i <= 3; i++){
            System.out.println( "idp" + i + "= " + idp[i] );
        }
		
		return idp;
	}
}