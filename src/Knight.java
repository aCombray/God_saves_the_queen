import java.util.Set;
import java.util.TreeSet;

public class Knight extends Piece {

	public Knight(Position p, ChessColor c) {
		super(p, Type.Knight, c);
	
	}
	
	@Override
	public Set<Position> getAttackRange(Set<Position> revealedBoard){
		Set<Position> range = new TreeSet<Position>();
		int x = super.p_position.getX();
		int y = super.p_position.getY();
		Position p = new Position(x,y);
		Position [] positions = new Position[]{new Position(2,1), new Position(2,-1), new Position(-2,1), 
				new Position(-2,-1), new Position(1,2), new Position(-1,2), new Position(-1,-2), new Position(1,-2)};
		for (Position d: positions) {
			Position q = p.move(d);
			if (revealedBoard.contains(q)){
				range.add(q);
			}
		}
		return range;
	}

}
