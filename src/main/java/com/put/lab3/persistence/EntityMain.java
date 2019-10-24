package com.put.lab3.persistence;

import com.put.lab3.persistence.entities.CoffeesEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class EntityMain {

    private static EntityManagerFactory emf;
    private static EntityManager entityManager;

    private static void init() {
        emf = Persistence.createEntityManagerFactory("persistence");
        entityManager = getEntityManager();
    }

    private static void getAllCoffee() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CoffeesEntity> cq = cb.createQuery(CoffeesEntity.class);
        Root<CoffeesEntity> rootEntry = cq.from(CoffeesEntity.class);
        CriteriaQuery<CoffeesEntity> all = cq.select(rootEntry);

        TypedQuery<CoffeesEntity> allQuery = entityManager.createQuery(all);
        List<CoffeesEntity> list = allQuery.getResultList();

        for (CoffeesEntity elem : list) {
            System.out.println(elem);
        }
    }

    private static CoffeesEntity getCoffee(String cofName, int supId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CoffeesEntity> cq = cb.createQuery(CoffeesEntity.class);
        Root<CoffeesEntity> root = cq.from(CoffeesEntity.class);

        CriteriaQuery<CoffeesEntity> one = cq.select(root)
                .where(cb.equal(root.get("cofName"), cofName), cb.equal(root.get("supId"), supId));

        TypedQuery<CoffeesEntity> query = entityManager.createQuery(one);

        return query.getSingleResult();
    }

    private static void addCoffee(CoffeesEntity coffeesEntity) {
        entityManager.getTransaction().begin();
        entityManager.persist(coffeesEntity);
        entityManager.getTransaction().commit();
    }

    private static void removeCoffee(String cofName, int supId) {
        CoffeesEntity coffeesEntity = getCoffee(cofName, supId);
        entityManager.getTransaction().begin();
        entityManager.remove(coffeesEntity);
        entityManager.getTransaction().commit();
    }

    public static void main(String[] args) {
        CoffeesEntity coffee = CoffeesEntity.builder()
                .cofName("bla")
                .supId(49)
                .price(BigDecimal.valueOf(100))
                .sales(100)
                .total(100)
                .build();

        getAllCoffee();
        addCoffee(coffee);
        System.out.println();
        getAllCoffee();
        removeCoffee(coffee.getCofName(), coffee.getSupId());
        System.out.println();
        getAllCoffee();

    }

    private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
