package com.techtrader.repository;

import com.techtrader.model.Device;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByPriceBetweenOrderByPrice(double min, double max, PageRequest of);

    @Query("SELECT DISTINCT d FROM Device d JOIN d.specs s " +
            "WHERE LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            " OR LOWER(d.type) LIKE LOWER(CONCAT('%', :keyword1, '%'))" +
            " OR LOWER(s) LIKE LOWER(CONCAT('%', :keyword2, '%'))")
    List<Device> findAllByNameContainingOrDeviceTypeContainingOrSpecsContaining(String keyword, String keyword1, String keyword2, PageRequest of);

    Optional<List<Device>> findAllByType(String type, PageRequest of);
}
