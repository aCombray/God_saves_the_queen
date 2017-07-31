import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class Player {
	/**
	 * A player is a list of pieces. 
	 * The status of a player consists of:
     * @param m_c, the color of the player
     * @param m_pieces, the chess pieces this player is controlling. 
	 */
	public final ChessColor m_c; 
	public List<Piece> m_pieces;
	public Player(ChessColor c){
		m_c = c; 
		m_pieces = new LinkedList<Piece>();
	}
	

	/**
	 * The player can recruit the chess pieces,
	 * @param newPiece, the new chess pieces adding to the List,
	 */
	
	public void recruit(Piece newPiece){
		m_pieces.add(newPiece);
	}
	
	/**
	 * @param movepiece. You move this piece to capture the enemy. 
	 * @param enemy. The piece of the opponent's that you want to capture,
	 * replace the position of movepiece by the position of the enemy's.
	 */
	public boolean capture(Piece movepiece, Piece enemy){
		if (movepiece == null || enemy == null){
			return false;
		} else if (!m_pieces.contains(movepiece)){
			return false;
		} else {
			movepiece.updatePos(enemy.getPos());
			return true;
		}
	
	}
	
	/**
	 * 
	 * @param lost The piece that is captured by the opponent. 
	 * @return
	 */
	public boolean captured(Piece lost){
		if (m_pieces.contains(lost)){
			m_pieces.remove(lost);
			return true;
		} else return false;
	}
	
	public Piece inRange(Position p, Set<Position> revealedBoard){
		for (Piece cp: m_pieces){
			if (cp.inThePath(p, revealedBoard)){
				return cp;
			}
		}
		return null; 
	}
	
	public Piece inRange(Piece enemy, Set<Position> revealedBoard){
		for (Piece cp: m_pieces){
			if (cp.inThePath(enemy, revealedBoard)){
				return cp;
			}
		}
		return null; 
	}
	
	
	public List<Piece> getPieces(){
		return m_pieces;
	}
	
	public Set<Position> getPositions(){
		Set<Position> getP = new TreeSet<Position>();
		for (Piece c: m_pieces){
			getP.add(c.getPos());
		}
		return getP;
	} 
	
	public Piece getPiece(Position p){
		if (this.getPositions().contains(p)) {
			for (Piece c: m_pieces) {
				if (c.getPos().equals(p)){
					return c;
				}
			}
		} 
		return null;
	}
	
	public ChessColor getColor(){
		return m_c;
	}
	
	
}
