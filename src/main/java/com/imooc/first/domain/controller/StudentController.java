package com.imooc.first.domain.controller;

import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/list")

    public List<StudentCourse> getStudentList() {
        return studentService.getStudentList();
    }


}
