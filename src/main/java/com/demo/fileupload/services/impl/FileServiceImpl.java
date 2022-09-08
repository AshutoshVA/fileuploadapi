package com.demo.fileupload.services.impl;

import com.demo.fileupload.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upoladImage(String path, MultipartFile file) throws IOException {

        //File Name
        String name = file.getOriginalFilename();
        //e.g abc.png

        //random name generation of file

        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filepath = path + File.separator + fileName1;


        //create folder if not created
        File f = new File(path);

        if (!f.exists()) {
            f.mkdir();

        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filepath));

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
