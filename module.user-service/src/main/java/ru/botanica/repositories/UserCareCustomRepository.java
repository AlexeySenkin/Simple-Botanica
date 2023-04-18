package ru.botanica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.botanica.entities.UserCareCustom;
@Repository
public interface UserCareCustomRepository extends JpaRepository<UserCareCustom, Long>, JpaSpecificationExecutor<UserCareCustom> {
}
