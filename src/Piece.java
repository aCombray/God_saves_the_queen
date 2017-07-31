import java.util.Set;

public abstract class Piece {
	/** 
	 * A chess piece in the game. It should have a position, color, type, attack range. 
	 */
	

		/** Current position of the piece, type and color.
		 * @param p_position, the current position of the piece.
		 * @param p_type, the type of the piece,
		 * @param p_c, the color of the piece. 
		 */
		protected Position p_position; 
		public Type p_type;
        public ChessColor p_c;
        
	    
		
		/**
		 * Constructor
		 */
		public Piece( Position p, Type t, ChessColor c){
			this.p_position = p;
			this.p_type = t;
			this.p_c = c;
        }
       
		public boolean isEnemy(Piece enemy){
			return !(this.p_c == enemy.p_c); 
		}

		/**
		 * override this method
		 * Add parameter the revealedBoard from the GameBoard. 
		 * @return
		 */
		public Set<Position> getAttackRange(Set<Position> revealedBoard){
			return null;
		}
		
		public boolean inThePath(Piece enemy, Set<Position> revealedBoard){
		    if (this.isEnemy(enemy)== false){
		    	return false;
		    }
			for (Position pos : this.getAttackRange(revealedBoard)){
				if (pos.equals(enemy.p_position)){
					return true;
				}
			} 
			return false;
		}
		
		
		public boolean inThePath(Position p,Set<Position> revealedBoard){
			for (Position pos : this.getAttackRange(revealedBoard)){
				if (pos.equals(p)){
					return true;
				}
			} 
			return false;
			
		}
		
		
		
		
		public Position getPos(){
			return p_position; 
		}
		public void updatePos(Position newposition){
			this.p_position = newposition; 
		}
		
		public ChessColor getColor(){
			return p_c;
		}
		
		public Type getType(){
			return p_type;
		}
		
		
		
		

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((p_c == null) ? 0 : p_c.hashCode());
			result = prime * result + ((p_position == null) ? 0 : p_position.hashCode());
			result = prime * result + ((p_type == null) ? 0 : p_type.hashCode());
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
			Piece other = (Piece) obj;
			if (p_c != other.p_c)
				return false;
			if (p_position == null) {
				if (other.p_position != null)
					return false;
			} else if (!p_position.equals(other.p_position))
				return false;
			if (p_type != other.p_type)
				return false;
			return true;
		}
		
		

}
