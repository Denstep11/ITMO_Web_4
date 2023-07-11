package database;

import data.UserDB;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.ejb.Stateless;

@Stateless
public class UserDAO {
    public UserDB findByLogin(String login){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UserDB.class, login);
    }

    public void save(UserDB user){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public boolean isfindUserByLogin(String login){
        String hql = "From UserDB where login=:loginfind";
        return  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(hql).setParameter("loginfind", login).list().size()>0;
    }

    public UserDB findUserByLogin(String login){
        String hql = "From UserDB where login=:loginfind";
        return (UserDB) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(hql).setParameter("loginfind", login).list().get(0);
    }

}
