package br.com.devschool.demo.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	public String uploadFile(MultipartFile file);
	
	public Boolean deleteFile(String fileName);
	
	public String getFilenameFromUrl(String url);
	
}
