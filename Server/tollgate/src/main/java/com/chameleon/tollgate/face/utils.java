package com.chameleon.tollgate.face;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class utils {
	public static void saveFile(MultipartFile file, String path) throws IOException {
		Path directory = Paths.get(path).toAbsolutePath().normalize();
		
		Files.createDirectories(directory);
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		Assert.state(!fileName.contains(".."), "Name of file cannot contatin '..'");
		
		Path targetPath = directory.resolve(fileName).normalize();
		
		Assert.state(!Files.exists(targetPath), fileName + " File already Exists");
		file.transferTo(targetPath);
		
	}
}
