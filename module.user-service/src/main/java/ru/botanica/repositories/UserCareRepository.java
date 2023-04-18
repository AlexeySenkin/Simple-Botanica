package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.UserCare;

@Repository
public interface UserCareRepository extends JpaRepository<UserCare, Long>, JpaSpecificationExecutor<UserCare> {

}
