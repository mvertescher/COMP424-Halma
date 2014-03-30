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
				return getBestMove(board.getLegalMoves()); 
			}
	
		}
		
		// Middle of turn, i.e. there was a hop
		else {
			return new CCMove(myID,null,null); 
		}
		
		
		return null;
	}
	
	public static CCMove getBestMove(ArrayList<CCMove> moves) {
		CCMove bestMove = moves.get(0); 
        double bestDist = 50; // Big num
        for (CCMove move : moves) { // For every move
        	Point to = move.getTo();
        	if (!isInGoal(move.getFrom())) { // If the move is not at the goal 
        		double d = to.distance(goal);
        		if (d < bestDist)  {
        			bestDist = d;
        			bestMove = move;
        		}
        		if (d == bestDist && move.isHop()) // Rate hops over single moves
        			bestMove = move;
        	}
        }
		return bestMove; 
	}
	
	public static int evaluateBoard(CCBoard board) {
		return 0;
	}
	
	public static void startTurnTimer() {
		turnTimer = System.currentTimeMillis();
	}
	
	public static boolean turnTimerUp() {
		if ((System.currentTimeMillis() - turnTimer) > 990) 
			return true; 
		return false;
	}
	
	public static double getSomething(){
		return Math.random();
	}
}
