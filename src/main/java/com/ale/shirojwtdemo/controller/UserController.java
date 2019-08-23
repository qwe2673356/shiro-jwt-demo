package com.ale.shirojwtdemo.controller;


import com.ale.shirojwtdemo.dao.UserDao;
import com.ale.shirojwtdemo.entity.Permission;
import com.ale.shirojwtdemo.entity.Role;
import com.ale.shirojwtdemo.entity.User;
import com.ale.shirojwtdemo.util.EncryUtil;
import com.ale.shirojwtdemo.util.UserConstants;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/list")
    public List<User> list(@Param("offset") int offset,@Param("limit") int limit, String username){
        User u = new User();u.setUsername(username);
        return userDao.list(offset,limit);
    }

    @RequestMapping("/count")
    public int count(){
        return userDao.count();
    }

    @RequestMapping("/update")
    public void update(User user){

        userDao.update(user);
    }

    @RequestMapping("/findById")
    public User findById(@RequestParam("id") String id){
        Integer uid = id ==null?-1:Integer.parseInt(id);
        return  userDao.findById(uid);
    }
    @RequestMapping("/findByUserName")
    public User findByUserName(@RequestParam("username") String username){
        return userDao.findByUserName(username);
    }
    @RequestMapping("/findByEmail")
    public User findByEmail(@RequestParam("email")String email){
        return userDao.findByEmail(email);
    }

    @RequestMapping("/save")
    public void save(User user){
        user.setCreatetime(new Date());
        user.setSalt(UserConstants.SALT);
        user.setPassword(EncryUtil.encryPwd(user.getPassword(), UserConstants.SALT));
        userDao.save(user);
    }

    @RequestMapping("/batchSave")
    public void batchSave(List<User> userList){
        userList = new ArrayList<User>();

        User u1 = new User();
        u1.setUsername("u1");

        User u2 = new User();
        u2.setUsername("u2");

        userList.add(u1);
        userList.add(u2);

        userDao.batchSave(userList);
    }

    @RequestMapping("/getRoles")
    public List<Role> getRoles(int userid){
        return userDao.getRoles(userid);
    }

    @RequestMapping("/saveUserRoles")
    public void saveUserRoles(List<Role> roleList,int userid){
        userDao.saveUserRoles(roleList,userid);
    }

    @RequestMapping("/getPermissions")
    public List<Permission> getPermissions(int userid){
       return userDao.getPermissions(userid);
    }

}
