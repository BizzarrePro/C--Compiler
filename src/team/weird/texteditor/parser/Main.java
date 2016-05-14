package team.weird.texteditor.parser;

public class Main {
	public static void main(String[] args){
		FirstSet firstSet = new FirstSet();
		firstSet.createFirstSet();
		firstSet.display();
		FollowSet followSet = new FollowSet(firstSet.symbolMap);
		followSet.createFollowSet();
		followSet.display();
	}
}
