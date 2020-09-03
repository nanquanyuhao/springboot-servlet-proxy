package net.nanquanyuhao.demo;

import net.nanquanyuhao.proxy.conf.TargetObject;
import net.nanquanyuhao.proxy.conf.TargetObjectResult;
import org.apache.http.client.utils.URIUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
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

    @Override
    public TargetObjectResult ahjustContainsKeyAndReturn(String key) {

        String value = key + "/";
        TargetObjectResult result = new TargetObjectResult();

        // 示例，如果符合 /ceph/1/ or /ceph/2/ 即包含
        if ("/ceph/1/".equals(value) || "/ceph/2/".equals(value)) {

            TargetObject targetObject = new TargetObject();
            targetObject.setTargetUri("http://192.168.235.129:8080");
            try {
                targetObject.setTargetHost(URIUtils.extractHost(new URI(targetObject.getTargetUri())));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            result.setTargetObject(targetObject);
        }

        return result;
    }
}
