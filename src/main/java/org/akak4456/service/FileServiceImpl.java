package org.akak4456.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;
@Service
@Log
public class FileServiceImpl implements FileService {
	private static final String pathPrefix = "C:/upload"; 
	@Override
	public String uploadFile(List<MultipartFile> uploadfile) throws IOException {
		// TODO Auto-generated method stub
		log.info("originalfileName: "+uploadfile.get(0).getOriginalFilename());
		//folder create
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy/MM/dd");
		Date time = new Date();
		String time1 = format1.format(time);
		String folderName = "C:/upload/"+time1;
		Path path = Paths.get(folderName);
		
		if(!Files.exists(path)) {
			Files.createDirectories(path);
		}
		//file create
		String fileName = null;
		for(MultipartFile multipart:uploadfile) {
			UUID uuid = UUID.randomUUID();
			fileName = uuid.toString()+"_"+multipart.getOriginalFilename();
			Path filepath = Paths.get(folderName, uuid.toString()+"_"+multipart.getOriginalFilename());
			multipart.transferTo(filepath);
		}
		return "/fileget/"+time1+"/"+fileName;
	}

	@Override
	public File getFile(String path,String fileName) {
		// TODO Auto-generated method stub
		String totalFileName = pathPrefix+path+"/"+fileName;
		log.info("totalFileName: "+totalFileName);
		File file = new File(totalFileName);
		return file;
	}

	@Override
	public boolean deleteFile(String path, String fileName) {
		// TODO Auto-generated method stub
		File file = new File(pathPrefix+path+"/"+fileName);
		if(!file.delete())
			return false;
		return true;
	}

}
