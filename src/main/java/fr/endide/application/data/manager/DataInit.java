package fr.endide.application.data.manager;

import fr.endide.application.data.entity.User;
import fr.endide.application.data.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements ApplicationRunner {
    UserRepository repository;
    @Autowired
    public DataInit(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setFirstName("FirstAdmin");
            user.setLastName("LastAdmin");
            user.setEmail("admin@application.com");
            user.setHashedPassword("$2y$10$qeyhQN4e6CXTzYcukoXhdepqjz8JduABkNMkS5d0BNdYgIrV8pdMy");
            user.setRoles("ADMIN");
            repository.save(user);
        }
    }
}
