package org.akak4456.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Service
@Log
public class FileServiceImpl implements FileService {
	private static final String pathPrefix = "C:/upload";

	private static final int thumbnailWidth = 100;

	@Override
	public String uploadFile(List<MultipartFile> uploadfile) throws IOException {
		// TODO Auto-generated method stub
		// folder create
		String time1 = createFolder();
		String folderName = pathPrefix + "/" + time1;
		// file create
		String fileName = null;
		File tofile = null;
		try {
			for (MultipartFile multipart : uploadfile) {
				UUID uuid = UUID.randomUUID();
				fileName = uuid.toString() + "_" + multipart.getOriginalFilename();
				String totalFileName = folderName + "/" + fileName;
				tofile = new File(totalFileName);
				multipart.transferTo(tofile);
			}
		} catch (IOException e) {
			throw e;
		}
		return "/fileget/" + time1 + "/" + fileName;
	}

	@Override
	public File getFile(String path, String fileName) throws IOException {
		// TODO Auto-generated method stub
		File file = null;
		String totalFileName = pathPrefix + path + "/" + fileName;
		file = new File(totalFileName);
		return file;
	}

	@Override
	public boolean deleteFile(String path, String fileName) throws IOException {
		// TODO Auto-generated method stub
		File file = null;
		file = new File(pathPrefix + path + "/" + fileName);
		if (!file.delete())
			return false;
		return true;
	}

	@Override
	public String profileUpload(MultipartFile profile) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fos = null;
		ByteArrayOutputStream baos = null;
		String time1 = null;
		String fileName = null;
		try {
			time1 = createFolder();
			UUID uuid = UUID.randomUUID();
			fileName = uuid.toString() + "_" + profile.getOriginalFilename();
			fos = new FileOutputStream(new File(pathPrefix + "/" + time1 + "/" + fileName));

			baos = createThumbnail(profile, thumbnailWidth);

			baos.writeTo(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "/fileget/" + time1 + "/" + fileName;
	}

	private String createFolder() throws IOException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		Date time = new Date();
		String time1 = format1.format(time);
		String folderName = pathPrefix + "/" + time1;
		Path path = Paths.get(folderName);

		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		return time1;
	}

	private ByteArrayOutputStream createThumbnail(MultipartFile orginalFile, Integer width) throws IOException {
		ByteArrayOutputStream thumbOutput = null;
		BufferedImage thumbImg = null;
		BufferedImage img = null;
		try {
			thumbOutput = new ByteArrayOutputStream();
			img = ImageIO.read(orginalFile.getInputStream());
			thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, orginalFile.getContentType().split("/")[1], thumbOutput);
		} catch (IOException e) {
			throw e;
		} finally {
		}
		return thumbOutput;
	}
}
