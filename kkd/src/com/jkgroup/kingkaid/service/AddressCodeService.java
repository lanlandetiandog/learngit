package com.jkgroup.kingkaid.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jkgroup.kingkaid.bo.AddressCodeNode;
import com.jkgroup.kingkaid.bo.FrontAddr;

/**
 * 省市区数据处理类
 * 读取并缓存省市区数据，拆分文本地址为省、市、区代码加街道字符串
 * 原因是mloan地址存的是文本，只能在此进行拆分。
 * 
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月15日 上午10:05:30
 */

@Service
public class AddressCodeService {

	private final static Logger log = LoggerFactory.getLogger(AddressCodeService.class);
	
	private AddressCodeNode[] provinces = null;
	private AddressCodeNode[] hfProvinces = null;
	
	@PostConstruct
	public void init() {
	    loadAreaCode("/areacode.json", 1);
		log.info("省市区缓存加载完毕");
		loadAreaCode("/hfareacode.json", 2);
		log.info("汇付天下省市缓存加载完毕");
	}
	
	private void loadAreaCode(String filePath, int type) {
	       InputStream in = getClass().getResourceAsStream(filePath);
	        ObjectMapper om = new ObjectMapper();
	        try {
	            if (type == 1) {
	                provinces = om.readValue(in, AddressCodeNode[].class);
	            } else if (type == 2) {
	                hfProvinces = om.readValue(in, AddressCodeNode[].class);
	            }
	        } catch (Exception e) {
	            log.error("加载文件" + filePath + "失败", e);
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	}

	/**
	 * 获取省参数列表
	 * @return
	 */
	private AddressCodeNode[] getProvinces() {
		return provinces;
	}
	
	/**
	 * 获取市列表
	 * @return
	 */
	private List<AddressCodeNode> getCities(String provinceParaId) {
		for(AddressCodeNode node : provinces) {
			if(node.getParaId().equals(provinceParaId)) {
				return node.getChildren();
			}
		}
		return new ArrayList<AddressCodeNode>();
	}
	
	/**
	 * 获取区县列表
	 * @return
	 */
	private List<AddressCodeNode> getDistricts(String cityParaId) {
		String provinceParaId = cityParaId.substring(0, 2) + "0000"; 
		for(AddressCodeNode node : provinces) {
			if(node.getParaId().equals(provinceParaId)) {
				for(AddressCodeNode cn : node.getChildren()) {
					if(cn.getParaId().equals(cityParaId)) {
						return cn.getChildren();
					}
				}
			}
		}
		return new ArrayList<AddressCodeNode>();
	}

	/**
	 * 后台系统地址存的是文本，前台需要将之拆分
	 * @param backendAddr
	 * @return
	 */
	public FrontAddr buildFrontAddr(String backendAddr) {
		if(StringUtils.isEmpty(backendAddr)) {
			return new FrontAddr();
		}
		AddressCodeNode[] pList = getProvinces();
		String paraId = null;
		String addr = backendAddr;
		for(AddressCodeNode cn : pList) {
			if(addr.startsWith(cn.getParaName())) {
				addr = addr.substring(cn.getParaName().length());
				paraId = cn.getParaId();
				break;
			}
		}
		if(paraId == null) {
			return new FrontAddr(paraId, addr);
		}
		List<AddressCodeNode> cList = getCities(paraId);
		for(AddressCodeNode cn : cList) {
			if(addr.startsWith(cn.getParaName())) {
				addr = addr.substring(cn.getParaName().length());
				paraId = cn.getParaId();
				break;
			}
		}
		if(paraId == null) {
			return new FrontAddr(paraId, backendAddr);
		}
		List<AddressCodeNode> dList = getDistricts(paraId);
		for(AddressCodeNode cn : dList) {
			if(addr.startsWith(cn.getParaName())) {
				addr = addr.substring(cn.getParaName().length());
				paraId = cn.getParaId();
				break;
			}
		}
		if(paraId == null) {
			return new FrontAddr(paraId, backendAddr);
		}
		return new FrontAddr(paraId, addr);
	}
	
	/**
	 * 根据汇付天下的省市代码获取省市名称
	 * @param provinceCode 省代码
	 * @param cityCode 市代码
	 * @return
	 */
	public String[] getHFAddressTexts(String provinceCode, String cityCode) {
	    String[] addTexts = new String[] {"", ""};
	    for (AddressCodeNode pNode : hfProvinces) {
	        if (pNode.getParaId().equalsIgnoreCase(provinceCode)) {
	            addTexts[0] = pNode.getParaName();
	            for (AddressCodeNode cNode: pNode.getChildren()) {
	                if (cNode.getParaId().equalsIgnoreCase(cityCode)) {
	                    addTexts[1] = cNode.getParaName();
	                }
	            }
	        }
	    } 
	    return addTexts;
	}
	
	/**
	 * 根据省市代码获取省市名称
	 * @param provinceCode 省代码
	 * @param cityCode 市代码
	 * @return
	 */
	public String[] getAddressTexts(String provinceCode, String cityCode) {
	    String[] addTexts = new String[] {"", ""};
	    for (AddressCodeNode pNode : provinces) {
	        if (pNode.getParaId().equalsIgnoreCase(provinceCode)) {
	            addTexts[0] = pNode.getParaName();
	            for (AddressCodeNode cNode: pNode.getChildren()) {
	                if (cNode.getParaId().equalsIgnoreCase(cityCode)) {
	                    addTexts[1] = cNode.getParaName();
	                }
	            }
	        }
	    } 
	    return addTexts;
	}
	
}
