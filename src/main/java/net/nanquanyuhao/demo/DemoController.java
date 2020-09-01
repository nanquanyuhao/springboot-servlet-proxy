package net.nanquanyuhao.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public Map<String, Object> demo(){

        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 23);
        return map;
    }
}
