package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;

public interface PrintASTree {
	abstract void print(String tab, FileWriter fw);
}
