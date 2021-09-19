package DAO.impl;

import DAO.*;
import entity.*;

import javax.persistence.*;
import java.util.*;

public class UserImpl implements DAO<User> {
    private final EntityManager entityManager;
    private final PollsImpl pollsImpl;

    public UserImpl(EntityManager em){
        entityManager = em;
        pollsImpl = new PollsImpl(em);

    }

    public void makeNewUser(String[] params){
        String email = params[0];
        String f_name = params[1];
        String l_name = params[2];
        String password = params[3];
        String username = params[4];

        Polls polls = new Polls();
        executeInsideTransaction(entityManager1 -> entityManager1.persist(polls), entityManager);

        User newUser = new User();
        newUser.setE_mail(email);
        newUser.setF_name(f_name);
        newUser.setL_name(l_name);
        newUser.setPassword(password);
        newUser.setUsername(username);
        newUser.setPolls(polls);

        save(newUser);

        polls.setUser(newUser);
        String[] params1 = {null, null, null};
        pollsImpl.update(polls, params1, newUser);

    }

    public void makeNewPoll(Optional<User> user, String[] params) {
        pollsImpl.makeNewPoll(user.get().getPolls(), params);

    }
    @Override
    public void update(User user, String[] params) {
        String email = params[0] != null ? params[0] : user.getE_mail();
        String f_name = params[1] != null ? params[1] : user.getF_name();
        String l_name = params[2] != null ? params[2] : user.getL_name();
        String password = params[3] != null ? params[3] : user.getPassword();
        String username = params[4] != null ? params[4] : user.getUsername();

        user.setE_mail(email);
        user.setF_name(f_name);
        user.setL_name(l_name);
        user.setPassword(password);
        user.setUsername(username);

        save(user);
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }

    @Override
    public void save(User user) {
        executeInsideTransaction(entityManager1 -> entityManager1.persist(user), entityManager);
    }

    @Override
    public void delete(User user) {
        executeInsideTransaction(entityManager1 -> entityManager1.remove(user), entityManager);
    }
}
