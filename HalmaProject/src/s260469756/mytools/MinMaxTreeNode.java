package s260469756.mytools;

import java.util.ArrayList;

import halma.CCBoard;

public class MinMaxTreeNode {
	
	private MinMaxTreeNode parent;
	private ArrayList<MinMaxTreeNode> children;
	private CCBoard board;
	private int evaluation;
	
	public MinMaxTreeNode(MinMaxTreeNode p, CCBoard b, int eval) {
		board = b;
		evaluation = eval;
		parent = p;
		children = new ArrayList<MinMaxTreeNode>();
	}
	
	public CCBoard getBoard() {
		return board;
	}
	
	public int getEvaluation() {
		return evaluation;
	}
	
	public MinMaxTreeNode getParent() {
		return parent;
	}
	
	public ArrayList<MinMaxTreeNode> children() {
		return children;
	}
	
	public void addChild(CCBoard b, int eval) {
		children.add(new MinMaxTreeNode(this,b,eval));
	}
	
}
