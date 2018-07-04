package org.wanwanframework.sentence.grid;

import java.util.Map;

import org.wanwanframework.file.map.LineTool;
import org.wanwanframwork.file.core.FileController;

public class GridController extends FileController<String>{

	private String name = "一";
	private Map<String, String> listConfig;
	
	public GridController() {
		listConfig = LineTool.getLine(
				"./src/main/resources/sentence/grid/chapter.t", LineTool.DEFAULT_SPLITOR);
	}

	public GridController(String name) {
		this.name = name;
		listConfig = LineTool.getLine(
				"./src/main/resources/sentence/grid/chapter.t", LineTool.DEFAULT_SPLITOR);
	}
	
	public void process() {
		System.out.println("listConfig:" + listConfig);
		GridTool.makeFile(name, listConfig.get("template"));
	}
	
	public static void main(String[] args) {
		GridController.start(new GridController("" + 1));
	}
}
