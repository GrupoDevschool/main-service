package br.com.devschool.demo.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.devschool.demo.domain.service.StorageService;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private StorageService storageService;
	
	@PostMapping
	public ResponseEntity<Map<String, String>> upladoImage(@ModelAttribute MultipartFile image) {
		String url = storageService.uploadFile(image);
		Map<String, String> response = new HashMap<>();
		response.put("url", url);
		return ResponseEntity.ok(response);
	}
	
}

