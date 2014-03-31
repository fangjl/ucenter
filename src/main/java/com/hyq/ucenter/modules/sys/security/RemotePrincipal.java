package com.hyq.ucenter.modules.sys.security;

import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;

import com.google.common.collect.Maps;


/**
 * 单点登入反馈信息
 * @author fjl
 * 2014年3月20日
 */
@SuppressWarnings("serial")
public class RemotePrincipal implements java.io.Serializable{
	
	private String username;
	private String name;
	private String office;
	private String dataScope;
	private String tenantcode;
	private String tenantname;
	private String rolename;
	private String permissions;
	private Map offices;
	
	
	private Map<String, Object> cacheMap;

	public RemotePrincipal(){
		
	}
	
	public RemotePrincipal(AttributePrincipal attributePrincipal){
		Map<String,Object> m = attributePrincipal.getAttributes();
		

		this.username   =  attributePrincipal.getName();
		this.name 		=  (String)    m.get("name");
		this.tenantcode =  (String)    m.get("tenant_code");
		this.tenantname =  (String)    m.get("tenant_name");
		this.office     =  (String)    m.get("office");
		this.dataScope  =  (String)    m.get("data_scope");
		this.rolename   =  (String)    m.get("rolename");

		String perm=  (String) m.get("permission");
		this.permissions = perm.replaceAll("\\[", "").replaceAll("\\]", "");
						
		String acloffices	=  (String) m.get("office_codes_names");
		String[] aclofficeses = acloffices.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		this.offices = Maps.newHashMap();
		for(String o:aclofficeses){
			String[] r = o.split("\\|");
			offices.put(r[0], r[1]);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getTenantcode() {
		return tenantcode;
	}

	public void setTenantcode(String tenantcode) {
		this.tenantcode = tenantcode;
	}

	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Map getOffices() {
		return offices;
	}

	public void setOffices(Map offices) {
		this.offices = offices;
	}
	
	public Map<String, Object> getCacheMap() {
		if (cacheMap==null){
			cacheMap = new HashMap<String, Object>();
		}
		return cacheMap;
	}
	
	
}
