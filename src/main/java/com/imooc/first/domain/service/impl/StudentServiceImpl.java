package com.imooc.first.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.mapper.StudentCourseMapper;
import com.imooc.first.domain.mapper.StudentMapper;
import com.imooc.first.domain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentCourseMapper studentCourseMapper;





    // 分页查询学生信息
    public Page<Student> getStudentPage(int pageNum, int pageSize, String name, Integer age) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        if (age != null) {
            queryWrapper.eq("age", age);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }


    @Override
    public List<StudentCourse> getStudentList() {
        return  studentCourseMapper.selectList(null);
    }
}
