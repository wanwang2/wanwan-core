package org.wanwanframework.sentence.grid;

import org.wanwanframwork.file.FileUtil;

public class GridTool {

	public static String dir = "E:/files/仙剑昆仑/template";
	
	public static void makeFile(String name, String content) {
		FileUtil.createFile(dir + "/" + name + ".txt", content);
	}
	
	public static void main(String[] args) {
		makeFile("vv", "bb");
		System.out.println("end...");
	}
}
