package homework_06;

import homework_06.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookHelper {

    private SessionFactory sessionFactory;

    public BookHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    //TODO task_03
    public void deleteCriteriaLogicBook(int id, int author_id)  {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaDelete<Book> cd = cb.createCriteriaDelete(Book.class);

        Root<Book> root = cd.from(Book.class);
        // Более усложненный критерий для удаления. Есть условия И и ИЛИ.
        // В данном случае удаление происходил для записей у которых или имя содержит author и фамилия содержит 2
        // или фамилия равна Лермонтов
        cd.where(
                        cb.equal(root.get("id"), id),
                        cb.equal(root.get("author_id"), author_id)
        );
        //этап выполнения запроса
        Query query = session.createQuery(cd);
        int deletedValues = query.executeUpdate();
        System.out.println("Deleted values: " + deletedValues);

        session.getTransaction().commit();

        session.close();
    }


    public List<Book> getBookCriteriaLogic(){
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();
        // этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        // Через сессию получаем критерияБилдер
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Через критерияБилдер создаем критерияЗапрос
        //  CriteriaQuery это будет объект, в котором мы будем задавать условия.
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        // Через Root мы будем забирать наши условия
        Root<Book> root = cq.from(Book.class);// первостепенный, корневой entity (в sql запросе - from)

        cq.where(cb.or(
                cb.and(
                        cb.equal(root.get("id"), 10),
                        cb.like(root.<String>get("name"), "%Name_10%")
                ),
                cb.equal(root.get("author_id"), 5)
        ));

        //этап выполнения запроса - С помощью сессии создаем запрос и в него передаем критерии
        Query query = session.createQuery(cq);

        // Не используем никаких циклов для перебора, сразу получаем список авторов.
        List<Book> bookList = query.getResultList();

        //Закрываем сессию
        //        Транзакция здесь не пишется, тк. мы получаем информацию из таблицы, а не вносим туда изменения.
        session.close();
        return bookList;
    }





}
