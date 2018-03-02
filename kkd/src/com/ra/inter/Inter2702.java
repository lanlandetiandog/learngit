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

// 证书申请
public class Inter2702 {
	private static final Logger log=LoggerFactory.getLogger(Inter2702.class);
	public static CertServiceResponseVO toinprocess(Model model,
			FormData outData) throws RATKException {	
		// kkd传值
		String DN = FormDataUtil .getProperty(outData, "DN").toString();		
		log.debug("DN=="+DN);
		int connectTimeout = 3000;
		int readTimeout = 30000;
		String url = PropertiesUtil.getRAUri();
		RAClient client = new RAClient(url, connectTimeout, readTimeout);

		CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
		certServiceRequestVO.setTxCode("2702");
		certServiceRequestVO.setDn(DN);
		 
		CertServiceResponseVO certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);
		if (RAClient.SUCCESS.equals(certServiceResponseVO.getResultCode())) {
 		}
		CertServiceResponseVO cr = certServiceResponseVO;
		return cr;
	}
}
