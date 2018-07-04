package org.wanwanframework.sentence;

import java.util.Map;

import org.wanwanframework.file.map.LineTool;
import org.wanwanframwork.file.core.FileController;

/**
 * 分发格式： file->path |content
 * 
 * fileList->list<file> 基础配置文件为路径配置文件，指定系统的起点 参数配置文件+模板配置文件为输入配置
 */
public class Controller extends FileController<String>{

	private Map<String, String>[] configs;
	private Map<String, String> importParam;// 注入的参数、起最后修正参数的作用
	private Map<String, String> mapping;

	/**
	 * 初始化加载configs所有的配置路径指定的配置参数 resourcePath = "./src/main/resources/spirit";
	 */
	public Controller() {
		before("./src/main/resources/spirit/config.txt");
	}
	
	public Controller(String path) {
		before(path);
	}

	private void before(String path) {
		Map<String, String> url = LineTool.getLine(path, LineTool.DEFAULT_SPLITOR);
		String configUrl = url.get("url");
		String mappingPath = url.get("mapping");
		configs = LineTool.getConfigs(configUrl, ":\t", ":\t", ";");
		mapping = LineTool.getLine(mappingPath, LineTool.DEFAULT_SPLITOR);
	}

	/**
	 * 按照配置数组进行循环
	 */
	public void init() {
		if (importParam == null) {
			SpiritgirlTool.readConfigs(configs, mapping);
		} else {
			SpiritgirlTool.readConfigs(configs, mapping, importParam);
		}
	}

	public Map<String, String>[] getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, String>[] configs) {
		this.configs = configs;
	}

	public Map<String, String> getImportParam() {
		return importParam;
	}

	public void setImportParam(Map<String, String> importParam) {
		this.importParam = importParam;
	}

	public static void main(String[] args) {
		Controller.call(new Controller());
	}

}
