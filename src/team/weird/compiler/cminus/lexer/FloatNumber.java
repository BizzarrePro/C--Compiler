package team.weird.compiler.cminus.lexer;

public class FloatNumber extends Token{
	private double flo;
	private String type;
	public FloatNumber(double flo, String type){
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
