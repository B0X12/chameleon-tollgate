package com.chameleon.tollgate.face;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class utils {
	public static void saveFile(MultipartFile file, String path) throws IOException {
//		Path directory = Paths.get(path).toAbsolutePath().normalize();
//		
//		Files.createDirectories(directory);
//		
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		
//		Assert.state(!fileName.contains(".."), "Name of file cannot contatin '..'");
//		
//		Path targetPath = directory.resolve(fileName).normalize();
//		
//		Assert.state(!Files.exists(targetPath), fileName + " File already Exists");
//		file.transferTo(targetPath);
//		
		
		FileOutputStream fos = new FileOutputStream(path+file.getOriginalFilename());
		InputStream is = file.getInputStream();
		
		int readCount = 0;
		byte[] buffer = new byte[1024*16];
		
		while((readCount = is.read(buffer)) != -1) {
			fos.write(buffer, 0, readCount);
//			System.out.println(readCount);
		}
		is.close();
		fos.close();
		System.out.println(file.getOriginalFilename()+" 파일이 저장되었습니다.");
	}
}
