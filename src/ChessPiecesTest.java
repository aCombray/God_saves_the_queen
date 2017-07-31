import static org.junit.Assert.*;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class ChessPiecesTest {

	@Test
	public void test() {
		Set<Position> revealedBoard = new TreeSet<Position>();
		for (int i= 0; i < 3; i++){
			for (int j =0;j<5; j++ ){
				Position p = new Position(i,j);
				revealedBoard.add(p);
			}
		
		}
		revealedBoard.add(new Position(3,3));
		Knight n = new Knight(new Position(2,2), ChessColor.Black);
		Pawn p1 = new Pawn(new Position(3,3));
		Pawn p2 = new Pawn(new Position(1,4));
		Bishop b = new Bishop(new Position(1,1), ChessColor.White);
		assertFalse("p1 is not n", n.equals(p1));
		assertFalse("p1 is not attacked by n", n.inThePath(p1, revealedBoard));
		assertTrue("n is attacked by p1", p1.inThePath(n, revealedBoard));
		Position pos = new Position(2,2);
		Position pos2 = new Position(2,4);
		Position pos3 = new Position(2,3);
		Set<Position> pset = new TreeSet<Position>();
		pset.add(pos);
		pset.add(pos2);
		Pawn p3 = new Pawn(pos2);
		assertEquals(pset, p1.getAttackRange(revealedBoard));
		assertTrue("p2 is attacked by n", n.inThePath(p2, revealedBoard));
		revealedBoard.remove(pos3);
		Rook r1 = new Rook(new Position(2,1), ChessColor.Black);
		assertFalse("p3 is attacked by r1", r1.inThePath(p3, revealedBoard));
		assertFalse("n is attacked by r1", r1.inThePath(n, revealedBoard));
		assertTrue("b is attacked by r1", r1.inThePath(b, revealedBoard));
		assertTrue("n is attacked by b", b.inThePath(n, revealedBoard));
		Position q = new Position(7,0);
		Set<Position> adj = new TreeSet<Position>();
		adj.add(new Position(6,0));
		adj.add(new Position(6,1));
		adj.add(new Position(7,1));
		assertEquals(adj, q.getAdjacentPositions());

	}

	

}
