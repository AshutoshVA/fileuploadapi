package com.demo.fileupload.controller;

import com.demo.fileupload.payload.FileResponse;
import com.demo.fileupload.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileupload(@RequestParam("image") MultipartFile image) {
        String fileName = null;
        try {
            fileName = this.fileService.upoladImage(path, image);
        } catch (IOException e) {
            //repository call
            //file name save
            e.printStackTrace();
            new ResponseEntity<>(new FileResponse(null, "Image upload failed !!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new FileResponse(fileName, "Image uploaded successfully"), HttpStatus.OK);
    }

    //method to serve files

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
