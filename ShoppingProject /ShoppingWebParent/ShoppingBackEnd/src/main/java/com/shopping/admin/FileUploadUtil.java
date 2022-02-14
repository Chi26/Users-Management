package com.shopping.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void saveFile(String uploadDir,String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Path createDirectories = Files.createDirectories(uploadPath);
			System.out.println(createDirectories);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			System.out.println(filePath);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("Could  not save the file");
		}
	}
	
	
}
