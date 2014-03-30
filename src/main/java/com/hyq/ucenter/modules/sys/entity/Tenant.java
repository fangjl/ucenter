package com.hyq.ucenter.modules.sys.entity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.hyq.ucenter.common.persistence.IdEntity;
@Entity
@Table(name = "sys_tenant")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tenant  extends IdEntity<Tenant> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tenantCode;	// 父级编号
	private String tenantName; // 所有父级编号
	private Area   area;		// 归属区域
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String email; 	// 邮箱
	private Integer status;   // 0  ： 注册   1：已经审核   2：冻结
	
	public String getTenantCode() {
		return tenantCode;
	}
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
	@ManyToOne
	@JoinColumn(name="area_id")
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}

	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
	
	
	
}
