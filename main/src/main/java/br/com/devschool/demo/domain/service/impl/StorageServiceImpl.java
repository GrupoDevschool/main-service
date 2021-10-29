package br.com.devschool.demo.domain.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.devschool.demo.domain.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService{

	@Value("${spring.application.bucket.name}")
	private String bucketName;
	
	@Autowired
	private AmazonS3 s3Client;
	
	public String uploadFile(MultipartFile file) {
		File fileObj = convertMultiPartFileToFile(file);
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj).withCannedAcl(CannedAccessControlList.PublicRead));
		fileObj.delete();
		return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
	}
	
	public Boolean deleteFile(String fileName) {
		try {
			s3Client.deleteObject(bucketName, fileName);
			return true;
		} catch (SdkClientException ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	public String getFilenameFromUrl(String url) {
		String fileName = url.split(".s3.amazonaws.com/")[1];
		return fileName;
	}
	
	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Error converting multipartfile to file", e);
		}
		return convertedFile;
	}
	
}
