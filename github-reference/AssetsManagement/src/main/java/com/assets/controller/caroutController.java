	package com.assets.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assets.model.car;
import com.assets.model.carout;
import com.assets.model.User;
import com.assets.service.carService;
import com.assets.service.caroutService;
import com.assets.service.carService;
import com.assets.service.OrganService;
import com.assets.tool.Utils;

@Controller  
@RequestMapping("/carout")  
public class caroutController {  
    @Autowired  
    private caroutService Service;  
    
    /**  
     * 获取所有用户列表  
     * @param request  
     * @return  
     */  
    @RequestMapping("/getAll.do")  
    public String getAll(HttpServletRequest request,Model model, HttpSession session){  
    	try {
            List<carout> user = Service.findAll((int) session.getAttribute("useoId"));  
            model.addAttribute("records", user);  
            request.setAttribute("records", user);
            return "/car/out/list";	
		} catch (Exception e) {
			e.printStackTrace();
			return "/system/error";
		}
    }  
    
    
    @RequestMapping("/getAll_record.do")  
    public String getAll_record(HttpServletRequest request,Model model, HttpSession session){  
    	try {
    		
            List<carout> user = Service.getAll_record(1,(int) session.getAttribute("useoId"));  
            model.addAttribute("records", user);  
            request.setAttribute("records", user);
            return "/car/out/list_record";	
		} catch (Exception e) {
			e.printStackTrace();
			return "/system/error";
		}
    }  
    
    @RequestMapping("/getStatus.do")  
    public String getStatus(HttpServletRequest request,Model model, HttpSession session,int status){  
    	try {
            List<carout> user = Service.getAll_record(status,(int) session.getAttribute("useoId"));  
            model.addAttribute("records", user);  
            request.setAttribute("records", user);
            return "/car/out/list_record";	
		} catch (Exception e) {
			e.printStackTrace();
			return "/system/error";
		
		}
    }  
    @RequestMapping("/out_No.do")  
    public String nameNo(Model model,int id) {
    	model.addAttribute("id", id);
    	return "/car/out/outNo";  
	}
    @RequestMapping("/out_To.do")  
    public String nameTo(Model model,int id) {
    	model.addAttribute("id", id);
    	return "/car/out/outTo";  
	}
    
   
    @RequestMapping("/update_acceptout.do")  
    public void accep(Model model,carout carout,HttpServletResponse resp, HttpSession session,int yes){
    	String jsonStr="{\"code\":\"1001\",\"msg\":\"操作失败\"}"; 
    	try {
    		User u=(User) session.getAttribute("user");
    		carout out=Service.findById(Integer.valueOf(carout.getId()));
    		out.setApprovaldate(Utils.date());
			out.setMdtm(Utils.date());
			out.setApprovalremarks(carout.getRemarks());
			out.setApprover(u.getName());
    		//通过
    		if (yes==1) {
				out.setStatus(2);
			}else{
				//拒绝
				out.setStatus(3);
			}
    		Service.update(out);
    		jsonStr ="{\"code\":\"1000\",\"msg\":\"操作成功\"}";
		} catch (Exception e) {
			e.printStackTrace();
			jsonStr="{\"code\":\"1001\",\"msg\":\"操作失败\"}";
		}
         Utils.commend(resp, jsonStr);
    }
    
    
    
    
    
    
    /**
     * 根据name查询
     * @param request
     * @param session
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/getName.do")  
    public String  getName(HttpServletRequest request, HttpSession session,Model model, carout user){
    	try {
    		 List<carout> list=new ArrayList<carout>();
    		 user.setOid((int) session.getAttribute("useoId"));
	         list=Service.select_code(user);
	         model.addAttribute("records", list);  
	         return "/car/out/list";	   	
		} catch (Exception e) {
			e.printStackTrace();
			return "/system/error";
		}
    }
    
    /**
     * 批量通过
     * @param model
     * @param resp
     * @param req
     */
    @RequestMapping("/updatebatch_To.do")  
    public void batchTo(Model model,HttpServletResponse resp,HttpServletRequest req, HttpSession session) {
    	String jsonStr ="{\"code\":\"1001\",\"msg\":\"操作失败\"}";
    	try {
    		 User u=(User) session.getAttribute("user"); 
    		String[] ids=req.getParameterValues("ids");
        	if (Service.batchTo(ids,u.getName())) {
        		jsonStr ="{\"code\":\"1000\",\"msg\":\"操作成功\"}";
			}
		} catch (Exception e) {
			jsonStr="{\"code\":\"1001\",\"msg\":\"操作失败\"}";
		}
    	Utils.commend(resp, jsonStr);	
	}
    
    /**
     * 批量通过
     * @param model
     * @param resp
     * @param req
     */
    @RequestMapping("/updatebatch_No.do")  
    public void batchNo(Model model,HttpServletResponse resp,HttpServletRequest req, HttpSession session) {
    	String jsonStr ="{\"code\":\"1001\",\"msg\":\"操作失败\"}";
    	try {
    		String[] ids=req.getParameterValues("ids");
    		 User u=(User) session.getAttribute("user"); 
        	if (Service.batchNo(ids,u.getName())) {
        		jsonStr ="{\"code\":\"1000\",\"msg\":\"操作成功\"}";
			}
		} catch (Exception e) {
			jsonStr="{\"code\":\"1001\",\"msg\":\"操作失败\"}";
		}
    	Utils.commend(resp, jsonStr);	
	}
    
}  





