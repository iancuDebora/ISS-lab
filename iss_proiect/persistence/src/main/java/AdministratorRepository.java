import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class AdministratorRepository {

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

    public AdministratorRepository() {

    }

    public Iterable<Administrator> findAll()
    {
        initialize();
        Iterable<Administrator> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Administrator").list();
            for (Administrator event : (List<Administrator>) result) {
                System.out.println("EventAnnot (" + event.getId() + ") : " + event.getParola());
            }
            session.getTransaction().commit();
        }
        close();
        return result;

    }


    public Administrator findOne(String string) {
        initialize();
        Iterable<Administrator> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Administrator").list();
            for (Administrator event : (List<Administrator>) result) {
                System.out.println("EventAnnot (" + event.getId() + ") : " + event.getParola());
            }
            session.getTransaction().commit();
        }
        close();
        Administrator rez = null;
        for (Administrator agent:result)
            if (agent.getId().equals(string))
                rez=agent;
        return rez;
    }


}
