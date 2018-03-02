package com.jkgroup.kingkaid.bo;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 地址省、市、区
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月15日 上午11:44:17
 */

public class AddressCodeNode {

	private String paraId;
	private String paraName;
	private List<AddressCodeNode> children;

	public AddressCodeNode() {
		this.children = new LinkedList<AddressCodeNode>();
	}

	public AddressCodeNode(String paraId, String paraName) {
		super();
		this.paraId = paraId;
		this.paraName = paraName;
		if(paraId.endsWith("0")) {
			this.children = new LinkedList<AddressCodeNode>();
		}
	}

	@JsonProperty("v")
	public String getParaId() {
		return paraId;
	}

	public void setParaId(String paraId) {
		this.paraId = paraId;
	}

	@JsonProperty("t")
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	@JsonProperty("c")
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public List<AddressCodeNode> getChildren() {
		return children;
	}

	public void setChildren(List<AddressCodeNode> children) {
		this.children = children;
	}

}
