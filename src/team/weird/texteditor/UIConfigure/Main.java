package team.weird.texteditor.UIConfigure;

import java.io.IOException;

/**@project TextEditor
 * @filename Main.java
 * @author Siyuan_Liu
 * @copyright All rights reserved by Siyuan_Liu and Qian_Yang .
 * @description A basic text editor with lexer and parsing.
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		LookAndFeel look = new LookAndFeel();
		look.initFrame();
	}

}
