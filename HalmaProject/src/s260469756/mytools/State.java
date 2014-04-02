package s260469756.mytools;

import halma.CCBoard;
import halma.CCMove;

import java.util.ArrayList;

public class State {
	public CCBoard board;
	public ArrayList<CCMove> movesToState;
	//public int evaluation = -1;
	
	public State() {
		board = null;
		movesToState = new ArrayList<CCMove>();
	}
	
	public State(CCBoard b, CCMove withMove) {
		board = (CCBoard) b.clone();
		board.move(withMove);
		movesToState = new ArrayList<CCMove>();
		movesToState.add(withMove);
	}
	
	public State(CCBoard b, ArrayList<CCMove> moves) {
		board = b;
		movesToState = moves;
	}
	
	@Override
	public Object clone() {
		return new State((CCBoard) board.clone(), (ArrayList<CCMove>) movesToState.clone());
	}
}
