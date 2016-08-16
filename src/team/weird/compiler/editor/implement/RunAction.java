package team.weird.compiler.editor.implement;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import team.weird.compiler.editor.function.RunMenuItemFunc;
import team.weird.compiler.editor.util.RunActionUtil;


public class RunAction extends AbstractAction implements RunMenuItemFunc{
	private JTabbedPane tab;
	private JFrame frame;
	public RunAction(String name, JTabbedPane tab){
		super(name);
		tab = this.tab;
	}
	public RunAction(String name, JTabbedPane tab, JFrame frame) {
		// TODO Auto-generated constructor stub
		super(name);
		this.tab = tab;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		switch(command){
		case "run":	
			try {
				runCode();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
			break;
		case "syntax":
			showOriginalSyntax();
			break;
		case "lexer":
			showLexerAnalysis();
			break;
		case "parser":
			showParserAnalysis();
			break;
		case "semantic":
			showIntermediateCode();
			break;
		case "target":
			showAsmCode();
		}
	}

	@Override
	public void runCode(){
		// TODO Auto-generated method stub
		FileWriter fw = null;
		File fi = new File("./compile/temp.c");
		File dir = new File("./compile");
		JTextArea temp = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		try{
			if(!dir.exists()){
				dir.mkdir();
				String attr = "attrib +H " + dir.getAbsolutePath();
				Runtime.getRuntime().exec(attr);
				fw = new FileWriter(fi);
				String stream = temp.getText();
				if(stream != null)
					fw.write(stream);
			} 
			else {
				fw = new FileWriter(fi);
				String stream = temp.getText();
				if(stream != null)
					fw.write(stream);
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			RunActionUtil.run();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void showLexerAnalysis() {
		// TODO Auto-generated method stub
		FileAction fileAction = new FileAction("", tab);
		File file = new File("./compile/temp.tok");
		JTextArea text = fileAction.newFileAction("temp.tok");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String content = br.readLine();
			while(content != null){
				sb.append(content);
				sb.append("\r\n");
				content = br.readLine();
			}
			text.setText(sb.toString());
		} catch (FileNotFoundException fn){
			fn.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void showParserAnalysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showIntermediateCode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAsmCode() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showOriginalSyntax() {
		// TODO Auto-generated method stub
		
	}

}
