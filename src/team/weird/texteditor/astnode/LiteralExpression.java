package team.weird.texteditor.astnode;

public class LiteralExpression extends Expression{
	private Object number = null;
	public LiteralExpression(Object number, int line){
		this.number = number;
		super.setLine(line);
	}
	public Object getNumber() {
		return number;
	}
	public void setNumber(Object number) {
		this.number = number;
	}
	
}
