import DAO.impl.*;
import entity.*;

import javax.persistence.*;
import java.util.*;

public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "FeedApp";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("select t from User t");
        List<User> todoList = q.getResultList();
        for (User user: todoList
             ) {
            System.out.println(user.getUsername());
        }
        UserImpl userImpl = new UserImpl(em);
        String[] params_newUser = {"example@org.no", "Ola", "Nordmann", "Password", "OlaNordmann"};
        //userImpl.makeNewUser(params_newUser);
        String[] params_poll = {"False", "10:00", "Dette er vår andre poll", "andre poll", "True", "11:00", "2 min"};
        Optional<User> u = userImpl.get(1);
        System.out.println("u: " + u);
        userImpl.makeNewPoll(u, params_poll);


    }
}
