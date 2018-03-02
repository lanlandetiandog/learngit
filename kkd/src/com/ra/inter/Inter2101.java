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
public class Inter2101 {
	private static final Logger log=LoggerFactory.getLogger(Inter2101.class);
	public static CertServiceResponseVO toinprocess(Model model,
			FormData outData) throws RATKException {
		// 平台参数
		String certType = "1";
		String branchCode = "678";
		// kkd传值
		String custtype = FormDataUtil .getProperty(outData, "custtype").toString();
		String userName = FormDataUtil.getProperty(outData, "custname").toString();
		String identType,identNo, CustomerType;
		if (custtype.equals("0")) { // 企业取营业执照 0-自然人
			identType = "0";
			CustomerType = "1";
			identNo = FormDataUtil.getProperty(outData, "paperid").toString();
		} else {
			identType = "8";
			CustomerType = "2";
			identNo = FormDataUtil.getProperty(outData, "orgaid").toString();
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
