package com.sentriot.proto;

import java.io.File;
import java.net.URI;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebdavApplication {


	@Value("${document.root}")
    private String documentRoot;

	static String uri;
    public static void main(final String[] args)
    {
    	String property = System.getProperty("webdav.root", System.getProperty("user.home"));
    	System.out.println("SRIDHAR WebdavApplication.main() property = " + property);
		uri = Paths.get("",new String[]{ property}).toAbsolutePath().toString();
    	System.out.println("SRIDHAR WebdavApplication.main() path = " + uri);
        SpringApplication.run(WebdavApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer()
    {
        final TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		File file = Paths.get("",new String[]{uri}).toFile();
        System.out.println("SRIDHAR WebdavApplication.servletContainer() docroot = " + file.getAbsolutePath());
		factory.setDocumentRoot(file);
        return factory;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        return new ServletRegistrationBean(new WebdavServlet(), "/webdav/*");
    }
}
