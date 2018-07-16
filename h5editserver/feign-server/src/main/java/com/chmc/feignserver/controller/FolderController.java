package com.chmc.feignserver.controller;

import com.chmc.feignserver.service.ISchedualServiceFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/folder")
public class FolderController {
    @Autowired
    ISchedualServiceFolder iSchedualServiceFolder;

    @RequestMapping(value = "/getname")
    public String getName(@RequestParam String name){
        return iSchedualServiceFolder.getName(name);
    }
}
