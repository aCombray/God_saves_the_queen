import java.util.LinkedList;

public class WhitePlayer extends Player {
    public WhitePlayer(LinkedList<Piece> startList) {
		super(ChessColor.White);
		super.m_pieces = startList;
	}
}
