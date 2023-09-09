
public class Board extends Player{
	int N;
	int S;
	int W;
	int minotaurX;
    int minotaurY;
	Tile[] tiles ;
	Supply[] supplies;
	
	public Board() {
		N = 0;
		S = 0;
		W = 0;
		tiles = new Tile[N*N];
		supplies = new Supply[S];
	}
	
	public Board(int N,int S,int W) {
		this.N = N;
		this.S = S;
		this.W = (N*N*3+1)/2;
		tiles = new Tile[N*N];
		supplies = new Supply[S];
	}
	public Board (Board b) {
		N = b.N;
		S = b.S;
		W = b.W;
		tiles = new Tile[N*N];
		supplies = new Supply[S];
	}
    
    public int getMinotaurX() {
        return minotaurX;
    }
    
    public void setMinotaurX(int minotaurX) {
        this.minotaurX = minotaurX;
    }
    
    public int getMinotaurY() {
        return minotaurY;
    }
    
    public void setMinotaurY(int minotaurY) {
        this.minotaurY = minotaurY;
    }
	
	public void setN(int N) {
		  this.N=N;
	  }
	  
	  public int getN() {
		  return N;
	  }
	  public void setS(int S) {
		  this.S=S;
	  }
	  
	  public int getS() {
		  return S;
	  }
	  
	  public void setW(int W) {
		  this.W=W;
	  }
	  
	  public int getW() {
		  return W;
	  }
	  
	  public void setTile(Tile[] tiles) {
		  for(int i=0;i<N*N;i++) {
				this.tiles[i] = tiles[i];
			}
		  
	  }
	  
	  public Tile getTile(int x, int y) {
            if (x < 0 || x >= N || y < 0 || y >= N) {
                return null;  // or throw an exception, based on your preference
            }
            return tiles[x * N + y];
        }

	  
	  public void setSupply(Supply[] supplies) {
		  for(int i=0;i<S;i++) {
				this.supplies[i] = supplies[i];
			}
	  }
	  
	  public Supply[] getSupply() {
		  return supplies;
	  }
	  
	  public void createTile() {
		  for(int i=0; i<N*N;i++) {
			   tiles[i] = new Tile();
			}	  //i give to the array the corresponding coordinates from 0 to N*N which correspond to every tile
		  for(int k=0,i=N-1;i>=0;i--) {  
			  for(int j=0;j<N;j++) {
				  tiles[k].x = i;			//i give to the array the corresponding coordinates x
				  tiles[k].y = j;		//and y which correspond to every tile
				  k++;
			  }
		  }
		  for(int i=0; i<N*N;i++) {
			  tiles[i].tileId = N*tiles[i].x+tiles[i].y;											
		  }
		  for(int i=0;i<N*N;i++) {  			//wallfree variable are the null walls that surrounds each tile
			  if(tiles[i].x == 0 ) {
				tiles[i].down = true;
			  }  
			  if(tiles[i].x == N-1) {
				  tiles[i].up = true;
			  }
			  if(tiles[i].y == 0 ) {
				  tiles[i].left = true;
			  }
			  if(tiles[i].y == N-1) {
				  tiles[i].right = true;
			  }
		  }
		  int wallfree,rand;
		  for(int i=0;i<N*N;i++) { 	
			  wallfree = 0;								//wallfree variable are the null walls that surrounds each tile
			  if(tiles[i].down == false) {
				  wallfree++;
			  }
			  if(tiles[i].up == false){
				  wallfree++;
			  }
			  if(tiles[i].right == false){
				  wallfree++;
			  }
			  if(tiles[i].left == false){
				  wallfree++;
			  }										 
			  
			  								// if wallfree variable is bigger than 3 it means that the tile has 4 null walls so we add one more
			   while(wallfree > 3) {
				  rand = (int)((Math.random() * 2) + 1);
				  if(tiles[i+N].down == false && rand == 1) {
					  tiles[i+N].down = true;
					  tiles[i].up = true;
					  wallfree --;
					  
				  }
				  else if(tiles[i].right == false && rand == 2){
					  tiles[i].right = true;
					  tiles[i+1].left = true;
					  wallfree --;
					 
				  }
			  }
		  }
		  
	  }
	  
	  public void createSupply() {
		  for(int i=0; i<S;i++) {
			   supplies[i] = new Supply();
			}
		  boolean k;          // k variable boolean to check if there is anything on the tile 
		  for(int i=0;i<S;i++) {
		        k = true;
			    supplies[i].supplyId = i+1;             //i+1 so the the starting values are S1,S2...Sn
			    supplies[i].supplyTileId = ((int)(Math.random() * (N*N-1))+1); // random numbers from 1 to N*N-1 (0 Theseus starting position)
			    while(k == true){      // if there is something on the tile continue searching for a new tile that does not
			    	k = false;
				    if(getPlayerId() == supplies[i].supplyTileId && getPlayerId() == 2 ) { // if on the tile is the Minotaur
				    	k = true;
				    }
				    for(int j=0;j<S;j++) {
				        if(supplies[i].supplyTileId == supplies[j].supplyTileId && supplies[i].supplyId != supplies[j].supplyId) {// if there is another supply 
				    		k = true;																								//on the tile
				    	}
				    }
				    if(k == true) {														//if the tile is not clear look for another tile
				    	supplies[i].supplyTileId = ((int)(Math.random() * (N*N-1))+1);
				    }
			    }//N*x+y
			    for(int j=0;j<N*N;j++) {
			    	if(tiles[j].tileId == supplies[i].supplyTileId) {
			    		supplies[i].x= tiles[j].x;
			    		supplies[i].y= tiles[j].y;
                    }
			    }
		  }
	  }
	  
	  public void createBoard() {
		  createTile();
		  createSupply();
	  }
	  
	public boolean canMove(int x, int y) {
        int index = x * this.N + y;  // Assuming 'N' is the size of one dimension
        if (index < 0 || index >= this.tiles.length) {
            return false;  // Out of bounds
        }
        return this.tiles[index].isAccessible();  // Assuming there is a method isAccessible() in the Tile class
    }


	  
	  public String[][] getStringRepresentation(int theseusTile, int minotaurTile){
    	  String[][]repres = new String[2*N + 1][N];
    	  for(int i=0;i<(2*N + 1);i++) {
    		  for(int j=0;j<N;j++) {
    			  repres[i][j] = "";
    		  }
    	  }
    	  
		  for(int k,w=0,tcounter=0,i=0;i<2*N;i++) {					//k is the counter for the spaces ,i=2*N because is the horizontal walls and the normal tiles rows
			  for(int skipSupplyM=0, skipSupplyT =0,j=0;j<N;j++) {			    //q help choose when Minotauros or Theseus sit on a supply
				  k = 5;                                    // the spaces between walls
				  if(i%2 == 0) {
					  repres[i][j] = "+ ";
					  if(tiles[tcounter].up == true ) {
						  repres[i][j] += "--- ";
					  }
					  else{
						  repres[i][j] += "    ";
					  }
					  if(j == N-1)
						  repres[i][j] += "+";
				  }
				  if(i%2 == 1) {
					  //when there are walls on the left of the tile
					  if(tiles[w].left == true ) {
							  repres[i][j] = "| ";
							  k--;
					  }else {
						  repres[i][j] = " ";
					  }
					  //if Minotaur and any of the supplies are on the same tile
					  for(int s=0;s<S;s++) 
						  if(tiles[w].tileId  == supplies[s].supplyTileId && supplies[s].supplyTileId  == minotaurTile) {
    				          if(supplies[s].isAvailable()){
    					          if (supplies[s].supplyId >= 10) {
    							    repres[i][j] += "s"+ (supplies[s].supplyId) +"M";
    					          }
    					          else{
    							    repres[i][j] += "s"+ (supplies[s].supplyId) +" M";
    					          }
    				          }
        				      else{
        				          repres[i][j] += " * M";
        				      }
							  k = k-4;
							  skipSupplyM = supplies[s].supplyId;
						  }
					  //when Minotaur is on the tile
					  if(tiles[w].tileId  == minotaurTile && skipSupplyM == 0) {
							  repres[i][j] += " M  ";
							  k = k-4;
					  }
					  
					  //if Theseus and any of the supplies are on the same tile
					  for(int s=0;s<S;s++) 
    					  if(tiles[w].tileId  == supplies[s].supplyTileId && supplies[s].supplyTileId  == theseusTile) {
    				          if(supplies[s].isAvailable()){
    					          if (supplies[s].supplyId >= 10) {
        				  	          repres[i][j] += "s"+ (supplies[s].supplyId) +"T";
                                  }
                                  else{
    				  	              repres[i][j] += "s"+ (supplies[s].supplyId) +" T";
                                  }
    					      }
        				      else{
        				          repres[i][j] += " * T";
        				      }
    				  	      k = k-4;
    						  skipSupplyT = supplies[s].supplyId;
    					  }
					  //when Theseus is on the tile
					  if(tiles[w].tileId  == theseusTile && skipSupplyT == 0) {
						  	repres[i][j] += " T  ";
				  	        k = k-4;
					  }
					  //when any of the supplies is on the tile
					  for(int s=0;s<S;s++) {
						  if(tiles[w].tileId  == supplies[s].supplyTileId && supplies[s].supplyId != skipSupplyM && supplies[s].supplyId != skipSupplyT) {
						      if(supplies[s].isAvailable()){
    					          if (supplies[s].supplyId >= 10) {
                                      repres[i][j] += " s" + supplies[s].supplyId;
                                  }
                                  else {
                                      repres[i][j] += " s" + supplies[s].supplyId + " ";
                                  }
    						  	  k = k-4;
						          
						      }
						      else{
    					          repres[i][j] += " *  ";  
    							  k = k-4;
						      }
						  }
					  }
					  
					  for(int e=0;e<k;e++){    //it tells me how spaces i need to put so
						  repres[i][j] += " "; //i dont lose the balanced of the board
					  }
					  
					  //when there are walls on the right of the tile
					  if(tiles[w].right == true && j == N-1) {
							  repres[i][j] += "|";
					  }
    				  w++;
				  }
				  tcounter++;
				  if(tcounter%N == 0 && i%2 == 0) // for the tiles array and string array to correspond every time 
			    	  tcounter = tcounter - N;			//the counter of string 2N of the tiles array must be N
				  
			  }
		  }
		  
		  for(int j=0;j<N;j++) {			// i made an extra for() because w go from 0 - (2*N)-1
			  repres[2*N][j] = "+ ";		//so i had to make an exception
			  repres[2*N][j] += "--- ";
			  
			  if(j == N-1)
				  repres[2*N][j] += "+";
		  }
		  
		  return repres;
	  }
	  
	  
	  
	  
	  
	  
}
