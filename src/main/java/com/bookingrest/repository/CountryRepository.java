package com.bookingrest.repository;

import com.bookingrest.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Serializable> {

    Page<Country> findAll(Pageable pageable);

    Page<Country> findAllByName(String name, Pageable pageable);

    Country findById(int id);

    Country findByName(String name);
}
