package com.imooc.first.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.entity.Course;
import com.imooc.first.domain.entity.StudentCourse;

public interface StudentCourseService extends IService<StudentCourse> {

    Boolean addStudentCourse(StudentCourse studentCourse);

    IPage<StudentCourseDto> getCourseListByStudentId(int pageNum, int pageSize, Integer studentId);
}
