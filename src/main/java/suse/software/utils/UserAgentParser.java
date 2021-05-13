package suse.software.utils;

import lombok.Data;

/**
 * 简介: 用于解析 浏览器的User-Agent 对象的简单工具类
 */

@Data
public class UserAgentParser {
    private String url;
    private String platform;

    public UserAgentParser(String url){
        this.url=url;
        this.setPlatform("pc");
    }
}
