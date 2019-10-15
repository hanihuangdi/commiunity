package com.example.demo.contorller;

import com.example.demo.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
   @RequestMapping("/file/upload")
   @ResponseBody
    public  FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setUrl("/images/loading@3x.gif");
        fileDTO.setSuccess(1);
        fileDTO.setMessage("上传失败");
        return fileDTO;
    }
}
