package com.sun.hospital.controller;

import com.sun.hospital.entiy.Personal;
import com.sun.hospital.service.PersonService;
import com.sun.hospital.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/27 - 12:52
 */
@Controller
public class PersonController {
    @Resource
    private PersonService personService;

    @Resource
    private UserService userService;


    @RequestMapping("savePerson")
    public String savePerson(Personal personal, String userType, String username) {
        personService.savePerson(personal);
        userService.updateUserByName(userType, username);
        return "index";
    }

    @RequestMapping("/findAllDoctor/{username}")
    public String findAllDoctor(Model model,@PathVariable String username) {
        List<Personal> list = personService.findDoctorPerson();
        model.addAttribute("Doctor", list);
        model.addAttribute("username", username);
        return "function";
    }

    @RequestMapping("/Conversation/{username}")
    public ModelAndView conversation(@PathVariable String username,Model model, HttpServletRequest request) throws UnknownHostException {
        ModelAndView mav = new ModelAndView("/Conversation");
        mav.addObject("username", username);
        mav.addObject("webSocketUrl", "ws://"+InetAddress.getLocalHost().
                getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/Conversation");
        return mav;
    }


}
