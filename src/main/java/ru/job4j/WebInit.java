package ru.job4j;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import ru.job4j.config.WebConfig;

public class WebInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(WebConfig.class);
        ac.refresh();
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        filter.setForceRequestEncoding(true);
        FilterRegistration.Dynamic encoding
                = servletContext.addFilter("encoding", filter);
        encoding.addMappingForUrlPatterns(null, false, "/*");
        ServletRegistration.Dynamic dispatcher
                = servletContext.addServlet("dispatcher", new DispatcherServlet(ac));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
