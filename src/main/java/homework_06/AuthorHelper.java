package homework_06;


import homework_06.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }


    public List<Author> getAndSetAuthorList(String pater){
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();
        // этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        // Через сессию получаем критерияБилдер
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Через критерияБилдер создаем критерияЗапрос
        //  CriteriaQuery это будет объект, в котором мы будем задавать условия.
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);

        // Через Root мы будем забирать наши условия
        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)

        cq.where(cb.or(

                cb.like(root.<String>get("name"), pater)
                // cb.like(root.<String>get("author_id"), "%2%")
        ) );

        //этап выполнения запроса - С помощью сессии создаем запрос и в него передаем критерии
        Query query = session.createQuery(cq);

        // Не используем никаких циклов для перебора, сразу получаем список авторов.
        List<Author> authorList = query.getResultList();



        for(int i=0; i < 1; i++){

            Author elementAvtor = authorList.get(i);
            elementAvtor.setName("TETST_SET_name");
            authorList.set(i,elementAvtor);

            System.out.println(authorList.get(i) );

            session.save( authorList ) ;
        }


        // сгенерит ID и вставит в объект
        session.getTransaction().commit();
        session.close();



        //Закрываем сессию
        //        Транзакция здесь не пишется, тк. мы получаем информацию из таблицы, а не вносим туда изменения.
        //   session.close();

        return authorList;
    }

    public List<Author> getAuthorListNew(String pater){
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();
        // этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        // Через сессию получаем критерияБилдер
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Через критерияБилдер создаем критерияЗапрос
        //  CriteriaQuery это будет объект, в котором мы будем задавать условия.
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);

        // Через Root мы будем забирать наши условия
        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)

        cq.where(cb.or(

                cb.like(root.<String>get("name"), pater)
               // cb.like(root.<String>get("author_id"), "%2%")
        ) );

        //этап выполнения запроса - С помощью сессии создаем запрос и в него передаем критерии
        Query query = session.createQuery(cq);

        // Не используем никаких циклов для перебора, сразу получаем список авторов.
        List<Author> authorList = query.getResultList();



        //Закрываем сессию
        //        Транзакция здесь не пишется, тк. мы получаем информацию из таблицы, а не вносим туда изменения.
       session.close();

        return authorList;
    }


    public Author updateAuthor(Author author){

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        // Здесь происходит команда select
        Author author1 = session.get(Author.class, author.getId());

// Данная часть должна отработать для выполнения операции update, если будет обращение к существующей записи
// Мы получаем данные входящего параметра и обновляем данные записи
        author1.setName(author.getName());
        author1.setLastName(author.getLastName());
        session.save(author1);

        // сгенерит ID и вставит в объект
        session.getTransaction().commit();

        session.close();

        return author;

    }



    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id); // получение объекта по id
        session.close();
        return author;
    }

    public Author addAuthor(Author author){

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(author); // сгенерит ID и вставит в объект

        session.getTransaction().commit();

        session.close();


        return author;

    }

    public void deleteById(long id_22) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        //Берем нашего автора по айди
        Author author = session.get(Author.class, id_22);
        // Удаляем полученного автора
        session.delete(author);
        // Подтверждаем нашу транзакцию-удаление
        session.getTransaction().commit();
        session.close();
    }

    // Метод удаления по критерию
    public void deleteCriteria()  {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);

        Root<Author> root = cd.from(Author.class);// первостепенный, корневой entity
        // Мы будем удалять записи, в середине имени которыъ будет содержаться единица
        cd.where(cb.like(root.<String>get("name"), "%1%"));

        //этап выполнения запроса
        Query query = session.createQuery(cd);
        int deletedValues = query.executeUpdate();

        System.out.println("Deleted values: " + deletedValues);

        session.getTransaction().commit();

        session.close();
    }

    public void deleteCriteriaLogic()  {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);

        Root<Author> root = cd.from(Author.class);
        // Более усложненный критерий для удаления. Есть условия И и ИЛИ.
        // В данном случае удаление происходил для записей у которых или имя содержит author и фамилия содержит 2
        // или фамилия равна Лермонтов
        cd.where(cb.or(
                cb.and(
                        cb.like(root.<String>get("name"), "%1%"),
                        cb.like(root.<String>get("lastName"), "%2%")
                ),
                cb.equal(root.get("lastName"), "Lermontov")
        ));


        //этап выполнения запроса
        Query query = session.createQuery(cd);
        int deletedValues = query.executeUpdate();
        System.out.println("Deleted values: " + deletedValues);

        session.getTransaction().commit();

        session.close();
    }

}
