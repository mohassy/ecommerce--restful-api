package com.techtrader.repository;

import com.techtrader.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findByTraderIdAndDeviceId(Long id, Long id1);

    Optional<List<Listing>> findAllByTraderUsername(String name);

    Optional<Listing> findByDeviceId(Long id);
}
