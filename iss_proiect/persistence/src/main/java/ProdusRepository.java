import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ProdusRepository {


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

    public ProdusRepository() {

    }

    public Iterable<Produs> findAll()
    {
        initialize();
        Iterable<Produs> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Produs").list();
            for (Produs event : (List<Produs>) result) {
                System.out.println("EventAnnot (" + event.getDenumire());
            }
            session.getTransaction().commit();
        }
        close();
        return result;

    }

    public void save(Produs entity) {
        System.out.println("in repo");
        System.out.println(entity.getDenumire());
        initialize();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        close();
        Iterable<Produs> all = this.findAll();
        for(Produs pr:all)
            System.out.println(pr.getDenumire());

    }


    public void delete(Produs entity) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
        close();
    }


    public void update(Produs entity) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        close();
    }


    public Produs findOne(String string) {

        initialize();
        Iterable<Produs> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Produs").list();
            for (Produs event : (List<Produs>) result) {
                System.out.println("EventAnnot (" + event.getDenumire());
            }
            session.getTransaction().commit();
        }
        close();
        Produs rez = null;
        for (Produs agent:result)
            if (agent.getDenumire().equals(string))
                rez=agent;
        return rez;
    }

}
