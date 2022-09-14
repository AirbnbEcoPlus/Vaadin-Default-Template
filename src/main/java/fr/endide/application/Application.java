package fr.endide.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import fr.endide.application.data.service.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Theme(value = "schoolcompanion")
@PWA(name = "SchoolCompanion", shortName = "SchoolCompanion", offlineResources = {})
@Push
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {
    @Autowired
    private StudentRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
