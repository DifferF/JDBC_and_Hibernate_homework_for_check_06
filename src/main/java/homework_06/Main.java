package homework_06;


import homework_06.entity.Author;
import homework_06.entity.Book;

import java.util.List;

/*TODO task_02
Задание 2+
Обновить поле name для всех записей,
у которых длина значения поля last_name больше 7 В поле name записать значение «1»
* Задание на самостоятельный поиск решений.
 */

/*TODO task_03
Задание 3+
В класс BookHelper пакета ex_004_relations дописать методы удаления книге по id и по автору.
 */

/*TODO task_04
Задание 4+
Используя MySQL Workbench переписать базу данных так, чтобы одну книгу могли б написать несколько авторов,
также один автор может написать несколько книг. Реализовать связь многие ко многим.
 */

/*TODO task_05
Задание 5
Из пакета ex_002_select_where написать отдельный метод для выборки по поиску выражения
и в пакете ex_003_delete методы createCriteria и createCriteriaLogic переписать правильно.
 */

public class Main {

    public static void main(String[] args) {
        BookHelper bH = new BookHelper();
        AuthorHelper ah = new AuthorHelper();

        // TODO task_02
        List <Author> authorList_2 = ah.getAuthorListNew("________%");
        for (Author a : authorList_2) {
            Author author1 = ah.getAuthorById(a.getId());
            author1.setName("1");
            // author1.setLastName("task_02");
            ah.updateAuthor(author1);
        }

        //TODO task_03
        // удаление по id и по автору
        bH.deleteCriteriaLogicBook(11,10);

        
        // TODO task_05
        // Получаем список авторов из БД по условию
        // ah.getAuthorListNew("%Name_58%");
        List <Author> authorList = ah.getAuthorListNew("____%"); // условие
          for (Author a : authorList) {
        System.out.println(a.getId() + " " + a.getName());
         }

        // Выборка по Book где :
        // id = 10 и name = Name_10 или author_id = 5
        List <Book> booksList = bH.getBookCriteriaLogic(); // условие
        for (Book b : booksList) {
            System.out.println(b.getId() + " " + b.getName());
        }
    }
}
