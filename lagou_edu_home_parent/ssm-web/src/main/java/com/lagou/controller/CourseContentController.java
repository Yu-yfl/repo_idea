package com.lagou.controller;


import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseConentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseConentService courseConentService;

    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId){
        List<CourseSection> list = courseConentService.findSectionAndLessonByCourseId(courseId);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成 功", list);
        return responseResult;
    }

    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){
        Course course = courseConentService.findCourseByCourseId(courseId);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询课程信息成功", course);
        return responseResult;
    }

    /**
     * 新增章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){

        if(courseSection.getId() == null){
            courseConentService.saveSection(courseSection);
            return new ResponseResult(true,200,"新章节添加成功",null);
        }else {
            courseConentService.updateSection(courseSection);
            return new ResponseResult(true,200,"章节更新成功",null);
        }

    }

    /**
     * 修改章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateCourseStatus(int id,int status){
        courseConentService.updateSectionStatus(id,status);

        //封装map数据
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status",status);

        ResponseResult responseResult = new ResponseResult(true, 200, "章节状态修改成功", map);
        return responseResult;
    }

}
