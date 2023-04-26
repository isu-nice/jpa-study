package com.states.entity_mapping.many_to_one_bidirection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

// 일대다 연관관꼐 매핑을 추가하여 주문 정보를 조회하는 예시
@Configuration
public class JpaManyToOneBiDirectionConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaManyToOneRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            mappingManyToOneBiDirection();
        };
    }

    private void mappingManyToOneBiDirection() {
        tx.begin();
        Member member = new Member("hgd@gmail.com", "Hong Gil Dong",
                "010-1111-1111");
        Order order = new Order();

        member.addOrder(order);
        order.addMember(member);

        em.persist(member);
        em.persist(order);

        tx.commit();

        Member findMember = em.find(Member.class, 1L);

        // 일대다 양방향 관계를 매핑했기 때문에 find() 메서드로 조회한 member로부터
        // 객체 그래프 탐색을 통해 List 정보에 접근할 수 있음
        findMember.getOrders()
                .stream()
                .forEach(findOrder -> {
                    System.out.println("findOrder: " +
                            findOrder.getOrderId() + ", " +
                            findOrder.getOrderStatus());
                });

    }
}