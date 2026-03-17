package com.example;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Delete Book");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // Add Book
                case 1:
                    Transaction tx1 = session.beginTransaction();

                    Book book = new Book();

                    System.out.print("Enter Title: ");
                    book.setTitle(sc.nextLine());

                    System.out.print("Enter Author: ");
                    book.setAuthor(sc.nextLine());

                    System.out.print("Enter Publisher: ");
                    book.setPublisher(sc.nextLine());

                    System.out.print("Enter Quantity: ");
                    book.setQuantity(sc.nextInt());

                    session.save(book);
                    tx1.commit();

                    System.out.println("Book Added Successfully!");
                    break;

                // View Books
                case 2:
                    Query<Book> query = session.createQuery("from Book", Book.class);
                    List<Book> books = query.list();

                    System.out.println("\n---- Book List ----");
                    for (Book b : books) {
                        System.out.println(
                                b.getBook_id() + " | " +
                                b.getTitle() + " | " +
                                b.getAuthor() + " | " +
                                b.getPublisher() + " | " +
                                b.getQuantity());
                    }
                    break;

                // Delete Book
                case 3:
                    Transaction tx3 = session.beginTransaction();

                    System.out.print("Enter Book ID to Delete: ");
                    int id = sc.nextInt();

                    Book deleteBook = session.get(Book.class, id);

                    if (deleteBook != null) {
                        session.delete(deleteBook);
                        System.out.println("Book Deleted Successfully!");
                    } else {
                        System.out.println("Book Not Found!");
                    }

                    tx3.commit();
                    break;

                // Exit
                case 4:
                    session.close();
                    System.out.println("Exiting Program...");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}