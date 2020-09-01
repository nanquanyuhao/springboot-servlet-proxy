package net.nanquanyuhao.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/demo")
    public Map<String, Object> demo(){

        return demoService.getInfo();
    }
}
