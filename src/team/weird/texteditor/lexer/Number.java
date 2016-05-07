package team.weird.texteditor.lexer;

public class Number extends Token{
	private int num;
	private String type;
	public Number(int num, String type){
		super(""+num, type);
		this.num = num;
		this.type = type;
	}
	public int getNum(){
		return num;
	}
	public String getType(){
		return type;
	}
	public String toString(){
		return "Num: "+num+" TYPE: "+ type;
	}
}
