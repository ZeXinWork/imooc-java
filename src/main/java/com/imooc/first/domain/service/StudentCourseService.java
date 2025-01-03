package com.imooc.first.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.first.domain.dto.CourseStudentListDto;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.dto.StudentCourseNumberDto;
import com.imooc.first.domain.entity.Course;
import com.imooc.first.domain.entity.StudentCourse;

import java.util.ArrayList;

public interface StudentCourseService extends IService<StudentCourse> {

    Boolean addStudentCourse(StudentCourse studentCourse);

    IPage<StudentCourseDto> getCourseListByStudentId(Integer pageNumber, Integer pageSize, Integer studentId);

    ArrayList<StudentCourseNumberDto> getStudentCourseNumber();

    Integer isStudentEnrolled(Integer studentId, Integer courseId);

    ArrayList<CourseStudentListDto> getCourseStudentList();

    Boolean deleteStudentListByCourseId(Integer courseId);
}
