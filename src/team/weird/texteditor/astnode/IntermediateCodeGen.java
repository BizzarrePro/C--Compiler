package team.weird.texteditor.astnode;

import team.weird.texteditor.codegen.Instruction;

public interface IntermediateCodeGen {
	abstract Instruction generateIntermediateCode();
}
