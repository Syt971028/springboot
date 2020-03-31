package com.sun.hospital.controller;

import com.sun.hospital.entiy.EnumUser;
import com.sun.hospital.entiy.User;
import com.sun.hospital.service.DepartmentService;
import com.sun.hospital.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 孙耘田
 * @date 2020/3/13 - 20:28
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private DepartmentService departmentService;

    @RequestMapping("/userLogin")
    public String login(User user, Model model) {

        if (userService.login(user)) {
            if (userService.type(user).getUserType().equals(EnumUser.admin.name())) {
                model.addAttribute("username",user.getUsername());
                return "admin";
            } else if (userService.type(user).getUserType().equals(EnumUser.doctor.name())) {
                model.addAttribute("username",user.getUsername());
                return "doctor";
            } else {
                model.addAttribute("username",user.getUsername());
                return "function";
            }
            } else {
                model.addAttribute("msg", "用户名或密码错误!");
                return "index";
        }

    }

    @RequestMapping("/isregister")
    public void isregister(String username, HttpServletResponse response) throws IOException {
        if (userService.isregister(username)) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("false");

        }

    }

    @RequestMapping("/register")
    public String register(User user, String repassword, Model model) {
        if (userService.isregister(user.getUsername())) {
            if (user.getPassword().equals(repassword)) {
                if (userService.register(user)) {
                    model.addAttribute("msg", "注册成功");
                    model.addAttribute("username", user.getUsername());
                    model.addAttribute("depart", departmentService.findALLDept());
                    return "personal";
                } else {
                    model.addAttribute("msg", "未知错误");
                    return "registered";
                }
            } else {
                model.addAttribute("msg", "密码不同");
                return "registered";
            }
        } else {
            model.addAttribute("msg", "用户名已存在");
            return "registered";
        }
    }

    @RequestMapping("/findAllUsers")
    public String fandAllUsers(Model model) {
        List<User> users = userService.fandAllUser();
        model.addAttribute("user", users);
        return "admin";
    }

    @RequestMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Integer id, Model model) {

        User user = userService.findById(id);
        model.addAttribute("userById", user);

        return "adminUpdate";
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        boolean b = userService.deleteUser(id);
        if (b) {
            return "admin";
        } else {
            return "admin";
        }
    }

    @RequestMapping("/UserUpdate")
    public String userUpdate(Model model, int id, String userType) {
        boolean b = userService.updateUser(id, userType);

        if (b) {
            model.addAttribute("msg", "修改成功");
            return "admin";
        } else {
            model.addAttribute("msg", "修改失败");
            return "admin";
        }

    }
}
