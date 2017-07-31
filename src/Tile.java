import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Tile extends JComponent {
	private final Position t_position;
	private StatusOfTile t_status;
	private int t_num;
	private final Dimension t_dimension = new Dimension(80,80);
	private final int img_width = 40;
	private final int img_height = 40; 
	private final int pos_x = 20;
	private final int pos_y = 20; 
	private final int distance = 5;
	private final Color darkColor = new Color(205,133,63);
	private final Color lightColor = new Color(255,228,196);
	private final Color coverColor = new Color(169,169,169);
	private final Color lineColor = new Color(128,128,128);
	//private BufferedImage img = null;
	
	public Tile(StatusOfTile status, Position position, int num){
		t_status = status;
		t_position = position;
		t_num = num;
		//img = null;
	}
	
	public void updateStatus(StatusOfTile newT) {
		t_status = newT;
	}
	
	public StatusOfTile getStatus(){
		return t_status;
	}
	
	public void updateNum(int num){
		t_num = num;
	}
	
	public Position getPos(){
		return t_position;
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/**
		if (t_status != null && t_position != null){
			if (t_status == StatusOfTile.UnRevealed) {
				g.setColor(coverColor); 
				g.fillRect(0, 0, t_dimension.width,t_dimension.height);
				g.setColor(lineColor);
				g.drawRect(0, 0, t_dimension.width, t_dimension.height);
			} else if ((t_position.getX()-t_position.getY())%2 == 0 ){
				g.setColor(lightColor);
			    g.fillRect(0, 0, t_dimension.width, t_dimension.height);
			} else {
				g.setColor(darkColor);
				g.fillRect(0, 0, t_dimension.width, t_dimension.height);
			}
			
			
			
			if (t_status!= StatusOfTile.UnRevealed && t_status != StatusOfTile.UnOccupied ){
				String img_file = null; 
				
				switch (t_status) {
		        case OccupiedByBDT: img_file = "Chess_bdt.png"; break;
		        case OccupiedByBLT: img_file = "Chess_blt.png"; break;
		        case OccupiedByNLT: img_file = "Chess_nlt.png"; break;
		        case OccupiedByNDT: img_file = "Chess_ndt.png"; break;       
		        case OccupiedByRLT: img_file = "Chess_rlt.png"; break;
		        case OccupiedByRDT: img_file = "Chess_rdt.png"; break;
		        case OccupiedByPLT: img_file = "Chess_plt.png"; break;
		        case OccupiedByQDT: img_file = "Chess_qdt.png"; break;
		        case OccupiedByGOD: img_file = "Chess_kdt.png"; break;
		        default: img_file = "Invalid"; break;
		     }
				 
			    try {
					if (img == null) {
						img = ImageIO.read(new File(img_file));
					}
				} catch (IOException e) {
					System.out.println("Internal Error:" + e.getMessage());
			 }
				
				
				g.drawImage(img, pos_x, pos_y, img_width, img_height, null);
				System.out.println(img_file);
			}
			*/
			
			
			
			
			
			
			
			if (t_status != null && t_position != null){
				if (t_status == StatusOfTile.UnRevealed) {
					
					g.setColor(coverColor); 
					g.fillRect(0, 0, t_dimension.width,t_dimension.height);
					this.setBorder(BorderFactory.createRaisedBevelBorder());
					//g.setColor(lineColor);
					//g.drawRect(0, 0, t_dimension.width, t_dimension.height);
				} else if ((t_position.getX()-t_position.getY())%2 == 0 ){
			        this.setBorder(null);
					g.setColor(lightColor);
				    g.fillRect(0, 0, t_dimension.width, t_dimension.height);
				} else {
					this.setBorder(null);
					g.setColor(darkColor);
					g.fillRect(0, 0, t_dimension.width, t_dimension.height);
				}
			
				
				
				if (t_status!= StatusOfTile.UnRevealed && t_status != StatusOfTile.UnOccupied ){
					
					
					switch (t_status) {
			        case OccupiedByBDT: g.setColor(Color.BLACK); g.drawString("B", pos_x, pos_y); break;
			        case OccupiedByBLT: g.setColor(Color.WHITE); g.drawString("B", pos_x, pos_y); break;
			        case OccupiedByNLT: g.setColor(Color.WHITE); g.drawString("N", pos_x, pos_y); break;
			        case OccupiedByNDT: g.setColor(Color.BLACK); g.drawString("N", pos_x, pos_y); break;       
			        case OccupiedByRLT: g.setColor(Color.WHITE); g.drawString("R", pos_x, pos_y); break;
			        case OccupiedByRDT: g.setColor(Color.BLACK); g.drawString("R", pos_x, pos_y); break;
			        case OccupiedByPLT: g.setColor(Color.WHITE); g.drawString("P", pos_x, pos_y); break;
			        case OccupiedByQDT: g.setColor(Color.BLACK); g.drawString("Q", pos_x, pos_y); break;
			        case OccupiedByGOD: g.setColor(Color.BLACK); g.drawString("G", pos_x, pos_y); break;
			        default: break;
			     }
			}
			
			if (t_num != 0 && t_status!= StatusOfTile.UnRevealed){
				String t_string = Integer.toString(t_num);
				g.setColor(Color.blue);
				g.drawString(t_string, pos_x + img_width+distance, pos_y+ img_height+distance);
			}
			
		}
		
	}
	
	
	@Override
	public Dimension getPreferredSize(){
		return t_dimension;
	}
	


}
