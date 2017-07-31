import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BlackPlayer extends Player {
	private Position m_position;
	private List<Piece> carryList;
	
	
   
	public BlackPlayer(Piece newpiece) {
		super(ChessColor.Black);
		setM_position(new Position(0,0));
		carryList = new LinkedList<Piece>();
		super.m_pieces.add(newpiece);
	}


	public Position getM_position() {
		return m_position;
	}


	public void setM_position(Position m_position) {
		this.m_position = m_position;
		updateAllCarriedPieces();
	}
	
	
	public void updateAllCarriedPieces(){
		if (carryList != null){
		    for (Piece c: carryList){
			   c.updatePos(m_position);
		    }
		}
	}
	
	
	
	@Override
	public void recruit(Piece newpiece){
		newpiece.updatePos(this.m_position);
		carryList.add(newpiece);
	}
	
	@Override
	public boolean capture(Piece movepiece, Piece enemy){
	    if (carryList.contains(movepiece)) {
			movepiece.updatePos(enemy.getPos());
			super.m_pieces.add(movepiece);
			return carryList.remove(movepiece);
		} else {
			return false; 
		}
	
	
	}
	
	
	
	public Map<Type, Integer> getCarriedPieces(){
		Map<Type, Integer> carriedPieces = new TreeMap<Type, Integer>();
		carriedPieces.put(Type.Bishop, 0);
		carriedPieces.put(Type.Knight, 0);
		carriedPieces.put(Type.Rook, 0);
		for (Piece c: carryList){
			
			switch(c.getType()){
			case Bishop: carriedPieces.put(Type.Bishop, (carriedPieces.get(Type.Bishop)+1)); break;
			case Knight: carriedPieces.put(Type.Knight, (carriedPieces.get(Type.Knight)+1)); break;
			case Rook: carriedPieces.put(Type.Rook, (carriedPieces.get(Type.Rook)+1)); break;
			default:
				break; 
			}
		}
		
		return carriedPieces;
	}
	
	
	
	
	@Override
	public Piece inRange(Position p, Set<Position> revealedBoard){
		if (carryList != null){
		   for (Piece cp: carryList){
			  if (cp.inThePath(p, revealedBoard)){
				   return cp;
			  }
		   }
		}
		return null; 
	}
	
	@Override
	public Piece inRange(Piece enemy, Set<Position> revealedBoard){
		for (Piece cp: carryList){
			if (cp.inThePath(enemy, revealedBoard)){
				return cp;
			}
		}
		return null; 
	}
	
	public Piece inRange(Type t, Piece enemy, Set<Position> revealedBoard){
		if (this.getCarriedPieces().get(t)>0) {
		    for (Piece c: carryList){
			    if (c.getType().equals(t) && c.inThePath(enemy, revealedBoard)){
				     return c;
			    }
		    }
		}
		return null;
	}
	
	
	
	
	

}
