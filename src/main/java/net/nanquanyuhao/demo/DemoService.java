package net.nanquanyuhao.demo;

import net.nanquanyuhao.proxy.conf.TargetObjectResult;

import java.util.Map;

public interface DemoService {

    Map<String, Object> getInfo();

    TargetObjectResult ahjustContainsKeyAndReturn(String key);
}
