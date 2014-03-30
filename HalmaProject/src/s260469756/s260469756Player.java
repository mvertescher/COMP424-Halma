package s260469756;

import halma.CCBoard;
import halma.CCMove;

import java.util.ArrayList;
import java.util.Random;

import s260469756.mytools.MyTools;

import java.awt.Point;

import boardgame.Board;
import boardgame.Move;
import boardgame.Player;

/**
 *A random Halma player.
 */
public class s260469756Player extends Player {
    Random rand = new Random();
    
    /** Provide a default public constructor */
    public s260469756Player() { super("260469756"); }
    public s260469756Player(String s) { super(s); }
    
    public Board createBoard() { return new CCBoard(); }

    private Point start; 
    private Point goal;

    
    public void gameStarted( String msg ) { 
    	System.out.println( "Game started: " + msg ); 
    	
    	System.out.println( "Getting some info. " );
    	
    	switch (this.playerID) { // Determine starting corner
    		case 0:
    			start = new Point(0,0);
    			goal = new Point(15,15);
    			break;
    		case 1:
    			start = new Point(0,15);
    			goal = new Point(15,0);
    			break;
    		case 2:
    			start = new Point(15,0);
    			goal = new Point(0,15);
    			break;
    		case 3: // Bottom right
    			start = new Point(15,15);
    			goal = new Point(0,0);
    			break;
    	}
    	
    }
    
    
    /** Implement a very stupid way of picking moves */
    public Move chooseMove(Board theboard) 
    {
        // Cast the arguments to the objects we want to work with
        CCBoard board = (CCBoard) theboard;

        // Get the list of legal moves.
        ArrayList<CCMove> moves = board.getLegalMoves();
        
        int player_id = this.playerID;
        
        ArrayList<Point> pieces = board.getPieces(player_id);
        
        /*
        System.out.println("My pieces: ");
        for (Point p : pieces) {
        	System.out.print("("+p.x+","+p.y+")  ");
        }
        System.out.println();
        
        System.out.println("Player: " + player_id);
        */
               
        //System.out.println("My move: " + this.getName());
       
        //printMoves(moves);
        
        CCMove bestMove = moves.get(0); 
        double bestDist = 50; // Big num
        for (CCMove move : moves) { // For every move
        	Point to = move.getTo();
        	if (to == null) // Check if there is a null point 
        		return new CCMove(player_id,null,null);
        	
        	if ( move.getFrom().x > 3 || move.getFrom().y > 3) { // If the move is not at the goal 
        		double d = Point.distance(to.x, to.y, goal.x, goal.y);
        		System.out.println("Trying move : " + move.toPrettyString() + " dist = " + d);
        		//System.out.println(d);
        		if (d < bestDist)  {
        			bestDist = d;
        			bestMove = move;
        		}
        	}
        	
        }
        System.out.println("Best move : " + bestMove.toPrettyString() + " dist = " + bestDist);
        
        try { Thread.sleep(000); } 
        catch (InterruptedException e) { e.printStackTrace(); }
        
       
        // Use my tool for nothing
        MyTools.getSomething();
        
        return bestMove;
        //return (CCMove) moves.get(0);
        // Return a randomly selected move.
        //return (CCMove) moves.get(rand.nextInt(moves.size()));
    }
    
    private boolean inGoal(Point p) {
    	return false; 
    }
    
    
    private void printMoves(ArrayList<CCMove> moves) {
    	for (CCMove m : moves) {
    		System.out.println(m.toPrettyString()); 
    	}
    }
    
    private CCMove heur(ArrayList<CCMove> moves) { // May want to speed this up 
    	 CCMove bestMove = moves.get(0); 
         double bestDist = 50; // Big num
         for (CCMove move : moves) {
         	Point to = move.getTo();
         	if (to != null) {
         		double d = Point.distance(to.x, to.y, goal.x, goal.y);
         		System.out.println(d);
         		if (d < bestDist)  {
         			bestDist = d;
         			bestMove = move;
         		}
         	}
         }
         
         try {
 				Thread.sleep(1000);
 			} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
         return bestMove;
    }
    
    private void printPiecesAndID(ArrayList<Point> pieces) {
    	System.out.println("My pieces: ");
        for (Point p : pieces) {
        	System.out.print("("+p.x+","+p.y+")  ");
        }
        System.out.println();
        
        System.out.println("Player: " + this.playerID);
    }
}