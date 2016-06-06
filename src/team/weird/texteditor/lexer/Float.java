package team.weird.texteditor.lexer;

public class Float extends Token{
	private double flo;
	private String type;
	public Float(double flo, String type){
		super(type);
		this.flo = flo;
		this.type = type;
	}
	public double getFloat(){
		return flo;
	}
	public String getType(){
		return type;
	}
}
