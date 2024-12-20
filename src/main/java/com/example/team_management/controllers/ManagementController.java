package com.example.team_management.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.team_management.models.entity.Management;
import com.example.team_management.services.ManagementService;
import com.example.team_management.services.UserService;

/**
 * 進捗管理コントローラー
 */
@Controller
public class ManagementController {

    @Autowired
    ManagementService managementService;

    @Autowired
    UserService userService;

    /**
     * トップ画面表示
     * 
     * @param model モデル
     * @return トップ画面
     */
    @RequestMapping("/")
    public String viewToppage(Model model){

        // ログインユーザーが管理者かチェック
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                              .map(GrantedAuthority::getAuthority)
                              .anyMatch(role -> role.equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        return "index";
    }

    /**
     * 進行中・当年完了済み進捗画面表示
     * 
     * @param model モデル
     * @return 進行中・当年完了済み進捗画面
     */
    @RequestMapping("/view")
    public String viewManagementTable(Model model){

        // 現在が何年か取得
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        // 表示データの取得
        List<Management> incomplateList = managementService.getAllIncompleteManagementData();
        List<Management> complateList = managementService.getAllCompletionManagementData(year);
        Map<Integer, String> userMap = userService.getUserNameList();

        // delete_atの日付が古い順に並び替え
        complateList.sort(Comparator.comparing(Management::getDelete_at));

        model.addAttribute("incomplate_list", incomplateList);
        model.addAttribute("complate_list", complateList);
        model.addAttribute("user_list", userMap);

        return "management/management";
    }

    /**
     * 新規進捗追加登録画面表示
     * 
     * @param userDetails ユーザーディテール
     * @param model モデル
     * @return 新規進捗追加登録画面
     */
    @RequestMapping("/register_form")
    public String viewRegisterItem(@AuthenticationPrincipal UserDetails userDetails ,Model model){

        // IDと名前の組み合わせ取得
        Map<Integer, String> userMap = userService.getUserNameList();

        // ログインアカウントのID認証情報取得
        String email = userDetails.getUsername();
        Integer selectedUserId = userService.findIdByEmail(email);

        model.addAttribute("user_list", userMap);
        model.addAttribute("selected_user_id", selectedUserId);

        // 追加ページ
        return "management/register_item";
    }

    /**
     * 進捗登録
     * 
     * @param userId ユーザーID
     * @param subject 科目
     * @param link 課題リンク
     * @param status ステータス
     * @param comDate 完了予定日
     * @return リダイレクト先
     */
    @RequestMapping(value = "/item_register", method = RequestMethod.POST)
    public String itemRegister(@RequestParam("user_id") Integer userId,
                                @RequestParam("subject") String subject,
                                @RequestParam("link") String link,
                                @RequestParam("status") Integer status,
                                @RequestParam("completion_schedule") String comDate,
                                Model model){

                                    
        // 実行
        try {
            managementService.addManagementData(userId, subject, link, status, comDate);
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/view";
    }

    /**
     * 進捗内容編集・完了画面表示
     * 
     * @param id 管理ID
     * @param model モデル
     * @return 進捗内容編集・完了画面
     */
    @RequestMapping(value = "/view/edit/{id}", method = RequestMethod.GET)
    public String viewManagementEdit(@PathVariable String id,Model model){

        Management management = new Management();

        // 実行
        try {
            management = managementService.checkCanEdit(id);
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        // IDと名前の組み合わせ取得
        Map<Integer, String> userMap = userService.getUserNameList();

        model.addAttribute("user_list", userMap);
        model.addAttribute("management_data", management);

        return "management/edit_item";
    }

    /**
     * 進捗編集登録
     * 
     * @param managementId 管理ID
     * @param userId ユーザーID
     * @param subject 件名
     * @param link リンク
     * @param status ステータス
     * @param comDate 完了予定日
     * @return リダイレクト先
     */
    @RequestMapping(value = "/edit_register", method = RequestMethod.POST)
    public String itemEditRegister(@RequestParam("management_id") Integer managementId,
                                @RequestParam("user_id") Integer userId,
                                @RequestParam("subject") String subject,
                                @RequestParam("link") String link,
                                @RequestParam("status") Integer status,
                                @RequestParam("completion_schedule") String comDate,
                                Model model){

                            
        // 実行
        try {
            managementService.updateManagementData(managementId, userId, subject, link, status, comDate);
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/view";
    }

    /**
     * 進捗完了登録
     * 
     * @param id 管理ID
     * @return リダイレクト先
     */
    @RequestMapping(value = "/management/complate/{id}", method = RequestMethod.GET)
    public String managementComplateRegister(@PathVariable String id, Model model){

        // 実行
        try {
            managementService.deleteManagementDataById(id);
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/view";
    }

    /**
     * 年別完了済み進捗項目画面表示
     * 
     * @param model モデル
     * @return 年別完了済み進捗項目画面
     */
    @RequestMapping("/view/history")
    public String viewManagementHistoryLog(Model model){

        List<Integer> yaers = managementService.getComplateYearData();

        model.addAttribute("historys", yaers);

        return "management/history";
    }

    /**
     * 年別完了済み進捗画面表示
     * 
     * @param year 年
     * @param model モデル
     * @return 年別完了済み進捗画面
     */
    @RequestMapping(value = "/view/history/{year}", method = RequestMethod.GET)
    public String viewManagementHistory(@PathVariable String year, Model model){

        // 表示データの取得
        List<Management> yearComplateList = managementService.getAllCompletionManagementData(year);
        Map<Integer, String> userMap = userService.getUserNameList();

        // delete_atの日付が古い順に並び替え
        yearComplateList.sort(Comparator.comparing(Management::getDelete_at));

        model.addAttribute("history_data", yearComplateList);
        model.addAttribute("user_list", userMap);

        return "management/year_history";
    }
}
