package com.hyq.ucenter.modules.sys.dao;
import org.springframework.stereotype.Repository;
import com.hyq.ucenter.common.persistence.BaseDao;
import com.hyq.ucenter.common.persistence.Parameter;
import com.hyq.ucenter.modules.sys.entity.Tenant;
@Repository
public class TenantDao extends BaseDao<Tenant> {
	public Tenant findTenantByCode(String tenantCode){
		return getByHql("from Tenant where tenantcode= :p1", new Parameter(tenantCode));
	}
	
}
