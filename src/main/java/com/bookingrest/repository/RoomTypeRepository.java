package com.bookingrest.repository;

import com.bookingrest.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends PagingAndSortingRepository<RoomType, Integer> {

    Page<RoomType> findAll(Pageable pageable);

    Page<RoomType> findAllByNameContains(String name, Pageable pageable);

    RoomType findById(int id);

    RoomType findByName(String name);
}
