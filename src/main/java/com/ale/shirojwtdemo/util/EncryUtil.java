package com.ale.shirojwtdemo.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EncryUtil {

    public static String encryPwd(String pwd, String salt){
        SimpleHash simpleHash = new SimpleHash(UserConstants.ALGORITHM_NAME, ByteSource.Util.bytes(pwd).getBytes(),salt,UserConstants.HASHITERATIONS);
        System.out.println("EncryUtil.encryPwd() --> " + simpleHash.toHex());
        return  simpleHash.toHex();
    }

    public static void main(String[] args) {
        encryPwd("1","MYSALT");
    }


}
