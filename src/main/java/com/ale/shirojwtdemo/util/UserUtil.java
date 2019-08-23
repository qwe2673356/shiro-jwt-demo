package com.ale.shirojwtdemo.util;

import com.ale.shirojwtdemo.entity.User;
import org.apache.shiro.SecurityUtils;

public class UserUtil {

    public static void setSessionUser(User user) {
        SecurityUtils.getSubject().getSession().setAttribute(UserConstants.USER_SESSION, user);
    }
}
