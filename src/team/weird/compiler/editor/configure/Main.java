package team.weird.compiler.editor.configure;

import java.io.IOException;

/**@project TextEditor
 * @filename Main.java
 * @author Siyuan_Liu
 * @copyright All rights reserved by Siyuan_Liu and Qian_Yang .
 * @description A C-MINUS compiler with simple but beautiful user interface.
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		LookAndFeel look = new LookAndFeel();
		look.initFrame();
	}

}
