package org.wanwanframework.sentence.grid;

import org.wanwanframwork.file.core.FileController;

public class GridLoopController extends FileController<GridController[]>{

	GridLoopController() {
		core = new GridController[200];
	
	}
	
	@Override
	protected void process() {
		int i = 1;
		for (GridController gridController : core) {
			gridController = new GridController("" + i);
			gridController.process();
			i++;
		}
	}
	
	public static void main(String[] args) {
		GridLoopController.start(new GridLoopController());
	}
}
