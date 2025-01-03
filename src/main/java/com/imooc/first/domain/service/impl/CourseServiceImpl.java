package com.imooc.first.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.entity.Course;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.mapper.CourseMapper;
import com.imooc.first.domain.mapper.StudentCourseMapper;
import com.imooc.first.domain.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    StudentCourseMapper studentCourseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCourse(Course course) {
        if (course == null) {
            return false;
        }
        LambdaQueryWrapper<Course> courseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        courseLambdaQueryWrapper.eq(Course::getName, course.getName());
        Course existingCourse = this.getOne(courseLambdaQueryWrapper);
        if (existingCourse != null) {
            return false;
        }
        return this.save(course);
    }

    @Override
    public IPage<Course> getCourseList(int pageNum, int pageSize) {
        // 创建分页对象，传入当前页和每页记录数
        Page<Course> page = new Page<>(pageNum, pageSize);
        // 执行分页查询，返回分页结果
        return this.page(page, null);
    }

    @Override
    public IPage<Student> getStudentListByCourseId(Integer page, Integer pageSize, Integer courseId) {
        return studentCourseMapper.getStudentByCourseId(courseId, new Page<>(page, pageSize));
    }


}
