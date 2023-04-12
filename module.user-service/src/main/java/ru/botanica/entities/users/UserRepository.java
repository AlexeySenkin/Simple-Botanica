package ru.botanica.entities.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.userId from User u where u.userName like ?1")
    Long findIdByUserName(String userName);

}
