package ru.totsystems.stocks_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.totsystems.stocks_api.model.Security;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    @Query(value = "from Security  where history.secId = ?1")
    Optional<Security> findBySecId(String secId);
}
