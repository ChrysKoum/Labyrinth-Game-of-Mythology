
public class Supply {
	int supplyId;
	int x;
	int y;
	int supplyTileId;
	boolean available;

	public Supply () {
		supplyId = 0;
		x = 0;
		y = 0;
		supplyTileId = 0;
		available = true;
	}
	
	public Supply (Supply a) {
		supplyId = a.supplyId;
		x = a.x;
		y = a.y;
		supplyTileId = a.supplyTileId;
	}
	
	public void setsupplyId(int SupId) {
		supplyId = SupId;
	}
	
	public int getsupplyId() {
		return supplyId;
	}
	public void setx(int x) {
		this.x = x;
	}
	
	public int getx() {
		return x;
	}
	
	public void sety(int y) {
		this.y = y;
	}
	
	public int gety() {
		return y;
	}
	
	public void setsupplyTileId(int SupTileId) {
		supplyTileId = SupTileId;
	}
	
	public int getsupplyTileId() {
		return supplyTileId;
	}
	
	public void isAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean isAvailable() {
        return available;
    }
    
    public void pickUp() {
        this.available = false;
    }
	
}
