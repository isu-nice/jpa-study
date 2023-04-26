package com.states.basic;

import com.states.entity.Member;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration
public class JpaBasicConfig {
    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaBasicRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();

        this.tx = em.getTransaction();

        return args -> {
//            example01();
//            example02();
//            example03();
//            example04();
            example05();
        };
    }

    private void example01() {
        Member member = new Member("hgd@gmail.com");

        em.persist(member);

        Member resultMember = em.find(Member.class, 1L);
        System.out.println("Id: " + resultMember.getMemberId()
                + ", email: " + resultMember.getEmail());
    }

    private void example02() {
        tx.begin(); // 트랜잭션 시작
        Member member = new Member("hgd@gmail.com");

        em.persist(member); // member 객체를 영속성 컨텍스트에 저장
        tx.commit(); // member 객체를 DB의 테이블에 저장

        // 영속성 컨텍스트에 저장한 member 객체를 1차 캐시에서 조회
        Member resultMember1 = em.find(Member.class, 1L);
        System.out.println("Id: " + resultMember1.getMemberId()
                + ", email: " + resultMember1.getEmail());

        // 식별자 값이 2L인 member 객체는 존재하지 않음
        Member resultMember2 = em.find(Member.class, 2L);
        System.out.println(resultMember2 == null); // 결과: true
    }

    private void example03() {
        tx.begin();

        Member member1 = new Member("hgd1@gmail.com");
        Member member2 = new Member("hgd2@gmail.com");

        // 영속성 컨텍스트에 저장
        em.persist(member1);
        em.persist(member2);

        tx.commit(); // INSERT 쿼리 모두 실행 -> 테이블에 데이터 저장
    }

    private void example04() {
        tx.begin();
        em.persist(new Member("hgd@gmail.com"));
        tx.commit(); // INSERT 쿼리 실행

        tx.begin();
        // member 객체를 '영속성 컨텍스트의 1차 캐시'에서 조회
        Member member1 = em.find(Member.class, 1L);
        member1.setEmail("hgd@yahoo.co.kr");
        tx.commit(); // UPDATE 쿼리 실행
    }

    private void example05() {
        tx.begin();
        em.persist(new Member("hgd@email.com"));
        tx.commit(); // INSERT 쿼리 실행

        tx.begin();
        Member member = em.find(Member.class, 1L);
        em.remove(member); // 영속성 컨텍스트의 1차 캐시에 있는 엔티티 제거 요청

        // 영속성 컨텍스트의 1차 캐시에 있는 엔티티를 제거,
        // 쓰기 지연 SQL 저장소에 등록된 DELETE 쿼리 실행
        tx.commit();
    }
}