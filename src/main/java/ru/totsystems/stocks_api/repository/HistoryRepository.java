package ru.totsystems.stocks_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.totsystems.stocks_api.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
