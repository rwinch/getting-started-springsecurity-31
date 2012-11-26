package org.springframework.security.samples.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class, SecurityConfig.class, RepositoryConfig.class);
		context.setServletContext(servletContext);
		context.refresh();

		DispatcherServlet dispatcher = new DispatcherServlet(context);
		ServletRegistration servlet = servletContext.addServlet("message", dispatcher);
		servlet.addMapping("/");

		FilterRegistration filter = servletContext.addFilter("springSecurityFilterChain",
				context.getBean("springSecurityFilterChain", Filter.class));
		filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

	}

}
