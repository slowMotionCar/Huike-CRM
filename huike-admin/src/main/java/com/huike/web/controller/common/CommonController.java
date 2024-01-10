package com.huike.web.controller.common;

import com.huike.common.core.domain.AjaxResult;
import com.huike.common.result.FileDownloadAjaxResult;
import com.huike.common.utils.MessageUtils;
import com.huike.common.utils.file.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description 通用请求处理
 * @Date 2023-10-10
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    AliOssUtil aliOssUtil;

    /**
     * 通用文件下载
     * @return
     */
    @GetMapping("/download")
    public FileDownloadAjaxResult download(Boolean delete, String fileName) {

        String download = aliOssUtil.download(fileName);

        return FileDownloadAjaxResult.success(download);
    }

    /**
     * 通用文件上传
     *
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file) {
        //获取原文件名
        String filename = file.getOriginalFilename();
        //获取后缀
        assert filename != null;
        int i = filename.lastIndexOf(".");
        String lastName = filename.substring(i);
        //UUID生成文件名
        UUID uuid = UUID.randomUUID();
        //拼接
        String newFileName = uuid + lastName;
        try {
            //上传成功
            String upload = aliOssUtil.upload(file.getBytes(), newFileName);
            return AjaxResult.success(upload);
        } catch (IOException e) {
            return AjaxResult.error(MessageUtils.message("上传失败"));
        }

    }
}
