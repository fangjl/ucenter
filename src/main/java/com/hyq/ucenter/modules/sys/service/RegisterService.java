package com.hyq.ucenter.modules.sys.service;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hyq.ucenter.common.security.Digests;
import com.hyq.ucenter.common.service.BaseService;
import com.hyq.ucenter.common.utils.Encodes;
import com.hyq.ucenter.modules.sys.dao.MenuDao;
import com.hyq.ucenter.modules.sys.dao.OfficeDao;
import com.hyq.ucenter.modules.sys.dao.RoleDao;
import com.hyq.ucenter.modules.sys.dao.TenantDao;
import com.hyq.ucenter.modules.sys.dao.UserDao;
import com.hyq.ucenter.modules.sys.entity.Office;
import com.hyq.ucenter.modules.sys.entity.Role;
import com.hyq.ucenter.modules.sys.entity.Tenant;
import com.hyq.ucenter.modules.sys.entity.User;
@Service
@Transactional(readOnly = true)
public class RegisterService extends BaseService {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private TenantDao tenantDao;
	@Autowired
	private MenuDao menuDao;	
	
	public Office get(String id) {
		return officeDao.get(id);
	}
	@Transactional(readOnly = false)
	public void regsiter(Tenant t) {
		
		tenantDao.save(t);
		
		Date d = new Date();
		t.setCreateDate(d);
		Office o = new Office();
		o.setTenantCode(t.getTenantCode());
		o.setCode(t.getTenantCode()+"00");
		o.setName(t.getTenantName());
		o.setEmail(t.getEmail());
		o.setMaster(t.getMaster());
		o.setPhone(t.getPhone());
		o.setType("1");  //公司
		o.setCreateDate(d);
		o.setGrade("1"); //一级
		officeDao.save(o);
		
		
		
		Role r = new Role();
		r.setTenantCode(t.getTenantCode());
		r.setName("系统管理员");
		r.setOffice(o);
		r.setCreateDate(d);
		r.setDataScope(Role.DATA_SCOPE_ALL);  //所有数据
		r.setOfficeIds(o.getId()+"");
		
	/*	ArrayList<Office> officeList = Lists.newArrayList();
		officeList.add(o);
		r.setOfficeList(officeList);*/
		
		
		r.setMenuList(menuDao.findAllList());
		r.setRemarks("系统管理员有最高系统权限");
		roleDao.save(r);
		
		
		User user = new User();
		user.setOffice(o);
		user.setTenantCode(t.getTenantCode());
		user.setName(t.getMaster());
		user.setEmail(t.getEmail());
		user.setPassword(entryptPassword("admin"));
		user.setPhone(t.getPhone());
		user.setUserType("1");    //系统管理员
		user.setLoginName("admin@"+t.getTenantCode());
		user.setMobile(t.getPhone());
		user.setNo("00000");
		user.setCreateDate(d);
		ArrayList<Role> roleList = Lists.newArrayList();
		
		roleList.add(r);
		
		user.setRoleList(roleList);
		userDao.save(user);
	}

	public Tenant findTenantByCode(String tenantCode){
		
		return tenantDao.findTenantByCode(tenantCode);
	}
	
	
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	@Autowired
	public void setOfficeDao(OfficeDao officeDao) {
		this.officeDao = officeDao;
	}
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	
	
}
