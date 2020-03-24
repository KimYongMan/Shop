package com.ym.book.shop.repository;

import com.ym.book.shop.domain.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memeberName; //회원 이름
    private OrderStatus orderStatus; //ORDER, CANCEL

    public CharSequence getMemberName() {
        return memeberName;
    }
}
