package team.weird.texteditor.semantic;

public abstract class Node {
	protected String symbol = null;
	public Node(String symbol){
		this.setSymbol(symbol);
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String toString(){
		return symbol;
	}
}
