package s260469756.mytools;

import halma.CCBoard;
import halma.CCMove;


public class State {
	public CCBoard board;
	public CCMove lastMove;
	public State lastState;
	
	public State() {
		board = null;
		lastMove = null;
		lastState = null;
	}
	
	public State(CCBoard b, CCMove m, State s) {
		board = b;
		lastMove = m;
		lastState = s;
	}
		
	public State generateNextState(CCMove m) {
		State s = new State((CCBoard) board.clone(), m, this);
		s.board.move(m);
		return s;
	}

}
