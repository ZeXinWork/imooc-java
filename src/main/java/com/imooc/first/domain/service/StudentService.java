package com.imooc.first.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;

import java.util.List;

public interface StudentService extends IService<Student> {
    IPage<Student> getStudentList(int pageNum, int pageSize, String name, String phoneNumber);


    Boolean addStudent(Student student);


}
