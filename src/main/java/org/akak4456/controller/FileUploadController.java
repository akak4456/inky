package org.akak4456.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.akak4456.service.FileService;
import org.akak4456.vo.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@RestController
@Log
public class FileUploadController {
	@Autowired
	private FileService fileService;
	@PostMapping("/fileupload")
	public ResponseEntity<FileUpload> upload(@RequestParam("upload") List<MultipartFile> uploadfile) throws Exception {
		String uploadPath = fileService.uploadFile(uploadfile);
		FileUpload fileUpload = new FileUpload();
		fileUpload.setUrl(uploadPath);
		return new ResponseEntity<>(fileUpload,HttpStatus.OK);
	}
	
	@GetMapping("/fileget/{year}/{month}/{day}/{fileName}")
	public ResponseEntity<byte[]> imageRead(@PathVariable("year") String year,@PathVariable("month") String month,
			@PathVariable("day")String day,@PathVariable("fileName") String fileName){
		File file = fileService.getFile("/"+year+"/"+month+"/"+day+"/", fileName);
		log.info("file: "+file);
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
