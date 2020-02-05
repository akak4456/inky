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

import org.akak4456.vo.FileUpload;
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
	@PostMapping("/fileupload")
	public ResponseEntity<FileUpload> upload(@RequestParam("upload") List<MultipartFile> uploadfile) throws Exception {
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
		FileUpload fileUpload = new FileUpload();
		fileUpload.setUrl("/fileget/"+time1+"/"+fileName);
		return new ResponseEntity<>(fileUpload,HttpStatus.OK);
	}
	
	@GetMapping("/fileget/{year}/{month}/{day}/{fileName}")
	public ResponseEntity<byte[]> imageRead(@PathVariable("year") String year,@PathVariable("month") String month,
			@PathVariable("day")String day,@PathVariable("fileName") String fileName){
		String totalFileName = "C:/upload/"+year+"/"+month+"/"+day+"/"+fileName;
		log.info("totalFileName: "+totalFileName);
		File file = new File(totalFileName);
		
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
