package com.company.sharding_sphere;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.sharding_sphere.entity.Course;
import com.company.sharding_sphere.mapper.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-22 21:16:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestClass2 {

    @Resource
    private CourseMapper courseMapper;


    @Test
    public void addCourse() {
        Course course = new Course();
        course.setCname("Java");
        course.setStatus("Normal");
        course.setUserId(1000L);
        courseMapper.insert(course);
    }

    @Test
    public void addCourseBatch() {
        for (int i = 1; i <= 10; i++) {
            Course course = new Course();
            course.setCname("Java" + i);
            course.setStatus("Normal" + i);
            course.setUserId(1000L);
            courseMapper.insert(course);
        }
    }

    @Test
    public void testFind() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 592446424088576000L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }


    // 测试水平分库


    @Test
    public void test1() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            Course course = new Course();
            long userId;
            do {
                userId = random.nextInt(10000);
            } while (userId < 1000L || userId > 10000L);
            course.setUserId(userId);
            course.setCname("Java" + userId);
            course.setStatus("Normal");
            courseMapper.insert(course);
        }
    }


    @Test
    public void test2() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", 592461605468372993L);
        queryWrapper.eq("user_id", 6787L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }

}
