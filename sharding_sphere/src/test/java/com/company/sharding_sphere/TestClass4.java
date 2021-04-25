package com.company.sharding_sphere;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.sharding_sphere.entity.Dic;
import com.company.sharding_sphere.mapper.DicMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-24 20:48:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass4 {

    @Resource
    private DicMapper dicMapper;

    @Test
    public void testMethod1() {
        for (int i = 0; i < 10; i++) {
            Dic dic = new Dic("dicName" + i, 1);
            dicMapper.insert(dic);
        }
    }

    @Test
    public void testMethod2() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dic_id", 592818985544712193L);
        Dic dic = new Dic();
        dic.setDicStatus(2);
        dicMapper.update(dic, queryWrapper);
    }
}
