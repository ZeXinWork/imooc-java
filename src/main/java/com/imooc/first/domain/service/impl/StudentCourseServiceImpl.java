package com.imooc.first.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.mapper.StudentCourseMapper;
import com.imooc.first.domain.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;
    //新增课程
    @Override
    public Boolean addStudentCourse(StudentCourse studentCourse) {
        return this.save(studentCourse);
    }


    //根据studentcourseMapper写的getCoursesByStudentId 查询
    @Override
    public IPage<StudentCourseDto> getCourseListByStudentId(int pageNum, int pageSize, Integer studentId) {
        Page<StudentCourseDto> page = new Page<>(pageNum, pageSize);
        return studentCourseMapper.getCoursesByStudentId(page, studentId);
    }
}
