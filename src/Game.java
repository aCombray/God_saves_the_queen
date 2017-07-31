import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class Game implements Runnable {
	public void run(){
		// A frame is a top-level window
		final JFrame frame = new JFrame("God Save the Queen");
		//Status Panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.NORTH);
		status_panel.setBorder(BorderFactory.createRaisedBevelBorder());
		status_panel.setLayout(new BoxLayout(status_panel, BoxLayout.LINE_AXIS));
		final JLabel coin_num = new JLabel("#Coin: " );
		final JLabel status = new JLabel("Running...");
		final JPanel piece_status = new JPanel();
		status_panel.add(Box.createRigidArea(new Dimension(20,0)));
		status_panel.add(coin_num);
		status_panel.add(Box.createHorizontalGlue());
		status_panel.add(status);
		status_panel.add(Box.createHorizontalGlue());
		status_panel.add(piece_status);
		status_panel.add(Box.createRigidArea(new Dimension(20,0)));
		piece_status.setLayout(new GridLayout(3,1));
		final JLabel k_status = new JLabel("#Knight: ");
		final JLabel b_status = new JLabel("#Bishop: ");
		final JLabel r_status = new JLabel("#Rook: ");
		piece_status.add(k_status);
		piece_status.add(b_status);
		piece_status.add(r_status);
		//GameBoard
		final GameBoard board = new GameBoard(status,coin_num, k_status, b_status, r_status);
		frame.add(board, BorderLayout.CENTER);
		//Control Panel
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.SOUTH);
		control_panel.setBorder(BorderFactory.createRaisedBevelBorder());
		control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.LINE_AXIS));
		final JButton reset = new JButton("reset");
		final JPanel modeToolbar = new JPanel();
		final ButtonGroup group = new ButtonGroup();
		control_panel.add(Box.createRigidArea(new Dimension(20,0)));
		control_panel.add(modeToolbar);
		control_panel.add(Box.createHorizontalGlue());
		control_panel.add(reset);
		control_panel.add(Box.createRigidArea(new Dimension(20,0)));
		modeToolbar.setLayout(new GridLayout(2,2));
		
		JRadioButton recruit = new JRadioButton("Recruit");
		recruit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				board.setMode(Mode.Recruit);				
			}
			
		});
		JRadioButton knightAttack=  new JRadioButton("Knight Attack");
		knightAttack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				board.setMode(Mode.KnightAttack);				
			}
			
		});
		
		JRadioButton bishopAttack=  new JRadioButton("Bishop Attack");
		bishopAttack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				board.setMode(Mode.BishopAttack);				
			}
			
		});
		JRadioButton rookAttack =  new JRadioButton("Rook Attack");
		rookAttack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				board.setMode(Mode.RookAttack);				
			}
			
		});
		
		modeToolbar.add(recruit);
		modeToolbar.add(knightAttack);
		modeToolbar.add(bishopAttack);
		modeToolbar.add(rookAttack);
		
		group.add(recruit);
		group.add(knightAttack);
		group.add(bishopAttack);
		group.add(rookAttack);
		
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				board.reset();
				group.clearSelection();
			}
		});
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Game());
	}

}
