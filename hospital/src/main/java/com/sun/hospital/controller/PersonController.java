package com.sun.hospital.controller;

import com.sun.hospital.entiy.Personal;
import com.sun.hospital.service.PersonService;
import com.sun.hospital.service.RelationService;
import com.sun.hospital.service.UserService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.awt.SunHints;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

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

    @Resource
    private RelationService relationService;


    @RequestMapping("savePerson")
    public String savePerson(Personal personal, String userType, String username) {
        personService.savePerson(personal);
        userService.updateUserByName(userType, username);
        return "index";
    }

    @RequestMapping("/findAllDoctor/{username}")
    public String findAllDoctor(Model model,@PathVariable(value = "username") String username) {
        List<Personal> list = personService.findDoctorPerson();
        model.addAttribute("Doctor", list);
        model.addAttribute("username", username);
        return "function";
    }

    @RequestMapping("/talk")
    public ModelAndView conversation(String username,String pname,
                                     HttpServletRequest request) throws UnknownHostException {

        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> param : params.entrySet()) {

            pname = StringUtils.join(param.getValue());
        }

        ModelAndView mav = new ModelAndView("/Conversation");
        mav.addObject("username", username);
        mav.addObject("webSocketUrl", "ws://"+InetAddress.getLocalHost().
                getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/Conversation/"+username+"/"+pname);
        mav.addObject("pname", pname);
        System.out.println("ws://"+InetAddress.getLocalHost().
                getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/Conversation/"+username+"/"+pname);
        System.out.println("controller:"+"username="+username+"panme="+pname);
        return mav;
    }

    @RequestMapping(value = "/findPatient/{username}")
    public String findPatient(@PathVariable(value = "username") String username,Model model){
        List<Personal> patient = relationService.findPatient(username);
        model.addAttribute("patients",patient);
        return "doctor";
    }




}
