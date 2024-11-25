package com.example.team_management.controllers;

import java.util.*;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.team_management.models.management.Management;
import com.example.team_management.services.ManagementService;
import com.example.team_management.services.UserService;

@Controller
public class ManagementController {

    @Autowired
    ManagementService managementService;

    @Autowired
    UserService userService;


    @RequestMapping("/")
    public String tableview(Model model){

        // top page
        return "index";
    }

    @RequestMapping("/management")
    public String managamentView(Model model){

        // 一覧表示
        List<Management> incomplateList = managementService.getAllIncompleteManagementData();
        List<Management> complateList = managementService.getAllCompletionManagementData();
        Map<Integer, String> userMap = userService.getUserNameList();


        model.addAttribute("incomplate_list", incomplateList);
        model.addAttribute("complate_list", complateList);
        model.addAttribute("user_list", userMap);

        return "management/management";
    }

    @RequestMapping("/register_form")
    public String formItem(@AuthenticationPrincipal UserDetails userDetails ,Model model){

        Map<Integer, String> userMap = userService.getUserNameList();

        String email = userDetails.getUsername();
        Integer selectedUserId = userService.findIdByEmail(email);


        model.addAttribute("user_list", userMap);
        model.addAttribute("selected_user_id", selectedUserId);

        // 追加ページ
        return "management/register_item";
    }

    @RequestMapping(value = "/item_register", method = RequestMethod.POST)
    public String itemRegister(@RequestParam("user_id") Integer userId,
                                @RequestParam("subject") String subject,
                                @RequestParam("link") String link,
                                @RequestParam("status") Integer status,
                                @RequestParam("completion_schedule") String comDate){

                                    
        // 追加処理
        Management management = new Management();  
        management.setUser_id(userId);
        management.setSubject(subject);
        management.setLink(link);
        management.setStatus(status);
        management.setCompletion_schedule(Date.valueOf(comDate));  

        boolean result = managementService.addManagementData(management);

        if (result){
            return "redirect:/management";
        } else {
            return "redirect:/error/add_management_error";
        }
    }


    
    @RequestMapping(value = "/management/edit/{id}", method = RequestMethod.GET)
    public String managementEdit(@PathVariable String id,Model model){

        // 編集
        Map<Integer, String> userMap = userService.getUserNameList();
        Management management = managementService.getManagementDataById(id);

        if (management.getDelete_at() != null){
            // 存在しないidまたは完了済の場合
            return "redirect:/error/not_found_edit_item_error";
        }



        model.addAttribute("user_list", userMap);
        model.addAttribute("management_data", management);

        return "management/edit_item";
    }

    @RequestMapping(value = "/edit_register", method = RequestMethod.POST)
    public String itemEditRegister(@RequestParam("management_id") Integer managementId,
                                @RequestParam("user_id") Integer userId,
                                @RequestParam("subject") String subject,
                                @RequestParam("link") String link,
                                @RequestParam("status") Integer status,
                                @RequestParam("completion_schedule") String comDate){

                                    
        // 変更処理
        Management management = new Management(); 
        management.setManagement_id(managementId); 
        management.setUser_id(userId);
        management.setSubject(subject);
        management.setLink(link);
        management.setStatus(status);
        management.setCompletion_schedule(Date.valueOf(comDate));  

        boolean result = managementService.updateManagementData(management);

        if (result){
            return "redirect:/management";
        } else {
            return "redirect:/error/edit_management_error?parms=" + managementId;
        }
    }

    @RequestMapping(value = "/management/complate/{id}", method = RequestMethod.GET)
    public String managementComplate(@PathVariable String id){

        // 完了
        boolean result = managementService.deleteManagementDataById(id);
        

        if (result){
            return "redirect:/management";
        } else {
            return "redirect:/error/not_complate_management_error?parms=" + id;
        }

    }

    // "redirect:/error/used_email_error?parms=" + signupValidation.getEmail();


}
