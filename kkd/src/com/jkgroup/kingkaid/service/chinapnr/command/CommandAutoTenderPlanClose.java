package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动投标计划关闭
 * @author pan
 *
 */
public class CommandAutoTenderPlanClose implements HFCommand{

	private static final Logger log = LoggerFactory.getLogger(CommandAutoTenderPlanClose.class);

	@Override
	public FormData exec(Map<String, String> resultMap) {
		log.info("自动投标关闭" + resultMap.get("CmdId"));
		return null;
	}

}
