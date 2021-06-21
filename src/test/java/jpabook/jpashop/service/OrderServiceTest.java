package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품_주문()throws Exception{


        //given
        Member member = createMember();
        Item item = createBook("JPA",10000,10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then

        Order order = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualTo(order.getStatus());
        assertThat(1).isEqualTo(order.getOrderItems().size());
        assertThat(10000*orderCount).isEqualTo(order.getTotalPrice());
        assertThat(8).isEqualTo(item.getStockQuantity());

    }

    @Test
    public void 상품주문_재고수량초과()throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("JPA",10000,10);

        int orderCount =11;

        //when




        //then
        NotEnoughStockException nt = assertThrows(NotEnoughStockException.class, () ->{
            orderService.order(member.getId(), item.getId(), orderCount);
        });
        assertEquals(nt.getMessage(),"수량이 부족합니다.");



    }

    @Test
    public void 주문취소()throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("JPA",10000,10);

        int orderCount =2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when

        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        assertThat(10).isEqualTo(item.getStockQuantity());

    }

    private Member createMember(){
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울","강남구","112-1"));
        em.persist(member);
        return member;

    }

    private Book createBook(String name,int price,int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

}