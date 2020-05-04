package cn.codeprobe.community;

import cn.codeprobe.community.dao.UserMapper;
import cn.codeprobe.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(111);
        System.out.println(user);
        user = userMapper.selectByName("liubei");
        System.out.println(user);
        user = userMapper.selectByEmail("nowcoder102@sina.com");
        System.out.println(user);
    }
    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test");
        user.setCreateTime(new Date());
        user.setEmail("test@163.com");
        System.out.println(user.getId());
        int i = userMapper.insertUser(user);
        System.out.println(user);
        System.out.println(user.getId());
        System.out.println(i);
    }
    @Test
    public void testUpdateUser(){
        int i = userMapper.updateStatus(150, 1);
        System.out.println(i);
        int i1 = userMapper.updateHeader(150, "http://yzjblog.com");
        System.out.println(i1);
        int i2 = userMapper.updatePassword(150, "123123");
        System.out.println(i2);
    }
}
