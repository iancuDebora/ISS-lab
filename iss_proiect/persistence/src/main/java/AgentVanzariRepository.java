import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class AgentVanzariRepository {

    private static SessionFactory sessionFactory;

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public AgentVanzariRepository() {

    }

    public Iterable<AgentVanzari> findAll()
    {
        initialize();
        Iterable<AgentVanzari> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from AgentVanzari").list();
            for (AgentVanzari event : (List<AgentVanzari>) result) {
                System.out.println("EventAnnot (" + event.getId() + ") : " + event.getParola());
            }
            session.getTransaction().commit();
        }
        close();
        return result;

    }


    public AgentVanzari findOne(String string) {

        initialize();
        Iterable<AgentVanzari> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from AgentVanzari").list();
            for (AgentVanzari event : (List<AgentVanzari>) result) {
                System.out.println("EventAnnot (" + event.getId() + ") : " + event.getParola());
            }
            session.getTransaction().commit();
        }
        close();
        AgentVanzari rez = null;
        for (AgentVanzari agent:result)
            if (agent.getId().equals(string))
                rez=agent;
        return rez;
    }


}
