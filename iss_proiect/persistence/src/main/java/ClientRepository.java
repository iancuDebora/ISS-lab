import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ClientRepository {

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

    public ClientRepository() {

    }

    public Iterable<Client> findAll()
    {
        initialize();
        Iterable<Client> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Client").list();
            for (Client event : (List<Client>) result) {
                System.out.println("EventAnnot (" +event.getNume());
            }
            session.getTransaction().commit();
        }
        close();
        return result;

    }


    public int size() {
        return 0;
    }






}
