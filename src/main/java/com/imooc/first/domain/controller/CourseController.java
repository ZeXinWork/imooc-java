package com.imooc.first.domain.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.first.domain.dto.CourseStudentListDto;
import com.imooc.first.domain.entity.Course;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.service.CourseService;
import com.imooc.first.domain.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Resource
    StudentCourseService studentCourseService;

    @GetMapping("/list")
    public IPage<Course> getList(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return courseService.getCourseList(page, pageSize);
    }

    @PostMapping("/add")
    public Boolean addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/student")
    public IPage<Student> getStudentByCourseId(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("courseId") Integer courseId) {
        return courseService.getStudentListByCourseId(page, pageSize, courseId);
    }

    @GetMapping("/student/list")
    public ArrayList<CourseStudentListDto> getCourseStudent(
    ) {
        return studentCourseService.getCourseStudentList();
    }


    @DeleteMapping("/remove/{courseId}")
    Boolean removeCourse(@PathVariable("courseId") Integer courseId) {
        return studentCourseService.deleteStudentListByCourseId(courseId);
    }

}
