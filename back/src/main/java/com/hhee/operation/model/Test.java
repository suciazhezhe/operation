package com.hhee.operation.model;

import com.hhee.operation.core.http.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class Test {

    @GetMapping(value = "/testStart")
    public HttpResult testStart(){
        return HttpResult.ok("已启动！");
    }
}
