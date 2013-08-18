package com.mopo.web;

public class MopoTest {
    public static void main(String[] args) {
        String jsonStr = "{'type':1,'page':1}";
        String s = HttpUtil.sendPost("http://mopo.b3log.org/list", jsonStr,
                null);
        System.out.println(s);
    }

}
