package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import team.weird.texteditor.parser.SyntacticErrorException;

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
		SyntacticErrorException err = null;
		while(iter.hasNext()){
			err = (SyntacticErrorException)iter.next();
			try{
				throw err;
			} catch (SyntacticErrorException e) {
				e.printStackTrace();
				throw err;
			}
		}
	}
}
