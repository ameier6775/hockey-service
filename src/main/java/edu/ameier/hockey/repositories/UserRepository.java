package edu.ameier.hockey.repositories;

import edu.ameier.hockey.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String username);
}
