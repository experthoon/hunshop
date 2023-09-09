package com.example.shop.repository;

import com.example.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>{  // <엔티티 타입 클래스, 기본키 타입>

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query(value = "select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); // @Param 파라미터로 넘어온 값을 JPQL 변수로 지정

    @Query(value = "select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true) // nativeQuery = true : 기존 쿼리를 그대로 사용
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail); // @Param 파라미터로 넘어온 값을 JPQL 변수로 지정

}
