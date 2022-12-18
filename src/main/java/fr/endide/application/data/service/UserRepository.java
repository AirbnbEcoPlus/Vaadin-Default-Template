package fr.endide.application.data.service;

import java.util.UUID;

import fr.endide.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    User findByEmail(String email);
    void removeStudentByEmail(String email);
    boolean existsStudentByEmail(String email);
}

