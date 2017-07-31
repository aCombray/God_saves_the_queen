import java.util.Set;
import java.util.TreeSet;

public class Position implements Comparable<Position>{
	private final int pos_x, pos_y;
	public Position(int x, int y){
		pos_x = x;
		pos_y = y;
	}
	
	public boolean inBoard(){
		if (pos_x < 0 || pos_x>7 || pos_y <0 || pos_y> 7) {
			return false;
		} else {
			return true; 
		}
	
	}
	
	public int getX(){
		return pos_x;
	}
	
	public int getY(){
		return pos_y;
	}
	
	public PositionType getPositionType(){
		if (pos_x == 0 && pos_y == 0) {
			return PositionType.NWCorner;
		} else if (pos_x == 0 && pos_y == 7) {
			return PositionType.SWCorner;
			
		} else if (pos_x == 7 && pos_y == 0) {
			return PositionType.NECorner;
		} else if (pos_x == 7 && pos_y == 7) {
			return PositionType.SECorner;
		} else if ( pos_x == 0 ){
			return PositionType.WBorder;
		} else if (pos_x == 7) {
			return PositionType.EBorder;
		} else if (pos_y == 0 ) {
			return PositionType.NBorder;
		} else if (pos_y == 7) {
			return PositionType.SBorder;
		} else {
			return PositionType.Interior;
		}
	}
	
	
	
	
	public Position move(int x, int y){
		int t_x = this.pos_x + x;
		int t_y = this.pos_y + y;
		return new Position(t_x, t_y);
	}
	
	public Position move(Position d){
		return this.move(d.getX(), d.getY()); 
	}
	
	public boolean isAdjacent(Position other){
		if (other == null) {
			return false;
		} else if (!other.inBoard()) {
			return false;
		} else if (Math.abs(this.getX()-other.getX())< 2 && Math.abs(this.getY()-other.getY())<2  ) {
			return true; 
		} else {
			return false;
		}
	}
	
	public Set<Position> getAdjacentPositions(){
		Set<Position> adj = new TreeSet<Position>();
		for (int i = -1; i< 2; i++) {
			for (int j = -1; j< 2; j++){
				Position q = this.move(i, j);
				if (q.inBoard() && !this.equals(q)){
					adj.add(q);
				}
			}
		
		}
		
		return adj;
	}
	
	public String toString(){
		return "(" + Integer.toString(pos_x) + ","+ Integer.toString(pos_y) + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pos_x;
		result = prime * result + pos_y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (pos_x != other.pos_x)
			return false;
		if (pos_y != other.pos_y)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Position o){
		Integer x = this.getX();
		Integer y = this.getY();
		Integer xo = o.getX();
		Integer yo = o.getY();
		int firstresult = x.compareTo(xo);
		if (firstresult != 0) {
			return firstresult;
		} else {
			return y.compareTo(yo);
		}
	}
	

}
