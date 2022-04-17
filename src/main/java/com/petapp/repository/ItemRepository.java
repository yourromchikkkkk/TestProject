package com.petapp.repository;

import com.petapp.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();

    Optional<Item> findItemById(long id);

    @Query("select i from Item i where i.price = (select min(i.price) from Item i where i.itemName like %:name%)")
    Item findItemsByItemNameWithMinPrice(@Param("name")String name);

}
