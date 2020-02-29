package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
//        List<User> list=new ArrayList<>();
//        User user1=new User(1,"张三","男",18,"湖南省益阳市","294972606","294972606@qq.com");
//        User user2=new User(2,"李四","男",18,"湖南省益阳市","294972606","294972606@qq.com");
//        list.add(user1);
//        list.add(user2);
        String sql="select * from user";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return list;
    }

    @Override
    public User login(User user) {
        String sql="select * from user where username= ? and password =?";
        try {
            User loginUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
            return loginUser;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
