package com.company;

import com.company.sharding_sphere.mapper.CourseMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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


}
