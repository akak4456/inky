package org.akak4456.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadFile(List<MultipartFile> uploadfile) throws IOException;

	public File getFile(String path, String fileName) throws IOException;

	public boolean deleteFile(String path, String fileName) throws IOException;

	public String profileUpload(MultipartFile profile) throws IOException;
}