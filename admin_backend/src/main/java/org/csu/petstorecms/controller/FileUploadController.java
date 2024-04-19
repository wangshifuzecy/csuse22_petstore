package org.csu.petstorecms.controller;

import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/file/product")
    public CommonResponse<String> uploadProduct(MultipartFile file) throws Exception{
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "." + getFileExtension(file);
        String url = AliOssUtil.uploadFile("product", filename, file.getInputStream());
        if(url != null && !url.isEmpty()){
            return CommonResponse.createForSuccess(url);
        }
        return CommonResponse.createForFailure("fail to upload");
    }

    public static String getFileExtension(MultipartFile file) {
        // Get the original file name
        String originalFilename = file.getOriginalFilename();

        // Check if the file name is not null and contains a dot
        if (originalFilename != null && originalFilename.contains(".")) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < originalFilename.length() - 1) {
                // Extract the file extension by substring after the last dot
                return originalFilename.substring(lastDotIndex + 1);
            }
        }
        // If the file name does not contain a dot or is null, return an empty string
        return "";
    }
}
