package com.lhjitem.community.controller;

import com.lhjitem.community.dto.FileDTO;
import com.lhjitem.community.provider.AliCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lhj
 * @create 2022/3/4 14:17
 */
@Controller
public class UploadController {

    @Autowired
    private AliCloudProvider aliCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        String url = null;
        try {
            url = aliCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(url);
        return fileDTO;
    }
}
