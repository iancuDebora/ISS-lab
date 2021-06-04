import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class FirmaRepository {

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

    public FirmaRepository() {

    }

    public Iterable<Firma> findAll()
    {
        initialize();
        Iterable<Firma> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Firma").list();
            for (Firma event : (List<Firma>) result) {
                System.out.println("EventAnnot (" +event.getDenumire());
            }
            session.getTransaction().commit();
        }
        close();
        return result;

    }


    public int size() {
        return 0;
    }


    public void save(Firma entity) {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        close();
    }


    public void delete(Integer integer) {

    }


    public void update(AgentVanzari entity) {

    }


    public Firma findOne(String integer) {
        initialize();
        Iterable<Firma> result= null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            result = session.createQuery(" from Firma").list();
            for (Firma event : (List<Firma>) result) {
                System.out.println("EventAnnot (" + event.getDenumire()+event.agenti.toString());
            }
            session.getTransaction().commit();
        }
        close();
        Firma rez = null;
        for (Firma agent:result)
            if (agent.getDenumire().equals(integer))
                rez=agent;
        return rez;
    }


}
