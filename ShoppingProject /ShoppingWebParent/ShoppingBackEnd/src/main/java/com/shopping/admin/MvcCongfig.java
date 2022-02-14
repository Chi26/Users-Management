package com.shopping.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcCongfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "user-photos";
		
		Path userPhotosDir =Paths.get(dirName);
		System.out.println(userPhotosDir);
		
		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		System.out.println(userPhotosPath);
		
		registry.addResourceHandler("/user-photos/**").addResourceLocations("file:"+userPhotosPath+"/");
		
		
	}

	
}
