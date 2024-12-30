package com.imooc.first.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("student_course")
@Data

public class StudentCourse {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long courseId;
}
