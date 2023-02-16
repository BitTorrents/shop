package com.shop.entity;

import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격

    private int count; //수량

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    public static OrderItem createOrderItem(Item item, int count) throws OutOfStockException {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문할 상품 세팅
        orderItem.setCount(count); // 주문 수량 세팅
        orderItem.setOrderPrice(item.getPrice()); // 현재 시간 기준으로 상품 가격을 주문 가격으로 세팅
        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice*count;
    }

    public void cancel() {
        this.getItem().addStock(count); // 주문 취소시 주문 수량만큼 상품의 재고를 더해줍니다.
    }
}
