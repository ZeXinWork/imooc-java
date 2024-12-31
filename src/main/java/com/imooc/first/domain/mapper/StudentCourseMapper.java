package com.imooc.first.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    IPage<StudentCourseDto> getCoursesByStudentId(Page<StudentCourseDto> page, Integer studentId);
}
