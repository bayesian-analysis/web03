package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;

public interface UserDao {
    public List<User> findAll();
    public User login(User user);
}
