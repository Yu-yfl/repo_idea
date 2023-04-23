package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage( PromotionAdVO promotionAdVO){

        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdVO);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告分页查询成功", pageInfo);
        return responseResult;

    }


    /**
     * 图片上传
     *
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()){
            throw  new RuntimeException();
        }

        String realPath = request.getServletContext().getRealPath("/");
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //获取原文件名
        String originalFilename = file.getOriginalFilename();
        //生成新文件名称
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //文件上传
        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, newFileName);
        //如果目录不存在就创建目录
        if(!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录: " + filePath);
        }
        //真正上传文件
        file.transferTo(filePath);
        //将文件名和文件路径返回
        Map<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://localhost:8080/upload/"+newFileName);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", map);
        return responseResult;
    }

    /**
     * 广告动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(Integer id,Integer status){

        promotionAdService.updatePromotionAdStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告动态上下线成功", null);
        return responseResult;

    }


}
