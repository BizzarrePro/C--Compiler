package team.weird.compiler.cminus.semantic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ErrorList {
	private static final ErrorList INSTANCE = new ErrorList();
	private List<Throwable> errList = new ArrayList<Throwable>();
	private ErrorList(){}
	public static ErrorList getInstance(){
		return INSTANCE;
	}
	public void addException(Throwable err){
		errList.add(err);
	}
	public void throwsAllExceptions() throws Throwable{
		if(errList.isEmpty())
			return;
		Iterator<Throwable> iter = errList.iterator();
		SemanticException err = null;
		while(iter.hasNext()){
			err = (SemanticException)iter.next();
			try{
				throw err;
			} catch (SemanticException e) {
				e.printStackTrace();
			}
		}
	}
}
