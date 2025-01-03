package com.imooc.first.domain.service.impl;

import com.alibaba.druid.support.logging.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.dto.CourseStudentListDto;
import com.imooc.first.domain.dto.StudentCourseDto;
import com.imooc.first.domain.dto.StudentCourseNumberDto;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.mapper.StudentCourseMapper;
import com.imooc.first.domain.service.StudentCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
    public IPage<StudentCourseDto> getCourseListByStudentId(Integer pageNum, Integer pageSize, Integer studentId) {
        log.info(studentId.toString());
        Page<StudentCourseDto> page = new Page<>(pageNum, pageSize);
        return studentCourseMapper.getCoursesByStudentId(studentId, page);
    }


    //查询每个学生报了报了多少名课程
    @Override
    public ArrayList<StudentCourseNumberDto> getStudentCourseNumber() {
        return studentCourseMapper.getStudentCourseNumber();
    }

    //判断当前学生是否报名了该课程
    @Override
    public Integer isStudentEnrolled(Integer studentId, Integer courseId) {
        LambdaQueryWrapper<StudentCourse> studentCourseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentCourseLambdaQueryWrapper.eq(StudentCourse::getStudentId, studentId).eq(StudentCourse::getCourseId, courseId);

        StudentCourse one = this.getOne(studentCourseLambdaQueryWrapper);
        if (one != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public ArrayList<CourseStudentListDto> getCourseStudentList() {
        ArrayList<CourseStudentListDto> courseStudentList = studentCourseMapper.getCourseStudentList();
        log.warn(courseStudentList.toString());
        return courseStudentList;
    }

    @Override
    public Boolean deleteStudentListByCourseId(Integer courseId) {
        LambdaQueryWrapper<StudentCourse> studentCourseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentCourseLambdaQueryWrapper.eq(StudentCourse::getCourseId, courseId);
        return this.remove(studentCourseLambdaQueryWrapper);
    }
}
