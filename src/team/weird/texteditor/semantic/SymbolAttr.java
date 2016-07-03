package team.weird.texteditor.semantic;

public class SymbolAttr {
	public enum Attribute{
		VAR, ARRAY, FUNC
	}
	private String type = null;
	private Attribute attr= null;
	private int arrayLength = 0;
//	private String returnType = null;
	public SymbolAttr(String type, Attribute attr){
		this.type = type;
		this.attr = attr;
	}
	public SymbolAttr(String type, Attribute attr, int arrayLength){
		this.type = type;
		this.attr = attr;
		this.arrayLength = arrayLength;
	}
	public void setAttribute(Attribute attr){
		this.attr = attr;
	}
	public void setType(String type){
		this.type = type;
	}
	public void setArrayLength(int arrayLength){
		this.arrayLength = arrayLength;
	}
	public String getType(){
		return type;
	}
	public Attribute getAttribute(){
		return attr;
	}
	public int getArrayLength(){
		return arrayLength;
	}
}
