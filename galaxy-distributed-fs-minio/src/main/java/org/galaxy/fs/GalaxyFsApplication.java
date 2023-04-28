package org.galaxy.fs;

import org.galaxy.fs.utils.MinioUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class GalaxyFsApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GalaxyFsApplication.class, args);

//		FileSystemResource resource = new FileSystemResource("D:\\log\\1682672643822.jpg");
//		String uploadUrl = applicationContext.getBean(MinioUtils.class).uploadObject("/hello/20230428/1682672643822.jpg", resource);
//		System.out.println("上传后的路径是" + uploadUrl);

		String uploadUrl = "/hello/20230428/1682672643822.jpg";
//		String previewUrl = applicationContext.getBean(MinioUtils.class).previewObject(uploadUrl);
//		System.out.println("预览的链接是" + previewUrl);
		System.out.println(applicationContext.getBean(MinioUtils.class).removeObject(uploadUrl));
	}

}
