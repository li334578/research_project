package com.company.sharding_sphere;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.sharding_sphere.entity.Course;
import com.company.sharding_sphere.entity.User;
import com.company.sharding_sphere.mapper.CourseMapper;
import com.company.sharding_sphere.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-24 20:16:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestClass3 {
    @Resource
    private UserMapper userMapper;

    @Resource
    private CourseMapper courseMapper;

    @Test
    public void testMethod1() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("法外狂徒张三" + i);
            user.setStatus("Normal");
            userMapper.insert(user);
        }
    }

    @Test
    public void testMethod2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", 592813884675457025L);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testMethod3() {
        List<Course> courses = courseMapper.selectList(null);
        List<User> users = userMapper.selectList(null);
        System.out.println("----------------------------");
        System.out.println(courses);
        System.out.println("----------------------------");
        System.out.println(users);
    }
}
