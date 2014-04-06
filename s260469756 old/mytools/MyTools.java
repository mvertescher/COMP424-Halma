package s260469756.mytools;

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
	private static long turnTimer; 
	
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
		for (Point p: goalPoints) {
			if (myID == board.getPieceAt(p))
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
	
	public static CCMove nextMove1(CCBoard board) {
		// Get the last moved piece
		Point currentPiece = board.getLastMoved();
		
		// Beginning of turn
		if (currentPiece == null) { 
			startTurnTimer();
			// Determine if there are pieces still in the base 
			if (arePointsInBase(board)) {
				
			}
			
			// No points in base, getBestMove
			else {
				return getBestMove1(board.getLegalMoves()); 
			}
	
		}
		
		// Middle of turn, i.e. there was a hop
		else {
			return new CCMove(myID,null,null); 
		}
		
		
		return null;
	}
	
	/*
	 * Choose the move that moves the closest to the goal if the piece is not already in the goal
	 */
	public static CCMove getBestMove1(ArrayList<CCMove> moves) {
		CCMove bestMove = moves.get(0); 
        //double bestDist = 50; // Big num
        int bestDist = 50; // Inf 
		for (CCMove move : moves) { // For every move
        	Point to = move.getTo();
        	if (!isInGoal(move.getFrom())) { // If the move is not at the goal 
        		//double d = to.distance(goal);
        		int d = manhattanDistanceToGoal(to);
        		if (d < bestDist)  {
        			bestDist = d;
        			bestMove = move;
        		}
        		else if (d == bestDist && move.isHop()) // Rate hops over single moves
        			bestMove = move;
        	}
        }
		return bestMove; 
	}
	
	private static int manhattanDistanceToGoal(Point p) {
		return Math.abs(goal.x - p.x) + Math.abs(goal.y - p.y);
	}
	
	
	/*
	 * 
	 */
	public static CCMove getBestMove2(ArrayList<CCMove> moves) {
		CCMove bestMove = moves.get(0); 
        //double bestDist = 50; // Big num
        int bestDist = 50; // Inf 
		for (CCMove move : moves) { // For every move
			Point from = move.getFrom();
			Point to = move.getTo();
        	if (!isInGoal(move.getFrom())) { // If piece is not at the goal 
        		// Want to move toward the goal
        		if (manhattanDistanceToGoal(to) <= manhattanDistanceToGoal(from)) {
        			
        			
        			
        		}
        	
        		
        		//double d = to.distance(goal);
        		int d = manhattanDistanceToGoal(to);
        		if (d < bestDist)  {
        			bestDist = d;
        			bestMove = move;
        		}
        		else if (d == bestDist && move.isHop()) // Rate hops over single moves
        			bestMove = move;
        	}
        }
		return bestMove; 
	}
	
	
	
	public static boolean isEndTurnMove(CCMove move) {
		return (move.getTo() == null && move.getFrom() == null);
	}
	
	public static ArrayList<CCMove> getMovesForTurn(Board theboard) {	
		// Determine the root board
		CCBoard root = (CCBoard) theboard.clone();
		// Get the root moves
		ArrayList<CCMove> rootMoves = root.getLegalMoves();
		int numStates = rootMoves.size();
		// Filter the moves (optional)
		
		// Create the first level boards
		ArrayList<CCBoard> firstLevelBoards = new ArrayList<CCBoard>();
		ArrayList<CCMove> firstLevelMoves = new ArrayList<CCMove>();
		for (CCMove m : rootMoves) {
			CCBoard child = (CCBoard) root.clone();
			child.move(m);
			firstLevelMoves.add(m);
			firstLevelBoards.add(child); 
		}
		
		// Find the best board at the first level
		CCBoard bestBoard = firstLevelBoards.get(0);
		CCMove bestMove = firstLevelMoves.get(0);
		CCBoard tempBoard;
		CCMove tempMove; 
		int bestEval = 0;
		int tempEval;
		for (int index = 0; index < numStates; index++) {
			tempBoard = firstLevelBoards.get(index);
			tempMove = firstLevelMoves.get(index);
			tempEval = evaluateBoard(myID, tempBoard);
			if (tempEval > bestEval) {
				bestBoard = tempBoard;
				bestMove = tempMove;
				bestEval = tempEval;
			}
		}
		
		ArrayList<CCMove> turnMoves = new ArrayList<CCMove>();
		turnMoves.add(bestMove);
		
		// Calculate the next move toward the goal 
		//bestBoard.move(bestMove);
		
		CCMove nextMove = nextBestMoveForBoard(bestBoard);
		turnMoves.add(nextMove);
		if (nextMove.getTo() == null && nextMove.getFrom() == null)
			return turnMoves;
		bestBoard.move(nextMove);
		
		while (nextMove.getTo() != null && nextMove.getFrom() != null) {
			System.out.println("NextMove = " + nextMove.toPrettyString()); // inf loop
			nextMove = nextBestMoveForBoard(bestBoard);	
			turnMoves.add(nextMove);
			bestBoard.move(nextMove);
		}
		
		
		// Add end turn move
		//turnMoves.add(new CCMove(myID,null,null));
		
		
		return turnMoves;
	}
	
	private static CCMove nextBestMoveForBoard(CCBoard board) {
		
		ArrayList<CCMove> rootMoves = board.getLegalMoveForPiece(board.getLastMoved(), myID);
		if (rootMoves.isEmpty()) { 
			return new CCMove(myID,null,null);
		}
		
		
		int currentEval = evaluateBoard(myID, board);
		int numStates = rootMoves.size();
		// Filter the moves (optional)
		
		// Create the first level boards
		ArrayList<CCBoard> firstLevelBoards = new ArrayList<CCBoard>();
		ArrayList<CCMove> firstLevelMoves = new ArrayList<CCMove>();
		for (CCMove m : rootMoves) {
			CCBoard child = (CCBoard) board.clone();
			child.move(m);
			firstLevelMoves.add(m);
			firstLevelBoards.add(child); 
			System.out.println("nextBestMoveForBoard(): board added");
		}
		
		// Find the best board at the first level
		CCBoard bestBoard = firstLevelBoards.get(0);
		CCMove bestMove = firstLevelMoves.get(0);
		CCBoard tempBoard;
		CCMove tempMove; 
		int bestEval = 0;
		int tempEval;
		for (int index = 0; index < numStates; index++) {
			tempBoard = firstLevelBoards.get(index);
			tempMove = firstLevelMoves.get(index);
			tempEval = evaluateBoard(myID, tempBoard);
			if (tempEval > bestEval) {
				bestBoard = tempBoard;
				bestMove = tempMove;
				bestEval = tempEval;
			}
		}
		
		if (bestEval <= currentEval)
			return new CCMove(myID,null,null);
		return bestMove;
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
	
	
	
	
	public static ArrayList<CCMove> minMax(Board theboard) {
		
		CCBoard rootBoard = (CCBoard) theboard.clone();
		//MinMaxTreeNode root = new MinMaxTreeNode(null,rootBoard,mmEval(myID,rootBoard));
		
		//ArrayList<CCBoard> nextStates = getBoardStates(new ArrayList<CCBoard>(), rootBoard);
		
		// To build the first level
		// add all legal moves, 
		// for every legal hop, add all other legal hops from point
		
		ArrayList<State> firstStates = buildFirstStates(rootBoard);
		System.out.println("NUMBER OF FIRST LEVEL STATES CALCULATED: " + firstStates.size());
		ArrayList<CCMove> moves = getMovesToBestState(firstStates);
		moves.add(new CCMove(myID,null,null)); // Add the end turn move
		
		//System.out.println("CHOSEN MOVES : ");
		//for (CCMove m : moves)
			//System.out.println("      " + m.toPrettyString());
		
		return moves;
	}
	
	public static ArrayList<CCBoard> getBoardStates(ArrayList<CCBoard> states, CCBoard root) {
		ArrayList<CCMove> moves = root.getLegalMoves();
		for (CCMove m : moves) {
			if (!isEndTurnMove(m)) {
				CCBoard tempBoard = (CCBoard) root.clone();
				tempBoard.move(m);
				states.add(tempBoard);
				getBoardStates(states,tempBoard);
			}
		}	
		return states;
	}
	
	
	
	public static ArrayList<BMPair> buildFirstLevel(CCBoard root) {
		ArrayList<BMPair> states = new ArrayList<BMPair>(); 
		ArrayList<CCMove> firstMoves = root.getLegalMoves();
		ArrayList<CCMove> hopMoves = new ArrayList<CCMove>();
		BMPair pair;
		CCBoard nextBoard; 
		for (CCMove m : firstMoves) {
			if (!m.isHop()) {
				pair = new BMPair();
				nextBoard = (CCBoard) root.clone();
				nextBoard.move(m);
				pair.board = nextBoard;
				pair.moves.add(m);
				states.add(pair);
			}
			// If the move is a hop 
			else 
				hopMoves.add(m);
		}
		
		ArrayList<BMPair> hopStates = null;
		for (CCMove hm : hopMoves) { 
			//hopStates
			states.addAll(hopStates);
		}
		
		return states;
	}
	
	public static ArrayList<State> buildFirstStates(CCBoard root) {
		ArrayList<State> states = new ArrayList<State>(); 
		ArrayList<CCMove> firstMoves = root.getLegalMoves();
		State s;
		CCBoard nextBoard; 
		for (CCMove m : firstMoves) {
			if (!m.isHop()) {
				s = new State();
				nextBoard = (CCBoard) root.clone();
				nextBoard.move(m);
				s.board = nextBoard;
				s.movesToState.add(m);
				states.add(s);
			}
			// If the move is a hop 
			else {
				State temp = new State(root,m); // BUG, getting more states than needed
				states.addAll(getHopStates(temp,m));
			}
		}
		
		return states;
	}
	
	
	public static ArrayList<State> getHopStates(State root, CCMove lastMove) {
		ArrayList<State> statesQueue = new ArrayList<State>();
		State currentState; 
		ArrayList<Point> visited = new ArrayList<Point>();
		
		int index = 0;
		statesQueue.add(root);
		visited.add(lastMove.getFrom());
		
		
		while (index != statesQueue.size()) {
			currentState = statesQueue.get(index);
			index++;
						
			ArrayList<CCMove> legalMoves = currentState.board.getLegalMoveForPiece(currentState.board.getLastMoved(), myID); 
			
			for (CCMove m : legalMoves) {
				if (m.isHop() && !visited.contains(m.getTo())) {
					State newState = (State) currentState.clone();
					newState.board.move(m);
					newState.movesToState.add(m);
					statesQueue.add(newState);
					visited.add(m.getTo());
				}
			}
		}
		return statesQueue;
	}

	
	public static void addMultihopStates(ArrayList<BMPair> bmpairs, CCBoard board, BMPair lastPair, CCMove lastMove, ArrayList<Point> visited) {
		ArrayList<CCMove> moves = board.getLegalMoveForPiece(lastMove.getTo(), myID);
		ArrayList<CCMove> pairMoves = (ArrayList<CCMove>) lastPair.moves.clone();
		BMPair newPair;
		CCBoard nextBoard;
		for (CCMove m : moves) {
			if (!isEndTurnMove(m) && !visited.contains(m.getTo())) { 
				nextBoard = (CCBoard) board.clone();
				nextBoard.move(m);				
				newPair = new BMPair();
				newPair.board = nextBoard;
				pairMoves.add(m);
				newPair.moves = pairMoves;
				bmpairs.add(newPair);
				visited.add(m.getTo());
				if (m.isHop()) {
					System.out.println("Multihop move: " + m.toPrettyString());
					addMultihopStates(bmpairs, nextBoard, newPair, m,visited);
				}
			}	
		}
		
	}
	
	/*
	public static ArrayList<BMPair> buildFirstLevel(ArrayList<BMPair> bmpairs, CCBoard root) {
		ArrayList<CCMove> moves; 
		BMPair lastPair;
		ArrayList<CCMove> lastMoves;
		Point from = null;
		CCMove lastMove = null;
		
		if (bmpairs.isEmpty()) { // First iteration 
			lastPair = new BMPair();
			moves = root.getLegalMoves();
		}
		else {
			lastPair = bmpairs.get(bmpairs.size() - 1);
			lastMoves = lastPair.moves;
			lastMove = lastMoves.get(lastMoves.size() - 1); 
			moves = root.getLegalMoveForPiece(lastMove.getTo(), myID);
		}	
			
		for (CCMove m : moves) {
			if (lastMove != null)
				from = lastMove.getFrom();
			if (!isEndTurnMove(m) && !m.getTo().equals(from)) {
				CCBoard tempBoard = (CCBoard) root.clone();
				tempBoard.move(m);
				//lastPair = new BMPair();
				
				lastPair.board = tempBoard;
				lastPair.moves.add(m);
				bmpairs.add(lastPair);
				if (m.isHop())
					buildFirstLevel(bmpairs,tempBoard);
			}
		}	
		return bmpairs;
	}*/
	
	public static ArrayList<CCMove> getMovesToBestState(ArrayList<State> states) {
		State bestState = states.get(0); // init
		int bestEval = 0; 
		for (State s : states) {
			CCMove firstMove = s.movesToState.get(0);
			Point endPoint = s.board.getLastMoved();
			int eval = mmEval(myID,s.board);
			
			// Lower eval if moving in goal
			//if (isInGoal(firstMove.getFrom())) 
				//eval -= 2;
			
			// Lower eval if piece is moving away from center diagnal
			if (endPoint != null)
				eval -= manhattanDistanceToDiagonal(endPoint); // * 2
				
			if (eval > bestEval || (eval == bestEval && firstMove.isHop())) {
				bestEval = eval;
				bestState = s;
				
				// If the next state moves a piece into the goal, take it.
				CCMove finalMove = s.movesToState.get(s.movesToState.size() - 1);
				if (!isInGoal(firstMove.getFrom()) && isInGoal(finalMove.getTo()))
					return s.movesToState;
			}

			
		}
		return bestState.movesToState;
	}
	
	
	
	/*
	 * Evaluate a particular board state.
	 * Better boards have a higher evaluation.
	 */
	public static int mmEval(int player_id, CCBoard board) {
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
		return Math.abs(p.x - p.y);
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
	
	public static ArrayList<CCBoard> fastStates(CCBoard root) {
		ArrayList<CCBoard> states = new ArrayList<CCBoard>(); 
		CCBoard board, temp;
		ArrayList<CCMove> moves, pointMoves;
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> pieces = root.getPieces(myID);
		
		for (Point p : pieces) {
			moves = root.getLegalMoveForPiece(p, myID);
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
							pointMoves = board.getLegalMoveForPiece(board.getLastMoved(), myID);
							for (CCMove m : pointMoves) {
								if (m.isHop() && !visited.contains(m.getTo())) {
									temp = (CCBoard) root.clone();
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
		
		return null;
	}
	
	
	
	
	
	/* Timers, not needed */
	
	public static void startTurnTimer() {
		turnTimer = System.currentTimeMillis();
	}
	
	public static boolean turnTimerUp() {
		if ((System.currentTimeMillis() - turnTimer) > 990) 
			return true; 
		return false;
	}




}

