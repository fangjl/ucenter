
package com.hyq.ucenter.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.hyq.ucenter.common.config.Global;
import com.hyq.ucenter.common.web.BaseController;
import com.hyq.ucenter.modules.sys.entity.Tenant;
import com.hyq.ucenter.modules.sys.service.RegisterService;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class RegsiterController extends BaseController {

	@Autowired
	private RegisterService registerService;
	
	@RequestMapping(value = "/register" ,method = RequestMethod.GET)
	public String regsiter(Model model){
		return "modules/sys/register";
	}
	@RequestMapping(value = "/register" ,method = RequestMethod.POST)
	public String regsiter(Tenant tenant, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		// 保存用户信息
		registerService.regsiter(tenant);
		if (!beanValidator(model, tenant)){
			return regsiter(model);
		}
		addMessage(redirectAttributes, "注册" + tenant.getTenantName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "checkTenantCode")
	public String checkLoginName(String tenantCode) {
		if (tenantCode !=null && registerService.findTenantByCode(tenantCode) == null) {
			return "true";
		}
		return "false";
	}

	

}
