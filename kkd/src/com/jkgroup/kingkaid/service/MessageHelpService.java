package com.jkgroup.kingkaid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年8月5日 下午5:18:02
 */

@Service
public class MessageHelpService {
	
	private final String UNDEFINED = "未定义";
	private final String LABEL_SUFFIX = "label";
	
	@Autowired
	private OptionService optionService;
	
	/**
	 * 包装的MessageWrapper对象包含数据以及option类型数据对应的label数组
	 * 适用于filed name与option name相同的情况
	 * @param outData
	 * @param fieldNames
	 * @return
	 */
	public MessageWrapper buildMessageWrapperWithOption(Page<FormData> outData, String...fieldNames) {
		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setBody(outData);
		buildWrapper(wrapper, outData.getResult(), fieldNames);
		return wrapper;
	}
	
	/**
	 * 包装的MessageWrapper对象包含数据以及option类型数据对应的label数组
	 * 适用于filed name与option name不同的情况
	 * @param outData
	 * @param fieldNames
	 * @param optionNames
	 * @return
	 */
	public MessageWrapper buildMessageWrapperWithOption(Page<FormData> outData, String[] fieldNames, String[] optionNames) {
		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setBody(outData);
		buildWrapper(wrapper, outData.getResult(), fieldNames, optionNames);
		return wrapper;
	}
	
	
	/**
	 * 包装的MessageWrapper对象包含数据以及option类型数据对应的label数组
	 * 适用于filed name与option name相同的情况
	 * @param dataList
	 * @param fieldNames
	 * @return
	 */
	public MessageWrapper buildMessageWrapperWithOption(List<FormData> dataList, String...fieldNames) {
		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setBody(dataList);
		buildWrapper(wrapper, dataList, fieldNames);
		return wrapper;
	}
	
	/**
	 * 包装的MessageWrapper对象包含数据以及option类型数据对应的label数组
	 * 适用于filed name与option name不同的情况
	 * @param dataList
	 * @param fieldNames
	 * @param optionNames
	 * @return
	 */
	public MessageWrapper buildMessageWrapperWithOption(List<FormData> dataList, String[] fieldNames, String[] optionNames) {
		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setBody(dataList);
		buildWrapper(wrapper, dataList, fieldNames, optionNames);
		return wrapper;
	}
	
	/**
	 * 包装的MessageWrapper对象包含数据以及option类型数据对应的label数组
	 * 适用于filed name与option name相同的情况
	 * @param fd
	 * @param fieldNames
	 * @return
	 */
	public MessageWrapper buildMessageWrapperWithOption(FormData fd, String...fieldNames) {
		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setBody(fd);
		List<FormData> dataList = new ArrayList<FormData>(1);
		dataList.add(fd);
		buildWrapper(wrapper, dataList, fieldNames);
		return wrapper;
	}
	
	/**
	 * 包装的MessageWrapper对象包含数据以及option类型数据对应的label数组
	 * 适用于filed name与option name不同的情况
	 * @param fd
	 * @param fieldNames
	 * @param optionNames
	 * @return
	 */
	public MessageWrapper buildMessageWrapperWithOption(FormData fd, String[] fieldNames, String[] optionNames) {
		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setBody(fd);
		List<FormData> dataList = new ArrayList<FormData>(1);
		dataList.add(fd);
		buildWrapper(wrapper, dataList, fieldNames, optionNames);
		return wrapper;
	}
	
	private void buildWrapper(MessageWrapper wrapper, List<FormData> dataList, String[] fieldNames, String[] optionNames) {
		if(dataList == null || dataList.isEmpty() || fieldNames.length == 0 || fieldNames.length != optionNames.length) {
			return;
		}
		for(int i = 0; i < fieldNames.length; i ++) {
			Map<String, String> stateMap = optionService.getOptionsMap(optionNames[i]);
			String[] labels = new String[dataList.size()];
			for(int j = 0; j < labels.length; j ++) {
				String key = (String) FormDataUtil.getProperty(dataList.get(j), fieldNames[i]);
				labels[j] = stateMap.get(key);
				labels[j] = StringUtils.isBlank(labels[j]) ? UNDEFINED : labels[j];
			}
			wrapper.addAnnex(fieldNames[i] + LABEL_SUFFIX, labels);
		}
	}
	
	private void buildWrapper(MessageWrapper wrapper, List<FormData> dataList, String...fieldNames) {
		if(dataList == null || dataList.isEmpty() || fieldNames.length == 0) {
			return;
		}
		for(String optionName : fieldNames) {
			Map<String, String> stateMap = optionService.getOptionsMap(optionName);
			String[] labels = new String[dataList.size()];
			for(int i = 0; i < labels.length; i ++) {
				String key = (String) FormDataUtil.getProperty(dataList.get(i), optionName);
				labels[i] = stateMap.get(key);
				labels[i] = StringUtils.isBlank(labels[i]) ? UNDEFINED : labels[i];
			}
			wrapper.addAnnex(optionName + LABEL_SUFFIX, labels);
		}
	}
}
