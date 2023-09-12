package com.example.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 장바구니 엔티티가 회원 엔티티를 참조하는 일대일 단방향 매핑입니다.
    @JoinColumn(name = "member_id")
    private Member member;

}