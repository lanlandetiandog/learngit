package com.ra.inter;
 
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.RAClient;
import cfca.ra.toolkit.exception.RATKException;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PropertiesUtil;

// 证书申请
public class Inter2101Other {
	private static final Logger log=LoggerFactory.getLogger(Inter2101Other.class);
	public static CertServiceResponseVO toinprocess(Map<String, String> map,
			FormData outData) throws RATKException {
		// 平台参数
		String certType = "1";
		String branchCode = "678";
		// kkd传值
		String custtype = map.get("custtype").toString();		
		String userName = map.get("custname").toString();		
		String identType,identNo, CustomerType;
		if (custtype.equals("0")) { // 企业取营业执照 0-自然人
			identType = "0";
			CustomerType = "1";
			identNo = map.get("paperid").toString();
		} else {
			identType = "8";
			CustomerType = "2";
			identNo = map.get("paperid").toString();
		} 
		log.debug("custtype=="+custtype+ "===userName==="+userName, "===identType==="+identType +"===identNo==="+identNo);
		int connectTimeout = 3000;
		int readTimeout = 30000;
		String url = PropertiesUtil.getRAUri();
		RAClient client = new RAClient(url, connectTimeout, readTimeout);

		CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
		certServiceRequestVO.setTxCode("2101");
		certServiceRequestVO.setCertType(certType);
		certServiceRequestVO.setBranchCode(branchCode);
		certServiceRequestVO.setCustomerType(CustomerType);
		certServiceRequestVO.setUserName(userName);
		certServiceRequestVO.setIdentType(identType);
		certServiceRequestVO.setIdentNo(identNo);

		CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);
		if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {
 		}
		CertServiceResponseVO cr = certServiceResponseVO;
		return cr;
	}
}
