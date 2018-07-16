package com.chmc.feignserver.service;

import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceUserHytrix implements IScheduleServiceUser {
    @Override
    public String getName(String name){
        return "Get user:" + name + "error.";
    }
}
