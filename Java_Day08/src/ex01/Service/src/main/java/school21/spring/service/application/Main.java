package school21.spring.service.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import school21.spring.service.repositories.UsersRepository;
import org.springframework.context.*;
import school21.spring.service.models.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args){
        CreateDataBase();

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll());
       
        System.out.println();
        usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findById(2).toString());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findById(2).toString());

        System.out.println();
        usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findByEmail("test_2") );
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findByEmail("test_2"));

        System.out.println();
        usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        usersRepository.delete(1L);
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersRepository.delete(1L);
        System.out.println(usersRepository.findAll());

        System.out.println();
        usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        usersRepository.update(new User(3L, "email"));
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersRepository.update(new User(3L, "email"));
        System.out.println(usersRepository.findAll());

        System.out.println();
        usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        usersRepository.save(new User(30L, "\033[32memail\033[0m"));
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        usersRepository.save(new User(31L, "\033[31memail\033[0m"));
        System.out.println(usersRepository.findAll());
    }

    private static void CreateDataBase(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
            for(int i=1; i<=10; i++){
                Transaction transaction = session.beginTransaction();
                User user = new User((long)i, "test_"+i);
                session.save(user);
                transaction.commit();
            }
        session.close();
        sessionFactory.close();
    }
}