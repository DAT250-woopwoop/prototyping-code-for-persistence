package DAO.impl;

import DAO.*;

import entity.*;

import javax.persistence.*;
import java.util.*;

public class PollImpl implements DAO<Poll> {
    private final EntityManager entityManager;

    public PollImpl(EntityManager em){
        entityManager = em;
    }


    @Override
    public void update(Poll poll, String[] params){
        boolean closed = params[0] != null ? Boolean.parseBoolean(params[0]) : poll.isClosed();
        String endTime = params[1] != null ? params[1] : poll.getEndTime();
        int noOption = params[2] != null ? Integer.parseInt(params[2]) : poll.getNoOption();
        String pollDesc = params[3] != null ? params[3] : poll.getPollDesc();
        String pollName = params[4] != null ? params[4] : poll.getPollName();
        boolean privatePoll = params[5] != null ? Boolean.parseBoolean(params[5]) : poll.isPrivatePoll();
        String startTime= params[6] != null ? params[6] : poll.getStartTime();
        String timeLimit = params[7] != null ? params[7]: poll.getTimeLimit();
        int yesOption = params[8] != null ? Integer.parseInt(params[8]): poll.getYesOption();

        poll.setClosed(closed);
        poll.setEndTime(endTime);
        poll.setNoOption(noOption);
        poll.setPollDesc(pollDesc);
        poll.setPollName(pollName);
        poll.setPrivatePoll(privatePoll);
        poll.setStartTime(startTime);
        poll.setTimeLimit(timeLimit);
        poll.setYesOption(yesOption);
        executeInsideTransaction(entityManager -> entityManager.merge(poll), entityManager);
    }

    @Override
    public Optional<Poll> get(long id) {
        return Optional.ofNullable(entityManager.find(Poll.class, id));
    }

    @Override
    public List<Poll> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Poll e");
        return query.getResultList();
    }

    @Override
    public void save(Poll poll) {
        executeInsideTransaction(entityManager -> entityManager.persist(poll), entityManager);
    }

    @Override
    public void delete(Poll poll) {
       executeInsideTransaction(entityManager -> entityManager.remove(poll), entityManager);
    }

    public void makePoll(String[] params, User user){
        Poll poll = new Poll();
        boolean closed = params[0] != null ? Boolean.parseBoolean(params[0]) : poll.isClosed();
        String endTime =  params[1] != null ? params[1] : poll.getEndTime();
        String pollDesc = params[2] != null ? params[2] : poll.getPollDesc();
        String pollName = params[3] != null ? params[3] : poll.getPollName();
        boolean privatePoll = params[4] != null ? Boolean.parseBoolean(params[4]) : poll.isPrivatePoll();
        String startTime = params[5] != null ? params[5] : poll.getStartTime();
        String timeLimit =  params[6] != null ? params[6] : poll.getTimeLimit();

        poll.setUser(user);
        String[] newParams = {String.valueOf(closed), endTime, null, pollDesc, pollName, String.valueOf(privatePoll), startTime, timeLimit, null};
        update(poll, newParams);
    }

    public void closePoll(Poll poll) {
        String[] newParams = {"True", null, null, null, null, null, null, null, null};
        update(poll, newParams);
    }

    public void voteYes(Poll poll){
        String[] newParams = {null, null, null, null, null, null, null, null, String.valueOf(poll.getYesOption() + 1)};
        update(poll, newParams);
    }
    public void voteNo(Poll poll){
        String[] newParams = {null, null, String.valueOf(poll.getNoOption() + 1), null, null, null, null, null, null};
        update(poll, newParams);
    }

}
