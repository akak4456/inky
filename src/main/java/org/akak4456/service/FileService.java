package org.akak4456.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String uploadFile(List<MultipartFile> uploadfile) throws IOException;
	File getFile(String path,String fileName);
	boolean deleteFile(String path,String fileName);
}
