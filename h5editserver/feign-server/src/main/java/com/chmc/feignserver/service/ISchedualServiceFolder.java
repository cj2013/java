package com.chmc.feignserver.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "edit-server-folder")
public interface ISchedualServiceFolder {
    @RequestMapping(value = "/folder/getname", method = RequestMethod.GET)
    String getName(@RequestParam(value = "name") String name);
}
