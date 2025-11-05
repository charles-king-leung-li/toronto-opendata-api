package com.toronto.opendata.dataportal.repository;

import com.toronto.opendata.dataportal.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    List<Dataset> findByCategory(String category);
    List<Dataset> findByFormat(String format);
}