package com.example.micro_service_3.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.micro_service_3.bean.ClassInfo;
import com.example.micro_service_3.bean.Student;
import com.example.micro_service_3.feign.ClassInfoService;
import com.example.micro_service_3.service.StudentService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 30/5/2023 0030 下午 2:39
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Resource
    private StudentService studentService;
    @Resource
    private ClassInfoService classInfoService;

    @GetMapping("/all")
    public String searchAllStudent() {
        studentService.list().forEach(System.out::println);
        System.out.println(classInfoService.searchAllClassInfo());
        classInfoService.searchListClassInfo().forEach(System.out::println);
        return "OK";
    }

    @GetMapping("/page")
    public String pageStudent(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        IPage<Student> page = new Page<>(current, size);
        studentService.page(page).getRecords().forEach(System.out::println);
        return "OK";
    }


    @GetMapping("/add")
    @GlobalTransactional(name = "", rollbackFor = Exception.class)
    public List<ClassInfo> addStudentAndClassInfo() {
//        String xid = GlobalTransactionContext.getCurrentOrCreate().getXid();
//        log.info(xid);
        Student student = new Student();
        student.setName("刘彻6");
        student.setAge(15);
//        try {
        studentService.save(student);
        classInfoService.addClassInfo();
//        } catch (Exception e) {
//            GlobalTransactionContext.reload(xid).rollback();
//            log.error("走到这里回滚了");
//            throw new RuntimeException("回滚");
//        }
        return classInfoService.searchListClassInfo();
    }
}
