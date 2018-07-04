package org.wanwanframework.sentence;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.wanwanframework.file.map.FilterUtil;
import org.wanwanframework.file.map.LineTool;
import org.wanwanframework.file.map.MappingUtil;
import org.wanwanframwork.file.FileReader;
import org.wanwanframwork.file.FileUtil;
import org.wanwanframwork.file.Log;

/**
 * 精灵模块的复用部分
 * 
 * @author coco
 *
 */
public class SpiritgirlTool {

	/**
	 * 最后处理模板文件:得到内容后直接放到路径下面去修改
	 */
	public static void writeTemplate(Map<String, String> map, Map<String, String> param,
			Map<String, String> templateMap) {
		String content;
		for (String key : templateMap.keySet()) {
			content = templateMap.get(key);
			content = FilterUtil.filter(content, param);
			modifyFile(key, map, content);
		}
	}

	/**
	 * 修改文件：通过匹配文件名关键字，把内容写到空文件中
	 * 
	 * @param templateKey
	 * @param map
	 * @param content
	 */
	public static void modifyFile(String templateKey, Map<String, String> map, String content) {
		for (String fileKey : map.keySet()) {
			if (fileKey.indexOf(templateKey) >= 0) {
				String path = map.get(fileKey);
				FileUtil.createFile(path, content);
			}
		}
	}

	/**
	 * 构建文件框架：创建空白文档集
	 * 
	 * @param map
	 */
	public static void writeStruct(Map<String, String> map, Map<String, String> param, Map<String, String> mapping) {
		String value;
		for (String key : map.keySet()) {
			value = map.get(key);
			value = FilterUtil.filter(value, param);
			map.put(key, value); // 修改键值对
			mappingCall(key, value, mapping);
			Log.log("key:" + key + ", value:" + value);
		}
	}

	/**
	 * 处理单个文件或者目录
	 * 
	 * @param key
	 * @param value
	 */
	public static void mappingCall(String key, String value, Map<String, String> mapping) {
		if (key.contains("@")) {
			for (String mapKey : mapping.keySet()) {
				if (key.indexOf(mapKey) >= 0) {
					String method = mapping.get(mapKey);
					Mapping.call(method, value);
				}
			}
		}
	}
	
	/**
	 * 读取一个配置——start文件
	 * 
	 * @param config
	 */
	public static void readConfig(Map<String, String> config, Map<String, String> mapping, Map<String, String> importParam) {
		Map<String, String>[] paramMaps = MappingUtil.getMapping(config.get("param"), LineTool.DEFAULT_SPLITOR);
		Map<String, String> templateMap = FileReader.loads(config.get("template"), "start");
		if (paramMaps.length > 0) {
			Map<String, String> param = paramMaps[0];
			modifyParam(param, importParam);
			for (int i = 0; i < paramMaps.length - 1; i++) {
				SpiritgirlTool.writeStruct(paramMaps[i + 1], param, mapping);
				SpiritgirlTool.writeTemplate(paramMaps[i + 1], param, templateMap);
			}
		}
	}
	
	/**
	 * 替换参数里面的值
	 * @param param
	 * @param importParam
	 */
	private static void modifyParam(Map<String, String> param, Map<String, String> importParam) {
		for(String key:param.keySet()) {
			if(importParam != null && importParam.containsKey(key)) {
				param.put(key, importParam.get(key));
			}
		}
	}
	
	/**
	 * 处理所有的配置文件
	 */
	public static void readConfigs(Map<String, String>[] configs, Map<String, String> mapping, Map<String, String> importParam) {
		for (int i = 0; i < configs.length; i++) {
			SpiritgirlTool.readConfig(configs[i], mapping, importParam);
		}
		Log.log("init end...");
	}
	
	/**
	 * 处理所有的配置文件
	 */
	public static void readConfigs(Map<String, String>[] configs, Map<String, String> mapping) {
		for (int i = 0; i < configs.length; i++) {
			SpiritgirlTool.readConfig(configs[i], mapping, null);
		}
		Log.log("init end...");
	}
	
	
	/**
	 * 处理所有的配置文件
	 * @param configs
	 * @param mapping
	 */
	public static void readConfigs(List<Map<String, String>> configs, Map<String, String> mapping, Map<String, String> importParam) {
		for (int i = 0; i < configs.size(); i++) {
			SpiritgirlTool.readConfig(configs.get(i), mapping, importParam);
		}
		Log.log("init end...");
	}
	
	/**
	 * 处理所有的配置文件
	 * @param configs
	 * @param mapping
	 */
	public static void readConfigs(List<Map<String, String>> configs, Map<String, String> mapping) {
		for (int i = 0; i < configs.size(); i++) {
			SpiritgirlTool.readConfig(configs.get(i), mapping, null);
		}
		Log.log("init end...");
	}
}
