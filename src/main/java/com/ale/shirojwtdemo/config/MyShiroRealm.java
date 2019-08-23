package com.ale.shirojwtdemo.config;

import com.ale.shirojwtdemo.dao.UserDao;
import com.ale.shirojwtdemo.entity.Permission;
import com.ale.shirojwtdemo.entity.Role;
import com.ale.shirojwtdemo.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserDao userDao;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user  = (User)principals.getPrimaryPrincipal();
        List<Role> roles = userDao.getRoles(user.getId());
        Set<String> roleNames = roles.stream().map(Role::getCode).collect(Collectors.toSet());
        List<Permission> permissionList = userDao.getPermissions(user.getId());
        Set<String> permissions = permissionList.stream().filter(p -> !StringUtils.isEmpty(p.getCode()))
                .map(Permission::getCode).collect(Collectors.toSet());

        authorizationInfo.setRoles(roleNames);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User User = userDao.findByUserName(username);
        System.out.println("----->>User="+User);
        if(User == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                User, //用户名
                User.getPassword(), //密码
                ByteSource.Util.bytes(User.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}