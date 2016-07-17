package team.weird.texteditor.implement;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;

public class TextHighLight implements DocumentListener{
	private Set<String> keywords;
	private Style keywordStyle;
	private Style normalStyle;
	
	public TextHighLight(JTextArea text){
		keywordStyle = ((DefaultStyledDocument)text.getDocument()).addStyle("Keyword_Style", null);
		normalStyle = ((DefaultStyledDocument)text.getDocument()).addStyle("Keyword_Style", null);
		StyleConstants.setForeground(keywordStyle, Color.RED);
		StyleConstants.setForeground(normalStyle, Color.BLACK);
		keywords = new HashSet<String>();
		keywords.add("public");
		keywords.add("protected");
		keywords.add("private");
		keywords.add("int");
		keywords.add("float");
		keywords.add("double");
	}
	public void colouring(StyledDocument doc, int pos, int len) throws BadLocationException {
		int start = indexOfWordStart(doc, pos);
		int end = indexOfWordEnd(doc, pos + len);
		char ch;
		while (start < end) {
			ch = getCharAt(doc, start);
			if (Character.isLetter(ch) || ch == '_') {
				start = colouringWord(doc, start);
			} else {
				SwingUtilities.invokeLater(new ColouringTask(doc, start, 1, normalStyle));
				++start;
			}
		}
	}
	public int colouringWord(StyledDocument doc, int pos) throws BadLocationException {
		int wordEnd = indexOfWordEnd(doc, pos);
		String word = doc.getText(pos, wordEnd - pos);
		if (keywords.contains(word)) {
			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, keywordStyle));
		} else {
			SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, normalStyle));
		}
		return wordEnd;
	}
	private int indexOfWordStart(Document doc, int pos) throws BadLocationException {
		for (; pos > 0 && isWordCharacter(doc, pos - 1); --pos);
		return pos;
	}
	private int indexOfWordEnd(Document doc, int pos) throws BadLocationException {
		for (; isWordCharacter(doc, pos); ++pos);
		return pos;
	}
	private boolean isWordCharacter(Document doc, int pos) throws BadLocationException {
		char ch = getCharAt(doc, pos); 
		if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_'){ 
			return true; 
		}
		return false;
	}
	private char getCharAt(Document doc, int pos) throws BadLocationException {
		return doc.getText(pos, 1).charAt(0);
	}
	private class ColouringTask implements Runnable {
		private StyledDocument doc;
		private Style style;
		private int pos;
		private int len;
		public ColouringTask(StyledDocument doc, int pos, int len, Style style) {
			this.doc = doc;
			this.pos = pos;
			this.len = len;
			this.style = style;
		}
		public void run() {
			try {
				doc.setCharacterAttributes(pos, len, style, true);
			} catch (Exception e) {}
		}
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try {
            colouring((DefaultStyledDocument) e.getDocument(), e.getOffset(), 0);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try{
			colouring((DefaultStyledDocument) e.getDocument(), e.getOffset(), e.getLength());
		}
		catch(BadLocationException e1){
			e1.printStackTrace();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}
}
