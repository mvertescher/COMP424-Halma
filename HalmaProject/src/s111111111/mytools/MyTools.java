package s111111111.mytools;

import halma.CCBoard;
import halma.CCMove;

import java.awt.Point;
import java.util.ArrayList;

import boardgame.Board;

public class MyTools {

	final static Point[] base0Points={
		new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0),
		new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1),
		new Point(0,2), new Point(1,2), new Point(2,2), new Point(0,3), new Point(1,3)};
	final static Point[] base2Points={
		new Point(0,15), new Point(1,15), new Point(2,15), new Point(3,15),
		new Point(0,14), new Point(1,14), new Point(2,14), new Point(3,14),
		new Point(0,13), new Point(1,13), new Point(2,13), new Point(0,12), new Point(1,12)};
	final static Point[] base1Points={
		new Point(15,0), new Point(14,0), new Point(13,0), new Point(12,0),
		new Point(15,1), new Point(14,1), new Point(13,1), new Point(12,1),
		new Point(15,2), new Point(14,2), new Point(13,2), new Point(15,3), new Point(14,3)};
	final static Point[] base3Points={
		new Point(15,15), new Point(14,15), new Point(13,15), new Point(12,15),
		new Point(15,14), new Point(14,14), new Point(13,14), new Point(12,14),
		new Point(15,13), new Point(14,13), new Point(13,13), new Point(15,12), new Point(14,12)};
	
	private static Point base;
	private static Point goal;
	private static Point[] basePoints; 
	private static Point[] goalPoints;
	
	private static int myID;
	
	/* WORKS */
	public static void initCorner(int playerID) {
    	myID = playerID;
		switch (playerID) { // Determine starting corner
			case 0: // Top left
				base = new Point(0,0);
				goal = new Point(15,15);
				basePoints = base0Points;
				goalPoints = base3Points;
				break;
			case 1: // Bottom left
				base = new Point(15,0);
				goal = new Point(0,15);
				basePoints = base1Points;
				goalPoints = base2Points;
				break;
			case 2: // Top right
				base = new Point(0,15);
				goal = new Point(15,0);
				basePoints = base2Points;
				goalPoints = base1Points;
				break;
			case 3: // Bottom right
				base = new Point(15,15);
				goal = new Point(0,0);
				basePoints = base3Points;
				goalPoints = base0Points;
				break;
    	}
	}
	
	/* WORKS */
	public static boolean arePointsInBase(CCBoard board) {
		//System.out.println("ARE POINTS IN BASE!!");
		Integer player_id; 
		for (Point p : basePoints) {
			//System.out.println("SHOULD BE 13 of theses");
			player_id = board.getPieceAt(p);
			if (player_id != null && player_id == myID)
				return true; 
		}	
		return false; 
	}
	
	/* Works */
	public static ArrayList<Point> getPointsInBase(CCBoard board) {
		ArrayList<Point> pointsInBase = new ArrayList<Point>();
		Integer player_id; 
		for (Point p: basePoints) {
			player_id = board.getPieceAt(p);
			if (player_id != null && player_id == myID)
				 pointsInBase.add(p);
		}
		return pointsInBase; 
	}
		
	/* WORKS */
	public static ArrayList<CCMove> getMovesForPoints(CCBoard board, ArrayList<Point> points) {
		ArrayList<CCMove> moves = new ArrayList<CCMove>();
		for (Point p : points) 
			moves.addAll(board.getLegalMoveForPiece(p, myID));
		return moves;
	}
	
	
	
	
	public static int pointsInGoal(CCBoard board) {
		int inGoal = 0;
		Integer player_id; 
		for (Point p: goalPoints) {
			player_id = board.getPieceAt(p);
			if (player_id != null && player_id == myID)
				inGoal += 1; 
		}	
		return inGoal; 
	}
	
	public static boolean isInGoal(Point piece) {
		for (Point p : goalPoints) {
			if (p.equals(piece))
				return true;
		}
		return false; 
	}
	

	
	private static int manhattanDistanceToGoal(Point p) {
		return Math.abs(goal.x - p.x) + Math.abs(goal.y - p.y);
	}
	
	
	public static boolean isEndTurnMove(CCMove move) {
		return (move.getTo() == null && move.getFrom() == null);
	}
	
	
	// 15*8+14*8+13*6+12*4
	private static int MHD = 358;
	
	public static int evaluateBoard(int player_id, CCBoard board) {
		if (player_id == myID) {
			ArrayList<Point> myPieces = board.getPieces(myID);
			int sum = 0; 
			for (Point p : myPieces) {
				sum += manhattanDistanceToGoal(p);
			}
			
			return MHD - sum;
		}
		return 0;
	}
	
	

	
	
	/*
	 * Evaluate a particular board state.
	 * Better boards have a higher evaluation.
	 */
	public static int evaluate(int player_id, CCBoard board) {
		if (player_id == myID) {
			ArrayList<Point> myPieces = board.getPieces(myID);
			int sum = 0; 
			for (Point p : myPieces) {
				sum += manhattanDistanceToGoal(p);
				//sum += manhattanDistanceToDiagonal(p);
			}
			
			return MHD - sum;
		}
		return 0;
		
	}


	
	
	
	
	
	
	public static int manhattanDistanceToDiagonal(Point p) {
		if (myID == 0 || myID == 3)
			return Math.abs(p.x - p.y);
		return Math.abs(15 - (p.x + p.y));
	}
	
	
	/*
	 * Core function, get the moves to the optimal state for the given turn.
	 */
	public static ArrayList<CCMove> fastGetMovesForTurn(Board theboard) {
		CCBoard rootBoard = (CCBoard) theboard.clone();
		ArrayList<State> firstStates = fastStates((CCBoard) rootBoard.clone());
		System.out.println("NUMBER OF FIRST LEVEL STATES CALCULATED: " + firstStates.size());
		
		//firstStates.get(0).board.move(new CCMove(myID,null,null));
		//for (CCMove m : firstStates.get(0).board.getLegalMoves())
		//	System.out.println("	FIRSTSTATES(0) LEGAL MOVE: " + m.toPrettyString());
		
		System.out.println("About to do DFS... ");
		//State bestState = levelOneBestState(firstStates);
		State bestState = dfsBestState((CCBoard) rootBoard.clone(), firstStates);
		
		
		ArrayList<CCMove> moves = fastGetMovesToState(bestState);
		moves.add(new CCMove(myID,null,null)); // Add the end turn move
		
		//System.out.println("CHOSEN MOVES : ");
		//for (CCMove m : moves)
			//System.out.println("      " + m.toPrettyString());
		
		return moves;
	}
	
	/*
	 * Hop moves states need to have their turns ended
	 */
	public static ArrayList<State> fastStates(CCBoard root) {
		ArrayList<State> states = new ArrayList<State>(); 
		ArrayList<CCMove> moves, pointMoves;
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> pieces = root.getPieces(myID);
		
		State rootState = new State(root,null,null);
		State lastState;
				
		for (Point p : pieces) {
			moves = root.getLegalMoveForPiece(p, myID);
			visited.add(p); // Add the initial point for the move as visited
			for (CCMove move : moves) {
				if (!visited.contains(move.getTo())) {
					if (!move.isHop()) {
						states.add(rootState.generateNextState(move));
						visited.add(move.getTo());
					}
					else { // Hop Move
						int queueHead = states.size();
						
						lastState = rootState.generateNextState(move);
						states.add(lastState);
						visited.add(move.getTo());
						
						while (queueHead != states.size()) {
							lastState = states.get(queueHead);
							queueHead += 1;
							pointMoves = lastState.board.getLegalMoveForPiece(lastState.board.getLastMoved(), myID);
							//lastState.board.move(new CCMove(myID,null,null)); // End turn for hop move
							for (CCMove m : pointMoves) {
								if (m.isHop() && !visited.contains(m.getTo())) {
									states.add(lastState.generateNextState(m));
									visited.add(m.getTo());
								}
							}	
							// Done with last state, end its turn
							lastState.board.move(new CCMove(myID,null,null)); 
						}
					}
				}
			}
			visited.clear();
		}
		
		return states;
	}
	
	public static State levelOneBestState(ArrayList<State> states) {
		State bestState = states.get(0); // init
		int bestEval = 0; 
		for (State s : states) {
			CCMove finalMove = s.lastMove;
			Point endPoint = s.lastMove.getTo(); //s.board.getLastMoved();
			int eval = evaluate(myID,s.board);
			
			// Lower eval if moving in goal
			//if (isInGoal(firstMove.getFrom())) 
				//eval -= 2;
			
			// Lower eval if piece is moving away from center diagnonal
			if (endPoint != null) {
				eval -= manhattanDistanceToDiagonal(endPoint); // * 2
				//System.out.println("DIAG EVAL ::::: " + eval);
			}
			
			if (eval > bestEval || (eval == bestEval && finalMove.isHop())) {
				bestEval = eval;
				bestState = s;
				
				// If the next state moves a piece into the goal, take it.
				//CCMove finalMove = s.movesToState.get(s.movesToState.size() - 1);
				//if (!isInGoal(firstMove.getFrom()) && isInGoal(finalMove.getTo()))
				//	return fastGetMovesToState(State state);
			}

			
		}
		return bestState;
	}
	
	public static ArrayList<CCMove> fastGetMovesToState(State state) {
		State currentState = state;
		ArrayList<CCMove> movesToState = new ArrayList<CCMove>();
		
		while (currentState != null) {
			movesToState.add(0,currentState.lastMove);
			currentState = currentState.lastState;
			//System.out.println("Getting moves to state ");
		}
		movesToState.remove(0);
		
		return movesToState;
	}
	
	
	
	
	protected static State dfsBestState(CCBoard rootBoard, ArrayList<State> states) {
		ArrayList<State> goodStates = new ArrayList<State>();
		int rootEval = evaluate(myID,rootBoard);
		// Get all states that have a better eval 
		for (State s : states) {
			int eval = evaluate(myID, s.board);
			if (eval >= rootEval) {
				goodStates.add(s);
			}
			
		}
		

		
		// DFS
		int bestValue = 0;
		State bestState = goodStates.get(0);
		for (State s : goodStates) {
			int temp = subtreeValue((CCBoard) s.board.clone()); 
			if (temp > bestValue) {
				bestValue = temp;
				bestState = s;
			}
		}
		
		return bestState;
	}
	
	
	protected static int subtreeValue(CCBoard board) {
		int currentID = (myID + 1) % 4;
		
		// Should be fixed 
		//ArrayList<CCMove> moves  = board.getLegalMoves();
		//if (isEndTurnMove(moves.get(0)))
		//	board.move(new CCMove(myID,null,null)); // End turn for hop move
		
		ArrayList<CCBoard> nextBoards = fastBoards(currentID,board);

		
		System.out.println("NextBoards size = " + nextBoards.size());
		CCBoard level2BestBoard = bestBoard(currentID, nextBoards);
		return (playerEvaluate(myID,board) - playerEvaluate(currentID,level2BestBoard));
	}
	
	/*
	 * Evaluate a particular board state.
	 * Better boards have a higher evaluation.
	 */
	public static int playerEvaluate(int player_id, CCBoard board) {
		ArrayList<Point> myPieces = board.getPieces(player_id);
		int sum = 0, diag = 0; 
		Point goalPoint = null; 
		//Point lastMoved = board.getLastMoved();
		switch(player_id) {
			case 0:	
				goalPoint = new Point(15,15);
				//sum = -Math.abs(lastMoved.x - lastMoved.y);
				break;
			case 1:	
				goalPoint = new Point(0,15);
				//sum = -Math.abs(15 - (lastMoved.x + lastMoved.y));
				break;
			case 2:	
				goalPoint = new Point(15,0);
				//sum =  -Math.abs(15 - (lastMoved.x + lastMoved.y));
				break;
			case 3:	
				goalPoint = new Point(0,0);
				//sum = -Math.abs(lastMoved.x - lastMoved.y);
				break;
		}
		
		for (Point p : myPieces) {
			sum += Math.abs(goalPoint.x - p.x) + Math.abs(goalPoint.y - p.y);
			if (myID == 0 || myID == 3)
				diag -= Math.abs(p.x - p.y);
			diag -= Math.abs(15 - (p.x + p.y));
		}
		return MHD - sum - (diag / 13);
	}
	
	
	public static CCBoard bestBoard(int currentID, ArrayList<CCBoard> boards) {
		CCBoard bestBoard = boards.get(0); // init
		int bestEval = 0; 
		for (CCBoard b : boards) {
			//CCMove finalMove = s.lastMove;
			Point endPoint = b.getLastMoved();
			int eval = evaluate(currentID,b);
			
			// Lower eval if moving in goal
			//if (isInGoal(firstMove.getFrom())) 
				//eval -= 2;
			
			// Lower eval if piece is moving away from center diagnonal
			if (endPoint != null) {
				eval -= manhattanDistanceToDiagonal(endPoint); // * 2
				//System.out.println("DIAG EVAL ::::: " + eval);
			}
			
			if (eval > bestEval) {/// || (eval == bestEval && finalMove.isHop())) {
				bestEval = eval;
				bestBoard = b;
				
				// If the next state moves a piece into the goal, take it.
				//CCMove finalMove = s.movesToState.get(s.movesToState.size() - 1);
				//if (!isInGoal(firstMove.getFrom()) && isInGoal(finalMove.getTo()))
				//	return fastGetMovesToState(State state);
			}

			
		}
		return bestBoard;
	}
	
	
	
	
	/*
	 * Fast first level calculations: 
	 * 1. Get all points, pieces
	 * 2. For each piece get legal moves 
	 * 3. For each piece maintain a visited list
	 * 4. Iterate though legal moves
	 * 		a. If no hop and not visited, add state
	 * 		b. If hop and not visited, add all states from hops that have not been visited 
	 * 
	 */
	
	public static ArrayList<CCBoard> fastBoards(int currentID, CCBoard root) {
		ArrayList<CCBoard> states = new ArrayList<CCBoard>(); 
		CCBoard board, temp;
		ArrayList<CCMove> moves, pointMoves;
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> pieces = root.getPieces(currentID);
		
		for (Point p : pieces) {
			moves = root.getLegalMoveForPiece(p, currentID);
			System.out.println("Num moves for piece: " + moves.size());
			visited.add(p); // Add the initial point for the move as visited
			for (CCMove move : moves) {
				if (!visited.contains(move.getTo())) {
					if (!move.isHop()) {
						board = (CCBoard) root.clone();
						board.move(move);
						states.add(board);
						visited.add(move.getTo());
					}
					else { // Hop Move
						int queueHead = states.size();
						
						board = (CCBoard) root.clone();
						board.move(move);
						states.add(board);
						visited.add(move.getTo());
						
						while (queueHead != states.size()) {
							board = states.get(queueHead);
							queueHead += 1;
							pointMoves = board.getLegalMoveForPiece(board.getLastMoved(), currentID);
							for (CCMove m : pointMoves) {
								if (m.isHop() && !visited.contains(m.getTo())) {
									temp = (CCBoard) board.clone();
									temp.move(m);
									states.add(temp);
									visited.add(m.getTo());
								}
							}	
						}
					}
				}
			}
			visited.clear();
		}
		
		return states;
	}
	

}


