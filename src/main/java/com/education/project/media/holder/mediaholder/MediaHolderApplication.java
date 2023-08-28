package com.education.project.media.holder.mediaholder;

import com.education.project.media.holder.mediaholder.tools.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MediaHolderApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaHolderApplication.class, args);
	}
}
