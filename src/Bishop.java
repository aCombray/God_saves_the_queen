import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

public class Bishop extends Piece {
	public Bishop(Position position, ChessColor c){
		super(position, Type.Bishop, c);
	}
	
	@Override
	public Set<Position> getAttackRange(Set<Position> revealedBoard){
		Set<Position> range = new TreeSet<Position>();
		int x = super.p_position.getX();
		int y = super.p_position.getY();
		Position p = new Position(x,y);
		
		Position [] positions = new Position []{new Position(1,1), new Position(-1,1), new Position(1,-1), new Position(-1,-1)};
		   for (Position d: positions){
			   Deque<Position> pdq = new ArrayDeque<Position>();
			   pdq.add(p.move(d)); 
			   while(revealedBoard.contains(pdq.getLast())){
				   Position q = pdq.getLast();
				   range.add(q);
				   pdq.addLast(q.move(d));
			   }
		   }
		
		
		return range;
		
	}
	

}
