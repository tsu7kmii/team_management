package com.example.team_management.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
        List<Management> incomplateList = managementService.getAllIncompleteManagementData();
        List<Management> complateList = managementService.getAllCompletionManagementData();
        Map<Integer, String> userMap = userService.getUserNameList();


        model.addAttribute("incomplate_list", incomplateList);
        model.addAttribute("complate_list", complateList);
        model.addAttribute("user_list", userMap);

        return "management/management";
    }

}
