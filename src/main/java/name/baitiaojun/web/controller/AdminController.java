package name.baitiaojun.web.controller;

import name.baitiaojun.domain.Admin;
import name.baitiaojun.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login.action", method = RequestMethod.POST)
    private ModelAndView login(HttpSession session, String name, String pwd) {
        Admin admin = adminService.login(name, pwd);
        ModelAndView modelAndView = new ModelAndView();
        if (admin != null) {
            session.setAttribute("admin", admin);
            modelAndView.addObject("admin", admin);
            modelAndView.setViewName("main");
            return modelAndView;
        } else {
            modelAndView.addObject("msg", "账户或密码错误！");
            modelAndView.setViewName("redirect:/login.jsp");
            return modelAndView;
        }
    }
}