package com.imooc.first.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.first.domain.entity.Course;
import com.imooc.first.domain.entity.Student;

import java.util.ArrayList;

public interface CourseService extends IService<Course> {

    Boolean addCourse(Course course);


    IPage<Course> getCourseList(int pageNum, int pageSize);

    IPage<Student> getStudentListByCourseId(Integer page, Integer pageSize,Integer courseId);


}
