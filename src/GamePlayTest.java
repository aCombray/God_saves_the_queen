import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

public class GamePlayTest {
	 
	private GamePlay newgame;
	
	@Before
	public void setUp(){
		Map<Position,Type> mineGenerator = new TreeMap<Position, Type>();
		mineGenerator.put(new Position(3,4), Type.Knight);
		mineGenerator.put(new Position(2,2), Type.Bishop);
		newgame = new GamePlay(mineGenerator,6);
	}
    /**
     * The first test tests displacement.
     */
	@Test
	public void DisplacementTest() {
		//newgame.printOutGameStatus();
		assertFalse("can't move up", newgame.blackDisplacement(Direction.UP));
		assertTrue("move right", newgame.blackDisplacement(Direction.RIGHT));
		//newgame.printOutGameStatus();
		assertTrue("move down", newgame.blackDisplacement(Direction.DOWN));
		//newgame.printOutGameStatus();
		assertEquals(ChessColor.White, newgame.getWhoseTurn());
	}
	
	/**
	 * The second test tests recruit. 
	 * 
	 */
	@Test
	public void RecruitTest(){
		assertEquals(1, newgame.hidenBishop());
		assertEquals(1, newgame.hidenKnight());
		assertEquals(0, newgame.hidenRook());
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.RIGHT);
		//newgame.printOutGameStatus();
		newgame.blackRecruit(new Position(2,2));
		assertEquals(1,newgame.bishopCarriedByBlack());
		assertEquals(0,newgame.knightsCarriedByBlack());
		assertEquals(1,newgame.hidenKnight());
		assertEquals(0,newgame.hidenBishop());
		assertEquals(5,newgame.getCoinNumber());
		//newgame.printOutGameStatus();
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.DOWN);
		assertEquals(0,newgame.knightsCarriedByBlack());
		assertFalse("blocked by the knight", newgame.blackDisplacement(Direction.DOWN));
		//newgame.printOutGameStatus();
		newgame.blackDisplacement(Direction.LEFT);
		newgame.whiteMove();
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		//newgame.printOutGameStatus();
		newgame.whiteMove();
		//newgame.printOutGameStatus();
	}
	
	@Test
	public void AttackTest(){
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackRecruit(new Position(2,2));
	    newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.DOWN);
	    newgame.blackDisplacement(Direction.LEFT);
		//newgame.printOutGameStatus();
		assertTrue("attack (3,4)", newgame.blackAttack(Type.Bishop, new Position(3,4)));
		//newgame.printOutGameStatus();
		assertEquals(0,newgame.bishopCarriedByBlack());
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.printOutGameStatus();
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.whiteMove();
		newgame.printOutGameStatus();
		assertEquals(GameStatus.Lose, newgame.getStatus());
	}
	
	@Test
	public void KnightAttackTest(){
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.RIGHT);
		assertEquals(2,newgame.numOfAdjacentMines(new Position (3,3)) );
		assertEquals(1, newgame.numOfAdjacentMines(new Position(3,1)));
		assertTrue("Recruit Knight at (3,4)", newgame.blackRecruit(new Position(3,4)));
		//newgame.printOutGameStatus();
		newgame.blackDisplacement(Direction.RIGHT);
		assertTrue("Knight Attack (5,5)", newgame.blackAttack(Type.Knight, new Position(5,5)));
		//newgame.printOutGameStatus();

		
	}
	
	@Test
	public void BishopTest(){
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.DOWN);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.blackDisplacement(Direction.RIGHT);
		newgame.printOutGameStatus();
		newgame.whiteMove();
		newgame.printOutGameStatus();
	}
	
	

}
