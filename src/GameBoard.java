import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {
	private GamePlay game;
	private JLabel gstatus;
	private JLabel gcoin_num;
	private JLabel gk_status;
	private JLabel gb_status;
	private JLabel gr_status;
	private Tile[][] tileArray = new Tile[8][8];
	private boolean success = false;
	private Mode black_mode = Mode.Displacement;
	public static final int BOARD_WIDTH = 640;
	public static final int BOARD_HEIGHT = 640;
	

	
	public GameBoard(JLabel status, JLabel coin_num, JLabel k_num, JLabel b_num, JLabel r_num){
		
		
		
		int k=2;
		int b=3; 
		int r=3;
		
		Map<Position,Type> mineGenerator = this.generateMinePositions(k, b, r);
		this.game = new GamePlay(mineGenerator, k+b+r);
		this.success = false;
		this.gstatus = status;
		this.gcoin_num = coin_num;
		this.gk_status = k_num;
		this.gb_status = b_num;
		this.gr_status = r_num;
		this.black_mode = Mode.Displacement;
		this.setLayout(new GridLayout(8,8));
		
		setFocusable(true);

		// This key listener allows the black player to move as long
		// as an arrow key is typed. 

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (game.getStatus().equals(GameStatus.Running) && game.getWhoseTurn() == ChessColor.Black
						&& success == false){
					if (e.getKeyCode() == KeyEvent.VK_LEFT)
						success = game.blackDisplacement(Direction.LEFT);
					else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
						success = game.blackDisplacement(Direction.RIGHT);
					else if (e.getKeyCode() == KeyEvent.VK_DOWN)
						success = game.blackDisplacement(Direction.DOWN);
					else if (e.getKeyCode() == KeyEvent.VK_UP)
						success = game.blackDisplacement(Direction.UP);
					response();
				}
				
			}

			
		});
		
		
		
		for (int j = 0; j< tileArray.length; j++){
			for (int i = 0; i< 8; i++){
				Position t_position = new Position(i,j);
				tileArray[i][j] = new Tile (game.tileStatus(t_position), t_position, game.numOfAdjacentMines(t_position));  
				tileArray[i][j].addMouseListener(new MouseAdapter(){

					@Override
					public void mouseClicked(MouseEvent e) {
						if (game.getStatus().equals(GameStatus.Running) && game.getWhoseTurn() == ChessColor.Black
								&& success == false )	{
							switch (black_mode){
							case BishopAttack: success = game.blackAttack(Type.Bishop, t_position);
								break;
							case Displacement: 
								break;
							case KnightAttack: success = game.blackAttack(Type.Knight, t_position);
								break;
							case Recruit: success = game.blackRecruit(t_position);
								break;
							case RookAttack: success = game.blackAttack(Type.Rook, t_position);
								break;
							default:
								break;
							
							}
						}
							
						    response();
						}
					
				});
				this.add(tileArray[i][j]);
			}
			}
		
		
		
	}
	
	public void boardUpdate(){
		for (int j = 0; j<tileArray.length; j++){
			for (int i=0; i< 8; i++){
			tileArray[i][j].updateStatus(game.tileStatus(tileArray[i][j].getPos()));
			tileArray[i][j].updateNum(game.numOfAdjacentMines(tileArray[i][j].getPos()));
			}
		}
		this.repaint();
		gcoin_num.setText("#Coin: " + game.getCoinNumber());
		gk_status.setText("#Knights: " + game.knightsCarriedByBlack());
		gb_status.setText("#Bishop: " + game.bishopCarriedByBlack());
		gr_status.setText("#Rooks: " + game.rookCarriedByBlack());
	}
	
	public void response(){
		if (success == true){
			if (game.getStatus().equals(GameStatus.Win)){
	             gstatus.setText("You win!");
		    }
			
			boardUpdate();
			printOutStatus();
			
		    if (game.playing){
			     game.whiteMove();
		         if (game.getStatus().equals(GameStatus.Lose)){
				       gstatus.setText("You lose!");
		         }
		         
				
			    boardUpdate();
			    
			
		    }
		success = false;
		}
	}
	
	public void reset(){
		int k =2;
		int b =2;
		int r =2;
		Map<Position,Type> newGenerator = this.generateMinePositions(k,b,r);
		
		this.game = new GamePlay(newGenerator, k+b+r);
		gstatus.setText("is Running...");
		success = false;
		black_mode = Mode.Displacement;
	
		
		boardUpdate();
		this.repaint();
		// Make sure that this component has the keyboard focus
	    requestFocusInWindow();
	}
	
	public void printTileStatus(Tile t){
		if (t != null ){
			StatusOfTile t_s = t.getStatus();
			switch (t_s){
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
	
	public void printOutStatus(){
		
		for (int j =0; j< 8; j++){
		   for (int i=0; i< 8; i++){
			    this.printTileStatus(tileArray[i][j]);
			
		    }
		   System.out.println();
		}
		
	}
	
	public void setMode(Mode mode){
		black_mode = mode;
		requestFocusInWindow();
	}
	
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
	}
	
	
	/** Generate random positions for hidding pieces
	 * 
	 * @param min = 0,
	 * @param max = 7,
	 * @return random integer and random positions.
	 */
	public int randomInteger(int min, int max){
		Random rand = new Random();
		int randomNum =  rand.nextInt((max -min)+1)+ min;
		return randomNum;
	}
	
	public Position randomPosition(int min, int max){
		int rand_x = randomInteger(min, max);
		int rand_y = randomInteger(min, max);
		return new Position(rand_x, rand_y);
	}
	
	public TreeMap<Position, Type> generateMinePositions(int k, int b, int r){
		Set<Position> initialOccupied = new TreeSet<Position>();
		initialOccupied.add(new Position(0,0));
		for (int j=7; j>4; j--){
			for (int i=7; i>4; i--){
				initialOccupied.add(new Position(i,j));
			}
		}
		
		Set<Position> k_set = new TreeSet<Position>();
		do {
			Position random = randomPosition(0,7);
			if(!initialOccupied.contains(random)){
				k_set.add(random);
				initialOccupied.add(random);
			}
			
		} while ( k_set == null || k_set.size()<k);
		Set<Position> b_set = new TreeSet<Position>();
		do {
			Position random = randomPosition(0,7);
			if(!initialOccupied.contains(random)){
				b_set.add(random);
				initialOccupied.add(random);
			}
			
		} while ( b_set == null || b_set.size()<b);
		Set<Position> r_set = new TreeSet<Position>();
		do {
			Position random = randomPosition(0,7);
			if(!initialOccupied.contains(random)){
				r_set.add(random);
				initialOccupied.add(random);
			}
			
		} while ( r_set == null || r_set.size()<r);
		TreeMap<Position, Type> map = new TreeMap<Position, Type>();
		for (Position pos : k_set){
			map.put(pos, Type.Knight);
		}
		for (Position pos: b_set){
			map.put(pos, Type.Bishop);
		}
		for (Position pos: r_set){
			map.put(pos, Type.Rook);
		}
		return map; 
		
		
	}
	
	public void printOutMinePositions(Map<Position, Type> mineGenerator){
		 for (Position pos : mineGenerator.keySet()){
			 System.out.print(pos.toString());
			 System.out.println();
		 }
	}

}
