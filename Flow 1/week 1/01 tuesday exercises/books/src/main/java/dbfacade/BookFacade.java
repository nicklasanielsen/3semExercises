package dbfacade;

import entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicklas Nielsen
 */
public class BookFacade {
    
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        BookFacade facade = BookFacade.getBookFacade(factory);
        
        Book b1 = facade.addBook("Author 1");
        Book b2 = facade.addBook("Author 2");
        
        // Find book by ID
        System.out.println("Book 1: " + facade.findBook(b1.getId()).getAuthor());
        System.out.println("Book 2: " + facade.findBook(b2.getId()).getAuthor());
        
        // Find all books
        System.out.println("Number of books: " + facade.getAllBooks().size());
    }

    private static EntityManagerFactory emf;
    private static BookFacade instance;

    private BookFacade() {
    }

    /**
     * Hvis metoden ikke er blevet kørt, vil der blive oprettet en instance
     * baseret på EntityManagerFactory'en.
     *
     * @param _emf Bruges til at oprette en instance af klassen.
     * @return Returnere selve klassen, hvori en EntityManagerFactory er
     * opbevaret til senere brug.
     */
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    /**
     * Bruges til at oprette en ny bog.
     *
     * @param author Definere hvem der har skrevet bogen.
     * @return Returnere bog objektet.
     */
    public Book addBook(String author) {
        Book book = new Book(author);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } finally {
            em.close();
        }
    }

    /**
     * Finder en bog baseret på bogens id.
     *
     * @param id Defindere hvilket id den ønskede bog har.
     * @return Returnere et bog objekt.
     */
    public Book findBook(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Book book = em.find(Book.class, id);
            return book;
        } finally {
            em.close();
        }
    }

    /**
     * Bruges til at indsamle alle boger på en gang.
     *
     * @return Liste med bog objekter.
     */
    public List<Book> getAllBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("Select book from Book book", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
