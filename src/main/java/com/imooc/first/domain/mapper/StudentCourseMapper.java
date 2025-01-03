package com.imooc.first.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.first.domain.dto.CourseStudentListDto;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.dto.StudentCourseNumberDto;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    IPage<StudentCourseDto> getCoursesByStudentId(@Param("studentId") Integer studentId,
                                                  @Param("page") Page<StudentCourseDto> page);

    IPage<Student> getStudentByCourseId(@Param("courseId") Integer courseId, @Param("page") Page<Student> page);

    ArrayList<StudentCourseNumberDto> getStudentCourseNumber();

    ArrayList<CourseStudentListDto> getCourseStudentList();
}
