package com.rolnik.shop.respositories;

import com.rolnik.shop.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}
