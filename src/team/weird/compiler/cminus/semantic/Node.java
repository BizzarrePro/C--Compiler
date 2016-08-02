package team.weird.compiler.cminus.semantic;

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
	public boolean isLeaf(){
		return false;
	}
	public String toString(){
		return symbol;
	}
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof Node || o instanceof String))	return false;
		if(o instanceof Node){
			if(((Node)o).getSymbol().equals(this.symbol))
				return true;
		} else {
			if(((String)o).equals(symbol))
				return true;
		}
		return false;
	}
	public int hashCode(){
		return symbol.hashCode();
	}
}
