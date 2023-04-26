package com.states.entity_mapping.many_to_one_unidirection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration
public class JpaManyToOneUniDirectionConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaManyToOneRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            mappingManyToOneUniDirection();
        };
    }

    private void mappingManyToOneUniDirection() {
        tx.begin();
        Member member = new Member("hgd@gmail.com",
                "Hong Gil Dong", "010-1111-1111");
        em.persist(member);

        Order order = new Order();
        order.addMember(member);

        em.persist(order);
        tx.commit();

        Order findOrder = em.find(Order.class, 1L);

        System.out.println("findOrder: " + findOrder.getMember().getMemberId() +
                ", " + findOrder.getMember().getEmail());

    }
}