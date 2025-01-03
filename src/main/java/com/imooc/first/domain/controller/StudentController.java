package com.imooc.first.domain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.dto.StudentCourseNumberDto;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.service.StudentCourseService;
import com.imooc.first.domain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//查询所有学生
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping("/list")
    public IPage<Student> getStudentList(@RequestParam int page,
                                         @RequestParam int pageSize,
                                         @RequestParam(required = false, defaultValue = "") String name,
                                         @RequestParam(required = false, defaultValue = "") String phoneNumber) {
        return studentService.getStudentList(page, pageSize, name, phoneNumber);
    }

    //查询某个学生的课程列表
    @GetMapping("/course/{studentId}")
    public IPage<StudentCourseDto> getStudentCourseList(@PathVariable Integer studentId,
                                                        @RequestParam Integer page,
                                                        @RequestParam Integer pageSize) {

        return studentCourseService.getCourseListByStudentId(page, pageSize, studentId);
    }

    //新增学生
    @PostMapping("/add")
    public Boolean addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PostMapping("/addCourse")
    public Boolean addStudentCourse(@RequestBody StudentCourse studentCourse) {
        return studentCourseService.addStudentCourse(studentCourse);
    }

    @GetMapping("/course/list")
    public ArrayList<StudentCourseNumberDto> getStudentCourseNumberList() {
        return studentCourseService.getStudentCourseNumber();
    }

    @GetMapping("/enrolled")
    public Integer isStudentEnrolled(@RequestParam("studentId") Integer studentId, @RequestParam("courseId") Integer courseId) {
        return studentCourseService.isStudentEnrolled(studentId, courseId);
    }

}
