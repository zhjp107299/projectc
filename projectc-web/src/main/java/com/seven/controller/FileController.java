package com.seven.controller;


import com.alibaba.fastjson.JSONArray;
import com.seven.utils.InfoMsg;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.UUID;

@RestController
@CrossOrigin
@ResponseBody
public class FileController {


    /**
     * 处理文件上传
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String fileUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println(file);
        String originalFilename = file.getOriginalFilename();   //获取文件名
        if ("".equals(originalFilename)) {
            System.out.println("上传文件名不能为空");
            InfoMsg infoMsg = new InfoMsg();
            infoMsg.setCode(400);
            infoMsg.setMsg("上传文件名不能为空");
            return JSONArray.toJSONString(infoMsg);
        }

        System.out.println("上传的文件名 ：" + originalFilename);

        String uploadPath = request.getServletContext().getRealPath("/upload");     //获取上传路径

        File uploadFile = new File(uploadPath);     //对文件进行判空，若没有则创建
        if (!uploadFile.exists()) {
            uploadFile.mkdir();
        }
        System.out.println("上传文件的路径 ：" + uploadPath);

        //上传文件 ：获取流输出流
        InputStream in = file.getInputStream();
        UUID uuid = UUID.randomUUID();      //使用UUID编辑文件名
        originalFilename = uuid+originalFilename;
        FileOutputStream fos = new FileOutputStream(new File(uploadFile, originalFilename));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
            fos.flush();
        }
        //释放流
        fos.close();
        in.close();
        System.out.println("上传成功");
        //判断参数选择重定向到哪个题库进行导入
        String uploadFilePath = uploadPath + "\\" + originalFilename;
        com.seven.utils.ResponseBody<String> stringResponseBody = new com.seven.utils.ResponseBody<>();
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        stringResponseBody.setData(uploadFilePath);
        stringResponseBody.setInfo(infoMsg);
        return JSONArray.toJSONString(stringResponseBody);
    }

    /**
     * 处理文件下载
     */
    @RequestMapping("/135799download")
    public void fileDownload(@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileType = "";

        switch(type){       //判断下载文件类型
            case "indepropo":
                fileType = "自主命题表";
                break;
            case "student":
                fileType = "学生表";
                break;
            case "teacher":
                fileType = "教师表";
                break;
            case "choice":
                fileType = "选择题库表";
                break;
            case "gapfilling":
                fileType = "填空题库表";
                break;
            case "judge":
                fileType = "判断题库表";
                break;
            case "programme":
                fileType = "编程填空题库表";
                break;
        }
        //1.获取文件路径（D盘项目中，若是放到服务器上需要修改代码）
        String downloadPath = "D:\\IDEA\\projectc\\excel";
        String downloadFilePath = downloadPath + "\\" + fileType + ".xlsx";
        String fileName = new File(downloadFilePath).getName();
        System.out.println("下载文件的路径 ：" + downloadFilePath);



        ClassPathResource classPathResource = new ClassPathResource(downloadPath);  //获取路径
        String userAgent = request.getHeader("User-Agent");     //获取头部信息
        String decodePath = URLDecoder.decode(downloadFilePath, "UTF-8");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")){       //处理IE浏览器
            fileName = URLEncoder.encode(fileName,"UTF-8");
        }else {
            fileName = new String((fileName).getBytes(),"ISO-8859-1");
        }
        response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disPosition","attachment;fileName = " + fileName);
        response.setHeader("fileName",fileName);
        response.setContentLength((int)new File(downloadFilePath).length());

        FileInputStream fis = new FileInputStream(downloadFilePath);


        int len;
        byte[] bytes = new byte[1024];

        ServletOutputStream sos = response.getOutputStream();

        while((len = fis.read(bytes)) > 0){
            sos.write(bytes,0,len);

        }

        //8.释放资源
        sos.close();
        fis.close();

    }

}


