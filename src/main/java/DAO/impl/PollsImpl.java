package DAO.impl;

import DAO.*;
import entity.*;

import javax.persistence.*;
import java.util.*;

public class PollsImpl implements DAO<Polls> {
    private final EntityManager entityManager;
    private final PollImpl pollImpl;

    public PollsImpl(EntityManager em) {
        entityManager = em;
        pollImpl =  new PollImpl(em);
    }

    public void makeNewPoll(Polls polls, String[] params){
        pollImpl.makePoll(params, polls);
    }


    @Override
    public void update(Polls polls, String[] params) {
        int currentPolls = params[0] != null ? Integer.parseInt(params[0]) : polls.getCurrentPolls();
        int futurePolls = params[1] != null ? Integer.parseInt(params[1]) : polls.getFuturePolls();
        int pastPolls = params[2] != null ? Integer.parseInt(params[2]) : polls.getPastPolls();

        polls.setCurrentPolls(currentPolls);
        polls.setFuturePolls(futurePolls);
        polls.setPastPolls(pastPolls);

        executeInsideTransaction(EntityManager2 -> entityManager.merge(polls), entityManager);
    }
    public void update(Polls polls, String[] params, User user) {
        int currentPolls = params[0] != null ? Integer.parseInt(params[0]) : polls.getCurrentPolls();
        int futurePolls = params[1] != null ? Integer.parseInt(params[1]) : polls.getFuturePolls();
        int pastPolls = params[2] != null ? Integer.parseInt(params[2]) : polls.getPastPolls();

        polls.setCurrentPolls(currentPolls);
        polls.setFuturePolls(futurePolls);
        polls.setPastPolls(pastPolls);
        polls.setUser(user);
        executeInsideTransaction(EntityManager2 -> entityManager.merge(polls), entityManager);
    }

    @Override
    public Optional<Polls> get(long id) {
        return Optional.ofNullable(entityManager.find(Polls.class, id));
    }

    @Override
    public List<Polls> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Polls e");
        return query.getResultList();
    }

    @Override
    public void save(Polls polls) {
        executeInsideTransaction(entityManager -> entityManager.persist(polls), entityManager);
    }

    @Override
    public void delete(Polls polls) {
        executeInsideTransaction(entityManager -> entityManager.remove(polls), entityManager);
    }
}
