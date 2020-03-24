package com.ym.book.shop.service;

import com.ym.book.shop.domain.entity.Delivery;
import com.ym.book.shop.domain.entity.Member;
import com.ym.book.shop.domain.entity.Order;
import com.ym.book.shop.domain.entity.OrderItem;
import com.ym.book.shop.domain.entity.item.Item;
import com.ym.book.shop.repository.ItemRepository;
import com.ym.book.shop.repository.MemberRepository;
import com.ym.book.shop.repository.OrderRepository;
import com.ym.book.shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //todo 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔터티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장, cascade로 인해 itemOrder, delivery 모두  save가 적용
        orderRepository.save(order);

        return order.getId();

    }

    //todo 취소
    @Transactional
    public  void cancelOrder(Long orderId){
        //주문 엔터티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소, order에 관련된 객체의 상태 변화가 일어나면 알아서 update문이 호출되어서 상태를 변경함
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

    //todo 검색

}
