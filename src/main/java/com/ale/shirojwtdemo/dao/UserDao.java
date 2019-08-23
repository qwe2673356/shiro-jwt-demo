package com.ale.shirojwtdemo.dao;

import com.ale.shirojwtdemo.entity.Permission;
import com.ale.shirojwtdemo.entity.Role;
import com.ale.shirojwtdemo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    int count();

    List<User> list(int offset, int limit);

    void update(User user);

    User findById(@Param("id") Integer id);

    User findByUserName(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    void save(@Param("user") User user);

    void batchSave(@Param("userList")List<User> userlist);

    List<Role> getRoles(@Param("userid")int userid);

    void saveUserRoles(@Param("roleList") List<Role> roleList,@Param("userid") int userid);

    List<Permission> getPermissions(@Param("userid")int userid);


}
