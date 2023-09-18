package com.example.shop.entity;

import com.example.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 한 명의 회원은 여러 번 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일 단방향 매핑

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    // 주인 설정 , cascade = CascadeType.ALL : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이
    private List<OrderItem> orderItems = new ArrayList<>();

   public void addOrderItem(OrderItem orderItem){
       orderItems.add(orderItem);
       orderItem.setOrder(this); // 양방향 참조 관계 이므로, orderItem 객체에도 order 객체를 세팅
   }

   public static Order createOrder(Member member, List<OrderItem> orderItemList){
       Order order = new Order();
       order.setMember(member);
       for (OrderItem orderItem : orderItemList){
           order.addOrderItem(orderItem); // 여러 개의 주문 상품을 담을 수 있도록 리스트형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를 추가합니다.
       }
       order.setOrderStatus(OrderStatus.ORDER);
       order.setOrderDate(LocalDateTime.now());
       return order;
   }

   //총 주문 금액을 구하는 메소드
    public int getTotalPrice(){
       int totalPrice = 0;
       for(OrderItem orderItem : orderItems){
           totalPrice += orderItem.getTotalPrice();
       }
       return totalPrice;
    }

    public void cancelOrder(){
       this.orderStatus = OrderStatus.CANCEL;

       for(OrderItem orderItem : orderItems){
           orderItem.cancel();
       }
    }
}
