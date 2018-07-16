package com.chmc.feignserver.controller;

import com.chmc.feignserver.service.IScheduleServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IScheduleServiceUser iScheduleServiceUser;

    @RequestMapping(value = "/getname", method = RequestMethod.GET)
    public String getName(@RequestParam String name){
        return iScheduleServiceUser.getName(name);
    }
}
