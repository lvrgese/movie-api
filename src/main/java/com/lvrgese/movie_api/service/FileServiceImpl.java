package com.lvrgese.movie_api.service;

import com.lvrgese.movie_api.exceptions.EmptyFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        if(file.isEmpty()){
            throw new EmptyFileException("FIle is empty");
        }
        String fileName=file.getOriginalFilename();
        String filePath=path + File.separator + fileName;

        File fileInstance=new File(path);
        if(!fileInstance.exists()) {
            Boolean res = fileInstance.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {

        String filePath=path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
