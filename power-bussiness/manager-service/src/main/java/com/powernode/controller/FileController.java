package com.powernode.controller;

import com.alibaba.fastjson.JSON;
import com.powernode.config.FileConfig;
import com.powernode.model.Result;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

@RestController
@Api(tags = "文件上传oss接口")
public class FileController {
    @Autowired
    private FileConfig fileConfig;


    @PostMapping("/admin/file/upload/element")
    public Result<String> uploadFile(MultipartFile file) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = fileConfig.getAk();
        String secretKey = fileConfig.getSk();
        String bucket = fileConfig.getBucket();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = file.getBytes(); //获取文件内容
            Auth auth = Auth.create(accessKey, secretKey);
            //上传凭据
            String upToken = auth.uploadToken(bucket);

            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            String filename = fileConfig.getDomainName() + putRet.key;
            return Result.success("上传成功", filename);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail(500, "上传失败");
            //ignore
        }
    }
}


