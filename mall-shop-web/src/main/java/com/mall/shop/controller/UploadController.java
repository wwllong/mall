package com.mall.shop.controller;

import com.mall.utils.FastDFSClient;
import common.pojo.Message;
import common.pojo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jack Wen
 * @className UploadController
 * @dsecription 上传文件控制层
 * @data 2019/4/20 0020
 * @vserion 1.0
 */

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${file.server.url}")
    private String FILE_SERVER_RUL;

    @RequestMapping("/fast")
    public Result upload(MultipartFile file) {
        //获得文件扩展名称
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.indexOf(".") + 1);

        try {
            //创建FastDFS客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            //上传
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);
            String url = FILE_SERVER_RUL + path;
            return Result.success(url);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.ADMIN_ERROR;
        }
    }

}
