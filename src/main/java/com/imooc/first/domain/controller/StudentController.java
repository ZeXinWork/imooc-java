package com.imooc.first.domain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//查询所有学生
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/list")
    public IPage<Student> getStudentList(@RequestParam int page,
                                         @RequestParam int pageSize,
                                         @RequestParam String name,
                                         @RequestParam String phoneNumber) {
        return studentService.getStudentList(page,pageSize,name,phoneNumber);
    }

    //查询某个学生的课程列表



}
