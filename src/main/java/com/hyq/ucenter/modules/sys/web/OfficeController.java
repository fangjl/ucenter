
package com.hyq.ucenter.modules.sys.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hyq.ucenter.common.config.Global;
import com.hyq.ucenter.common.persistence.Page;
import com.hyq.ucenter.common.web.BaseController;
import com.hyq.ucenter.modules.sys.entity.Office;
import com.hyq.ucenter.modules.sys.entity.User;
import com.hyq.ucenter.modules.sys.service.OfficeService;
import com.hyq.ucenter.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) Long id) {
		if (id!=null){
			return officeService.get(id);
		}else{
			
			return new Office(UserUtils.getUser().getTenantCode());
		}
	}

	
	
	//*******************************气站
	
	/**
	 * 
	 * @param extId
	 * @param type
	 * @param grade
	 * @param response
	 * @return
	 */
	
	
	@RequestMapping(value ="stationList")
	public String stationList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		office.setType("2");   //气站类型
		Page<Office> page = officeService.findOfficePage(new Page<Office>(request, response), office);
        model.addAttribute("page", page);
		return "modules/sys/officeStationList";
	}

	@RequestMapping(value = "stationForm")
	public String stationForm(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(office.getParent().getArea());
		}
		model.addAttribute("office", office);
		return "modules/sys/officeStationForm";
	}
	
	@RequestMapping(value = "stationSave")
	public String stationSave(Office office, Model model, RedirectAttributes redirectAttributes) {
		office.setType("2");    //门店
		office.setGrade("2");    
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/office/stationList";
		}
		if (!beanValidator(model, office)){
			return stationForm(office, model);
		}
		officeService.save(office);
		addMessage(redirectAttributes, "保存门店" + office.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/sys/office/stationList";
	}
	
	@RequestMapping(value = "stationDelete")
	public String stationDelete(Long id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/office/stationList";
		}
		if (get(id).isRoot()){
			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
		}else{
			officeService.delete(id);
			addMessage(redirectAttributes, "删除门店成功");
		}
		return "redirect:"+Global.getAdminPath()+"/sys/office/stationList";
	}

	
	
	//****************************************门店
	
	

	
	@RequestMapping(value ="storeList")
	public String storeList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		office.setType("3");   //门店类型
		Page<Office> page = officeService.findOfficePage(new Page<Office>(request, response), office);
        model.addAttribute("page", page);
		return "modules/sys/officeStoreList";
	}

	@RequestMapping(value = "storeForm")
	public String storeForm(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(office.getParent().getArea());
		}
		model.addAttribute("office", office);
		return "modules/sys/officeStoreForm";
	}
	
	@RequestMapping(value = "storeSave")
	public String storeSave(Office office, Model model, RedirectAttributes redirectAttributes) {
		office.setType("3"); //门店类型
		office.setGrade("3");//三级机构
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/office/storeList";
		}
		if (!beanValidator(model, office)){
			return storeForm(office, model);
		}
		officeService.save(office);
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/sys/office/storeList";
	}
	
	@RequestMapping(value = "storeDelete")
	public String storeDelete(Long id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/sys/office/storeList";
		}
		if (get(id).isRoot()){
			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
		}else{
			officeService.delete(id);
			addMessage(redirectAttributes, "删除机构成功");
		}
		return "redirect:"+Global.getAdminPath()+"/sys/office/storeList";
	}
	
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) Long extId, @RequestParam(required=false) Long type,
			@RequestParam(required=false) Long grade, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findAll();
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && Integer.parseInt(e.getType()) <= type.intValue()))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
