package team.weird.compiler.editor.menu;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import team.weird.compiler.editor.configure.TabbedPanel;

public final class ThrowExceptionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextPane pane ;
	private final JScrollPane scroll;
	private SimpleAttributeSet attrSet = new SimpleAttributeSet(); 
	private static final ThrowExceptionPanel INSTANCE = new ThrowExceptionPanel();
	public static ThrowExceptionPanel getInstance(){
		return INSTANCE;
	}
	private ThrowExceptionPanel(){
		pane = new JTextPane();
		//System.out.println(StyleConstants.getBackground(attrSet));
//		StyleConstants.setBackground(attrSet, Color.black);
//		pane.setParagraphAttributes(attrSet, true);
		scroll = new JScrollPane(pane);
	
		add(scroll);
		scroll.setBorder(BorderFactory.createEtchedBorder());
		scroll.setPreferredSize(new Dimension(0, 70));
		setLayout(new GridLayout());
	}
	public void appendException(String text){ 
		System.out.println(text);
        StyleConstants.setForeground(attrSet, Color.red); 
        Document doc = pane.getDocument();   
		try {
			doc.insertString(doc.getLength(), text, attrSet);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
