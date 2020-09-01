package net.nanquanyuhao.demo;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public Map<String, Object> getInfo() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 23);
        return map;
    }
}
