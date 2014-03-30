/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hyq.ucenter.modules.sys.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyq.ucenter.common.persistence.Page;
import com.hyq.ucenter.common.service.BaseService;
import com.hyq.ucenter.modules.sys.dao.OfficeDao;
import com.hyq.ucenter.modules.sys.entity.Office;
import com.hyq.ucenter.modules.sys.entity.User;
import com.hyq.ucenter.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends BaseService {

	@Autowired
	private OfficeDao officeDao;
	
	public Office get(Long id) {
		return officeDao.get(id);
	}
	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}
	public Page<Office> findOfficePage(Page<Office> page, Office office){
			User user =UserUtils.getUser();
			DetachedCriteria dc = officeDao.createDetachedCriteria();
			dc.add(dataScopeFilter(user, dc.getAlias(), ""));
			dc.add(Restrictions.eq("type",office.getType()));     			  //根据机构类型
			dc.add(Restrictions.eq("tenantCode",office.getTenantCode()));     //所属租户编码
			dc.add(Restrictions.eq("delFlag", Office.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("code"));
		return officeDao.find(page,dc);
		
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		office.setParent(this.get(office.getParent().getId()));
		String oldParentIds = office.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		office.setParentIds(
				       (!StringUtils.isNotBlank(office.getParent().getParentIds())?"":office.getParent().getParentIds())
						+office.getParent().getId()+","
						);
		officeDao.clear();
		officeDao.save(office);
		// 更新子节点 parentIds
		List<Office> list = officeDao.findByParentIdsLike("%,"+office.getId()+",%");
		for (Office e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, office.getParentIds()));
		}
		officeDao.save(list);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		officeDao.deleteById(id, "%,"+id+",%");
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
}
