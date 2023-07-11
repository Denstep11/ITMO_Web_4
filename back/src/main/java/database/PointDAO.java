package database;

import data.PointDB;
import data.UserDB;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PointDAO {

    public List<PointDB> getAll(){
        List<PointDB> points = (List<PointDB>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From PointDB", PointDB.class).list();
        return points;
    }

    public void save(PointDB point){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(point);
        tx1.commit();
        session.close();
    }

    public List<PointDB> findPointsByLogin(String login){
        String hql = "From PointDB where login=:loginfind";
        List<PointDB> points = (List<PointDB>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(hql).setParameter("loginfind", login).list();
        return points;
    }
}
