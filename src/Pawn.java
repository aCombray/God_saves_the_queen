import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class Pawn extends Piece{

	public Pawn(Position p) {
		super(p, Type.Pawn, ChessColor.White);
	}
	
	@Override
	public Set<Position> getAttackRange(Set<Position> revealedBoard){
		Set<Position> range = new TreeSet<Position>();
		int x = super.p_position.getX();
		int y = super.p_position.getY();
		Position p = new Position(x,y);
		Position [] positions = new Position[]{new Position(1,-1), new Position(-1,-1), new Position(-1,1)};
		for (Position d : positions){
			Position q = p.move(d);
			if (revealedBoard.contains(q)){ 
			range.add(q); 
		  }
		}
		return range;
	}
	
	public static LinkedList<Piece> makeListOfPawns(Position[] s){
		LinkedList<Piece> newset = new LinkedList<Piece>();
		if (s!= null){
		     for (Position p: s){
			    Pawn newpawn = new Pawn(p);
			    newset.add(newpawn);
		     }
		}
		return newset;
		
	}


}
