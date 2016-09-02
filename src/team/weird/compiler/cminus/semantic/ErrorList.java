package team.weird.compiler.cminus.semantic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import team.weird.compiler.editor.menu.ExitStatePanel;
import team.weird.compiler.editor.menu.ThrowExceptionPanel;


public class ErrorList {
	private static final ErrorList INSTANCE = new ErrorList();
	private List<Throwable> errList = new ArrayList<Throwable>();
	public ErrorList(){}
	public static ErrorList getInstance(){
		return INSTANCE;
	}
	public void addException(Throwable err){
		errList.add(err);
	}
	public void throwsAllExceptions() throws Throwable{
		if(errList.isEmpty())
			return;
		ThrowExceptionPanel panel = ThrowExceptionPanel.getInstance();
		Iterator<Throwable> iter = errList.iterator();
		SemanticException err = null;
		while(iter.hasNext()){
			err = (SemanticException)iter.next();
			try{
				throw err;
			} catch (SemanticException e) {
				StackTraceElement[] element = e.getStackTrace();
				panel.appendException(e.getMessage()+"\r\n");
				for(StackTraceElement el : element){
					panel.appendException("\t"+el.toString()+"\r\n");
				}
				e.printStackTrace();
			}
		}
		panel.setVisible(true);
		ExitStatePanel.getInstance().setVisible(true);
		throw new Exception();
	}
	public void clear(){
		errList.clear();
	}
}
