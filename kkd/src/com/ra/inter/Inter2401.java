package com.ra.inter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.RAClient;
import cfca.ra.toolkit.exception.RATKException;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PropertiesUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 证书申请并下载
 */
public class Inter2401 {
	private static final Logger log = LoggerFactory.getLogger(Inter2401.class);

	public static CertServiceResponseVO toinprocess(Model model,FormData outData, String p10) throws RATKException {
		// kkd传值
		String serialNo = FormDataUtil.getProperty(outData, "serialno").toString();
		String AuthCode = FormDataUtil.getProperty(outData, "authcode").toString();
		log.debug("serialNo=", serialNo);
		log.debug("AuthCode=", AuthCode);
		log.debug("p10=", p10);
		int connectTimeout = 3000;
		int readTimeout = 30000;
		String url = PropertiesUtil.getRAUri();
		log.debug("raurl=", url);
		RAClient client = new RAClient(url, connectTimeout, readTimeout);
		
		CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
		certServiceRequestVO.setTxCode("2401");
		certServiceRequestVO.setSerialNo(serialNo); 
		certServiceRequestVO.setAuthCode(AuthCode);
        certServiceRequestVO.setP10(p10);

		CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);
		if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {
		}
		CertServiceResponseVO cr = certServiceResponseVO;
		return cr;
	}
}
