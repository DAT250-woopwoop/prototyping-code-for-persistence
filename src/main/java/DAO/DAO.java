package DAO;

import entity.*;

import javax.persistence.*;
import java.util.*;
import java.util.function.*;

public interface DAO<T> {

    void update(T t, String[] params);

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void delete(T t);

    default void executeInsideTransaction(Consumer<EntityManager> action, EntityManager entityManager) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
