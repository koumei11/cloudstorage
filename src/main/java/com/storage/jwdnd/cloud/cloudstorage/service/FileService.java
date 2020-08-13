package com.storage.jwdnd.cloud.cloudstorage.service;

import com.storage.jwdnd.cloud.cloudstorage.mapper.FileMapper;
import com.storage.jwdnd.cloud.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper mMapper;

    public FileService(FileMapper mapper) {
        mMapper = mapper;
    }

    public List<File> getFiles(Integer userId) {
        return mMapper.getFiles(userId);
    }

    public File getFile(Integer fileId) {
        return mMapper.getFile(fileId);
    }

    public int insertFile(File file) {
        return mMapper.insert(file);
    }

    public int delete(Integer fileId) {
        return mMapper.delete(fileId);
    }
}
