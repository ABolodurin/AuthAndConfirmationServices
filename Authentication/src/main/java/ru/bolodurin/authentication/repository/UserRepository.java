package ru.bolodurin.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bolodurin.authentication.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query("UPDATE User u SET u.enabled = true")
    void enable(User user);

}
