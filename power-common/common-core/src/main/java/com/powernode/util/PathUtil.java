package com.powernode.util;

import org.springframework.util.AntPathMatcher;

public class PathUtil {

    public static boolean pathIsMatch(String[] patterns,String path){
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        for(String pattern : patterns){
            if(antPathMatcher.match(pattern,path))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String pattern = "/app/*.html";
        System.out.println(antPathMatcher.match(pattern,"/app/hello.htm"));
    }
}
