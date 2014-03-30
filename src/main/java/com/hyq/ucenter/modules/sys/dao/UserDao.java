
package com.hyq.ucenter.modules.sys.dao;

import java.util.Date;
import org.springframework.stereotype.Repository;
import com.hyq.ucenter.common.persistence.BaseDao;
import com.hyq.ucenter.common.persistence.Parameter;
import com.hyq.ucenter.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class UserDao extends BaseDao<User> {
	
	/*public List<User> findAllList(String tenantCode) {
		return find("from User where tenantCode=:p and delFlag=:p1 order by id", new Parameter(tenantCode,User.DEL_FLAG_NORMAL));
	}*/
	
	public User findByLoginName(String tenantCode,String loginName){
		
		
		return getByHql("from User where tenantCode= :p1 and loginName = :p2 and delFlag = :p3", new Parameter(tenantCode,loginName, User.DEL_FLAG_NORMAL));
	}

	public int updatePasswordById(String newPassword, Long id){
		return update("update User set password=:p1 where id = :p2", new Parameter(newPassword, id));
	}
	
	public int updateLoginInfo(String loginIp, Date loginDate, Long id){
		return update("update User set loginIp=:p1, loginDate=:p2 where id = :p3", new Parameter(loginIp, loginDate, id));
	}
	
}
