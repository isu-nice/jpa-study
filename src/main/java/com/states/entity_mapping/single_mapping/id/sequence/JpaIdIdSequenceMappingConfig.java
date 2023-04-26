package com.states.entity_mapping.single_mapping.id.sequence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration
public class JpaIdIdSequenceMappingConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaSingleMappingRunner(EntityManagerFactory emFactory){
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
            tx.begin();
            em.persist(new Member());  // 기본키 값 할당X
            Member member = em.find(Member.class, 1L);
            System.out.println("# memberId: " + member.getMemberId());
            tx.commit();

        };
    }
}