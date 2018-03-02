package com.jkgroup.kingkaid.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXB;

import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;
import com.jkgroup.kingkaid.bo.All;
import com.jkgroup.kingkaid.bo.Option;
import com.jkgroup.kingkaid.bo.Options;

/**
 * 缓存option.xml的服务类
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月15日 下午8:49:06
 */

@Service
public class OptionService {
	
//	private final static Logger log = LoggerFactory.getLogger(OptionService.class);

	// key: options name
	// value: options instance
	private final Map<String, Options> optionsMap = new HashMap<String, Options>();
	
	// key: options name
	// value: sub option map
	//                   key: sub option value
	//                   value: sub option text
	private final Map<String, Map<String, String>> optionMap = new HashMap<String, Map<String, String>>();
	
	@PostConstruct
	public void init() {
		InputStream xmlFileStream = getClass().getResourceAsStream("/options.xml");
		All all = JAXB.unmarshal(xmlFileStream, All.class);
		for(Options opts : all.getOptions()) {
			optionsMap.put(opts.getName(), opts);
			optionMap.put(opts.getName(), optionsToMap(opts));
		}
	}
	
	private Map<String, String> optionsToMap(Options opts) {
		Map<String, String> map = new HashMap<String, String>();
		for(Option opt : opts.getOptions()) {
			map.put(opt.getValue(), opt.getText());
		}
		return map;
	}
	
	/**
	 * 根据name获取Options
	 * 适用于drop-down list
	 * @param name
	 * @return
	 */
	public Options getOptions(String name) {
		return optionsMap.get(name);
	}
	
	/**
	 * 根据name寻找Options并将Options中的Option列表转化成Map返回
	 * 适用于列表展示
	 * @param name
	 * @return
	 */
	public Map<String, String> getOptionsMap(String name) {
		return optionMap.get(name);
	}
	
	/**
	 * 获取页面上年份选择列表
	 * @return
	 */
	public List<Integer> getYears() {
		List<Integer> years = new ArrayList<Integer>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		for(int i = 0; i < 70; i ++) {
			years.add(currYear - i);
		}
		return years;
	}
	
}
