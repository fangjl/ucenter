/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hyq.ucenter.modules.sys.dao;

import org.springframework.stereotype.Repository;

import com.hyq.ucenter.common.persistence.BaseDao;
import com.hyq.ucenter.common.persistence.Parameter;
import com.hyq.ucenter.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class RoleDao extends BaseDao<Role> {

	public Role findByName(String tenantCode,String name){
		return getByHql("from Role where delFlag = :p1 and tenantCode=:p2 and name = :p3", new Parameter(Role.DEL_FLAG_NORMAL, tenantCode,name));
	}

//	@Query("from Role where delFlag='" + Role.DEL_FLAG_NORMAL + "' order by name")
//	public List<Role> findAllList();
//
//	@Query("select distinct r from Role r, User u where r in elements (u.roleList) and r.delFlag='" + Role.DEL_FLAG_NORMAL +
//			"' and u.delFlag='" + User.DEL_FLAG_NORMAL + "' and u.id=?1 or (r.user.id=?1 and r.delFlag='" + Role.DEL_FLAG_NORMAL +
//			"') order by r.name")
//	public List<Role> findByUserId(Long userId);

}
