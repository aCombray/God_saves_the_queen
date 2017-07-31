import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * This class stores all status of the game.
 * The output is for each position, is it occupied or not, 
 * if occupied by which piece. 
 * How the status are changed. 
 */
public class GamePlay {
	
	private ChessColor whoseTurn;
	public boolean playing;
	private GameStatus status; 
	private Queen queen;
	private BlackPlayer black;
	private WhitePlayer white;
	private int coinNum; 
	
	
	
	private Set<Position> revealedBoard; 
    private Map<Position, Type> minePositions;
    
    public GamePlay(Map<Position, Type> mineGenerator, int coin_num){
    	whoseTurn = ChessColor.Black;
    	status = GameStatus.Running;
    	playing = true;
    	queen = new Queen();
    	black = new BlackPlayer(queen);
    	Position[] s = {new Position(5,5), new Position(6,5), new Position(7,5), new Position(5,6), new Position(6,6),
    		new Position(7,6), new Position(5,7), new Position(6,7)	};
    	white = new WhitePlayer(Pawn.makeListOfPawns(s));
    	coinNum = coin_num;
    	revealedBoard = new TreeSet<Position>();
    	revealedBoard.add(new Position(0,0));
    	for (int j =7; j>4; j--){
    		for (int i = 7; i>4; i--){
    			revealedBoard.add(new Position(i,j));
    		}
    	}
    	minePositions = mineGenerator;
    }
	
	public Set<Position> getOccupiedTiles(){
		Set<Position> occupied = new TreeSet<Position>();
		occupied.addAll(black.getPositions());
		occupied.addAll(white.getPositions());
		occupied.add(black.getM_position());
        return occupied;
		
	}
	
	//Todo Add unblocked region, so the attacks can not be blocked. 
	
	
	public GameStatus getStatus(){
		return status;
	}
	
	public int getCoinNumber(){
		return coinNum;
	}
	
	public ChessColor getWhoseTurn(){
		return whoseTurn;
	}
	
	public int knightsCarriedByBlack(){
		return black.getCarriedPieces().get(Type.Knight);
	}
	
	public int bishopCarriedByBlack(){
		return black.getCarriedPieces().get(Type.Bishop);
	}
	
	public int rookCarriedByBlack(){
		return black.getCarriedPieces().get(Type.Rook);
	}
	
	
	public int hidenKnight(){
		int x = 0;
		if (minePositions != null){
		    for (Map.Entry<Position, Type> entry : minePositions.entrySet()){
			      if (entry.getValue() == Type.Knight){
				         x++;
			      }
		     }
		}
		return x;
	}
	
	public int hidenBishop(){
		int x = 0;
		if (minePositions != null){
		    for (Map.Entry<Position, Type> entry : minePositions.entrySet()){
			      if (entry.getValue() == Type.Bishop){
				         x++;
			      }
		     }
		}
		return x;
	}
	
	public int hidenRook(){
		int x = 0;
		if (minePositions != null){
		    for (Map.Entry<Position, Type> entry : minePositions.entrySet()){
			     if (entry.getValue() == Type.Rook){
				        x++;
			      }
		     }
		}
		return x;
	}
	
	/**
	 * The basic move of the white player. 
	 */
	
	
	public void whiteMove(){
		if (this.whoseTurn.equals(ChessColor.White)){
			 if (white.inRange(black.getM_position(), revealedBoard)!= null){
				 Piece c = white.inRange(black.getM_position(), revealedBoard);
				 c.updatePos(black.getM_position());
				 this.playing = false;
				 this.status = GameStatus.Lose;
              } else {
				 for (Piece bc : black.m_pieces) {
					if (!bc.getType().equals(Type.Queen) && white.inRange(bc, revealedBoard)!= null){
						Piece c = white.inRange(bc, revealedBoard);
						if (white.capture(c, bc)){
							black.captured(bc);
						} 
						break;
					}
					 
				 }
				 this.whoseTurn = ChessColor.Black;
			 }
			
		 } 
	}
		
    /** 
     * The Displacement of the black player. 
     * @param d, the direction to move, 
     * @return boolean. 
	 */
		
	public boolean blackDisplacement (Direction d){
	    Position md = new Position(0,0);
		switch (d) {
		case DOWN: md = new Position(0,1);
			break;
		case LEFT: md = new Position(-1,0);
			break;
		case RIGHT: md = new Position(1,0);
			break;
		case UP: md = new Position(0,-1);
				break;
		default:
			break;
		}
			
		Position q = black.getM_position().move(md);
		if (q.equals(new Position(7,7))){
			black.setM_position(q);
			black.captured(queen);
			this.playing = false;
			this.status = GameStatus.Win;
			return true;
		} else if (!q.inBoard()){
			return false;
		} else if (revealedBoard.contains(q)){
			if (!this.getOccupiedTiles().contains(q)){
				black.setM_position(q);
				this.whoseTurn = ChessColor.White;
				return true;
			} else {
				return false; 
			}
		} else if (!minePositions.containsKey(q)){
			black.setM_position(q);
			this.revealNewBoard(q);
			this.whoseTurn = ChessColor.White;
			return true; 
		} else {
				switch(minePositions.get(q)){
				case Bishop: Bishop newBishop = new Bishop(q,ChessColor.White);
						     white.recruit(newBishop); break;
				case Knight: Knight newKnight = new Knight(q,ChessColor.White);
						     white.recruit(newKnight); break;
				case Rook:  Rook newRook = new Rook(q, ChessColor.White);
						    white.recruit(newRook); break;
				default: break;
						
				}
		    this.minePositions.remove(q);
		    this.revealNewBoard(q);
			this.whoseTurn = ChessColor.White;
			return true; 
		}
				
			
		}
/**
 * This is a dfs for adding new tiles to the revealed board.
 * @param q
 */
		private void revealNewBoard(Position q) {
			if (!minePositions.containsKey(q) && q.inBoard()){
				this.revealedBoard.add(q);
		        if (numOfAdjacentMines(q) == 0){
				   for (Position adj : q.getAdjacentPositions()){
					   if (!revealedBoard.contains(adj)){
						   revealNewBoard(adj);
					   }
					}
			    }
			}
		}
		
		
		
		
		
		
		
		/**
		 * The black player can recruit. It costs one coin. 
		 * @param testPos, the test position. 
		 * @return boolean.
		 */
		
		
		public boolean blackRecruit(Position testPos) {
			if (black.getM_position().isAdjacent(testPos)&& coinNum > 0){
				if (minePositions.containsKey(testPos)){
					switch(minePositions.get(testPos)){
					case Bishop: Bishop newBishop = new Bishop(testPos,ChessColor.Black);
					             black.recruit(newBishop);
						break;
					case Knight: Knight newKnight = new Knight(testPos,ChessColor.Black);
					             black.recruit(newKnight);
						break;
					case Rook:  Rook newRook = new Rook(testPos, ChessColor.Black);
					             black.recruit(newRook);
						break;
					default:
						break;
					
					}
					this.minePositions.remove(testPos);
				}
				this.revealNewBoard(testPos);
				coinNum = coinNum -1;
				this.whoseTurn = ChessColor.White;
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * The black player can attack with a chess piece of type t. 
		 * @param t, the type of the chess piece thrown out. 
		 * @param target, the target position. 
		 * @return boolean. 
		 */
		
		public boolean blackAttack(Type t, Position target){
			if (white.getPiece(target)!= null) {
				Piece enemy = white.getPiece(target);
				if (black.inRange(t, enemy, revealedBoard)!= null){
					Piece movepiece = black.inRange(t, enemy, revealedBoard);
					black.capture(movepiece, enemy);
					white.captured(enemy);
					this.whoseTurn = ChessColor.White;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
			
		}
		
	/**
	 * The method to distinguish the type if occupied by some black piece. 
	 * @param pos, the position of the tile. 
	 * @return StatusOfTile. 
	 */
		
   
    public StatusOfTile StatusWhenOccupiedByBlack(Position pos){
    	if (black.getPositions().contains(pos)&& pos != null) {
            for (Piece c:black.m_pieces){
                 if (c.getPos().equals(pos)){
	                     switch (c.getType()){
	                     case Bishop:  return StatusOfTile.OccupiedByBDT;
	                     case Knight:  return StatusOfTile.OccupiedByNDT;
	                     case Rook: return StatusOfTile.OccupiedByRDT;
	                     default: throw new RuntimeException("This position can only be occupied by black BKR");
	                     }
                  }
             }
        }
    	return null;
    }
    
    /**
	 * The method to distinguish the type if occupied by some white piece. 
	 * @param pos, the position of the tile. 
	 * @return StatusOfTile. 
	 */
    
    public StatusOfTile statusWhenOccupiedByWhite(Position pos){
    	if (white.getPositions().contains(pos) && pos != null){
	           for (Piece c: white.m_pieces){
                if (c.getPos().equals(pos)){
   	              switch (c.getType()){
		              case Bishop: return StatusOfTile.OccupiedByBLT;
		              case Knight: return StatusOfTile.OccupiedByNLT;
		              case Pawn: return StatusOfTile.OccupiedByPLT;
		              case Rook: return StatusOfTile.OccupiedByRLT;
		              default: throw new RuntimeException("This position can only be occupied by white BKRP");
   	              }
                }
             }
           }
    	return null;
    }
    
    public int numOfAdjacentMines(Position pos){
    	int num = 0;
    	for (Position adj : pos.getAdjacentPositions()){
    		if (minePositions.containsKey(adj)){
    			num++;
    		}
    	}
    	return num;
    }
    
    
    /**ToDo: TO refactory this. 
     * The main method to output the status of the tile. 
     * @param pos, the position of the tile. 
     * @return StatusOfTile. 
     */
		
	public StatusOfTile tileStatus(Position pos){
	    try { 
	      if (pos == null){
	    	  throw new NullPointerException("The arguement is null!");
	      } else if (!pos.inBoard()){
	    	  throw new RuntimeException("Arguement out of Board!");
	      } else if (!revealedBoard.contains(pos)){
	    	  return StatusOfTile.UnRevealed;
	      } else if (!this.getOccupiedTiles().contains(pos)){
	    	  return StatusOfTile.UnOccupied;
	      } else { switch(status){
		           case Lose: {  if (pos.equals(queen.getPos())){
                               return StatusOfTile.OccupiedByQDT;
                      } else if (black.getPositions().contains(pos)) {
                               return this.StatusWhenOccupiedByBlack(pos);
                      } else if (white.getPositions().contains(pos)){
           	                   return this.statusWhenOccupiedByWhite(pos);
                      } break;
                   }
		           case Running:{ if (black.getM_position().equals(pos)){
				               return StatusOfTile.OccupiedByGOD;
				       } else if (pos.equals(queen.getPos())){
				               return StatusOfTile.OccupiedByQDT;
			           } else if (black.getPositions().contains(pos)){
			        	       return this.StatusWhenOccupiedByBlack(pos);
			           } else if (white.getPositions().contains(pos)){
				    	       return this.statusWhenOccupiedByWhite(pos);
			           } break;
		               }
		           case Win: {    if (black.getM_position().equals(pos)){
                               return StatusOfTile.OccupiedByGOD;
                       } else if (black.getPositions().contains(pos)){
                               return this.StatusWhenOccupiedByBlack(pos);
                       } else if (white.getPositions().contains(pos)){
                    	       return this.statusWhenOccupiedByWhite(pos);
                       } break;
                   }
		           default: break;
	               }
	      }	  
	  } catch (NullPointerException e){
	    	System.out.println(e.getMessage());
	  } catch (RuntimeException e) {
	    	System.out.println(e.getMessage());
	  }
	return null;
	}
	
	
	public void printOutGameStatus(){
		for (int j=0; j< 8; j++){
			for (int i=0; i<8; i++){
				Position pos = new Position(i,j);
				StatusOfTile st = this.tileStatus(pos);
				if (st!= null){
				switch (st){
				case OccupiedByBDT: System.out.print(" BD ");
					break;
				case OccupiedByBLT: System.out.print(" BL ");
					break;
				case OccupiedByGOD: System.out.print(" GG ");
					break;
				case OccupiedByNDT: System.out.print(" ND ");
					break;
				case OccupiedByNLT: System.out.print(" NL ");
					break;
				case OccupiedByPLT: System.out.print(" PL ");
					break;
				case OccupiedByQDT: System.out.print(" QN ");
					break;
				case OccupiedByRDT: System.out.print(" RD ");
					break;
				case OccupiedByRLT: System.out.print(" RL ");
					break;
				case UnOccupied: System.out.print(" EM ");
					break;
				case UnRevealed: System.out.print(" ** ");
					break;
				default: System.out.print(" ?? ");
					break;
				
				}
				}
			}
			System.out.println();
		}
		System.out.println(" ------------------------------");
	}

}
