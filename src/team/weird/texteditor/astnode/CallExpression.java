package team.weird.texteditor.astnode;

import java.util.ArrayList;

public class CallExpression extends Expression{
	private String id;
	private ArrayList<Expression> argsList = new ArrayList<Expression>();
	public CallExpression(String id, ArrayList<Expression> argsList){
		this.id = id;
		this.argsList = argsList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Expression> getArgsList() {
		return argsList;
	}
	public void setArgsList(ArrayList<Expression> argsList) {
		this.argsList = argsList;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "CallExpression: " + id + " ( ");
		for(Expression exp : argsList) {
			exp.print(tab + "\t");
			System.out.println();
		}
		System.out.println(tab + " )");
	}
	
}
