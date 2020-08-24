package com.springboot.springbootimg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

/**
 * @PackageName:com.springboot.springbootimg.controller
 * @ClassName:uploadController
 * @Description:
 * @author:Starry the Night
 * @Date:2020/8/24 8:48
 */
@Controller
public class uploadController {


    /**
     * 上传地址
     * */
    @Value("${file.upload.path}")
    private String filePath;

    /**
     * 跳转到图片上传界面
     * @return
     */
   @GetMapping("/index")
   public String upload(){
        return "index";
    }

    /**
     * 图片上传
     * @param file
     * @param model
     * @return
     */
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {

        //获取上传图片的名字
        String filename = file.getOriginalFilename();

        //上传文件为空时
        if (file.isEmpty()) {
            model.addAttribute("msg","上传失败，请选择文件");
        }


        try {

            //文件保存到哪里
            File dest = new File(filePath+filename);
            //若没有保存目录filePath，则创建
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            //保存
            file.transferTo(dest);

            //上传图片查看链接
            model.addAttribute("url","http://localhost:8080/images/"+filename);

            model.addAttribute("click","点击查看已上传图片");
        }catch (Exception e){
            model.addAttribute("msg","上传失败");
        }


        return "index";
    }

}
