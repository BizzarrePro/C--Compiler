package team.weird.texteditor.menu;

public class FileAttribute {
	private String path;
	private String name;

	public FileAttribute(String path, String name) {
		this.path = path;
		this.name = name;
	}
	public String getPath(){
		return path;
	}
	public String getName(){
		return name;
	}
}
