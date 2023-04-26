package com.states.entity_mapping.single_mapping.column;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration
public class JpaColumnMappingConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaSingleMappingRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
//            testEmailNotNull();
//            testEmailUpdatable();
//            testEmailUnique();
        };
    }

    // nullable=false 이므로 에러 발생
    private void testEmailNotNull() {
        tx.begin();
        em.persist(new Member());
        tx.commit();
    }

    // updatable=false 이므로 이미 등록한 email이 수정되지 않아야 함
    private void testEmailUpdatable() {
        tx.begin();
        em.persist(new Member("hgd@gmail.com"));
        Member member = em.find(Member.class, 1L);
        member.setEmail("hge@yahoo.co.kr");
        tx.commit();
    }

    // unique=true 이므로 이미 등록된 주소가 한 번 더 등록될 수 없음
    private void testEmailUnique() {
        tx.begin();
        em.persist(new Member("hgd@gmai.com"));
        em.persist(new Member("hgd@gmai.com"));
        tx.commit();
    }
}