
public class Tile {
	int tileId;
	int x;
	int y;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	

  public Tile () {
	  tileId=0;
	  x=0;
	  y=0;
	  up=false;
	  down=false;
	  left=false;
	  right=false;
	  
  }
  
  public Tile (Tile t) {
	  tileId = t.tileId;
	  x=t.x;
	  y=t.y;
	  up=t.up;
	  down=t.down;
	  left=t.left;
	  right=t.right;
	  
  }
  
  public void settileId(int tileID) {
	  tileId=tileID;
  }
  
  public int gettileId() {
	  return tileId;
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
  
  public void setup(boolean up) {
	  this.up=up;
  }
  
  public boolean getup() {
	  return up;
  }
  
  public void setdown(boolean down) {
	  this.down=down;
  }
  
  public boolean getdown() {
	  return down;
  }
  
 public void setleft(boolean left) {
	 this.left=left;
 }
 
 public boolean getleft() {
	 return left;
 }
 
 public void setright(boolean right) {
	 this.right=right;
 }
 
 public boolean getright() {
	 return right;
 }
 public boolean isAccessible() {
        // A tile is accessible if it doesn't have walls on all sides
        return !(up && right && down && left);
    }
    public boolean canMove(int direction) {
        switch (direction) {
            case AdvancedXPlayer.UP:
                return !this.up;
            case AdvancedXPlayer.RIGHT:
                return !this.right;
            case AdvancedXPlayer.DOWN:
                return !this.down;
            case AdvancedXPlayer.LEFT:
                return !this.left;
            default:
                return false;  // This should not happen if the provided direction is valid
        }
    }
}

