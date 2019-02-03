package com.epam.orderprocessing;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.List;

@Stateless
public class OrderBean {

    @PersistenceContext(unitName = "order-unit")
    private EntityManager entityManager;

    public Order find(Long id) {
        return entityManager.find(Order.class, id);
    }

    public void addOrder(Order order) {
        entityManager.persist(order);
    }

    public void editOrder(Order order) {
        entityManager.merge(order);
    }

    public void deleteOrder(Order order) {
        entityManager.remove(order);
    }

    public void deleteOrderId(long id) {
        Order order = entityManager.find(Order.class, id);
        deleteOrder(order);
    }

    public List<Order> getOrders() {
        CriteriaQuery<Order> cq = entityManager.getCriteriaBuilder().createQuery(Order.class);
        cq.select(cq.from(Order.class));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<Order> getAll(int firstResult, int maxResults) {
        CriteriaQuery<Order> cq = entityManager.getCriteriaBuilder().createQuery(Order.class);
        cq.select(cq.from(Order.class));
        TypedQuery<Order> q = entityManager.createQuery(cq);
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
        return q.getResultList();
    }

    public int countAll() {
        CriteriaQuery<Long> cq = entityManager.getCriteriaBuilder().createQuery(Long.class);
        Root<Order> rt = cq.from(Order.class);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        TypedQuery<Long> q = entityManager.createQuery(cq);
        return (q.getSingleResult()).intValue();
    }

    public int count(String field, String searchTerm) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<Order> root = cq.from(Order.class);
        EntityType<Order> type = entityManager.getMetamodel().entity(Order.class);

        Path<String> path = root.get(type.getDeclaredSingularAttribute(field, String.class));
        Predicate condition = qb.like(path, "%" + searchTerm + "%");

        cq.select(qb.count(root));
        cq.where(condition);

        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public List<Order> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = qb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        EntityType<Order> type = entityManager.getMetamodel().entity(Order.class);

        Path<String> path = root.get(type.getDeclaredSingularAttribute(field, String.class));
        Predicate condition = qb.like(path, "%" + searchTerm + "%");

        cq.where(condition);
        TypedQuery<Order> q = entityManager.createQuery(cq);
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
        return q.getResultList();
    }

    public void clean() {
        entityManager.createQuery("DELETE FROM Order").executeUpdate();
    }
}