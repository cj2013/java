package com.chmc.feignserver.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceFolderHytrix implements ISchedualServiceFolder{
    @Override
    public String getName(String name){
        return "get folder name " + name  + " error";
    }
}
