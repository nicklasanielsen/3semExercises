package facades;

import DTOs.PersonDTO;
import DTOs.PersonsDTO;
import entities.Address;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone, String street, int zip, String city) throws MissingInputException {
        if (fName.isEmpty() || lName.isEmpty()) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        } else if (street.isEmpty() || zip <= 0 || city.isEmpty()) {
            throw new MissingInputException("Street and/or city is missing");
        }

        EntityManager em = getEntityManager();

        Address address = getAddress(street, zip, city);
        Person person = new Person(fName, lName, phone);

        try {
            em.getTransaction().begin();
            em.persist(person);
            person.setAddress(address);
            em.merge(person);
            em.getTransaction().commit();

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, id);

            if (person == null) {
                throw new PersonNotFoundException("Could not delete, provided id does not exist");
            }

            Address address = person.getAddress();
            address.removePerson(person);

            boolean deleteAddress = false;
            if (address.getPersons().isEmpty()) {
                deleteAddress = true;
            }

            em.getTransaction().begin();
            em.remove(person);

            if (deleteAddress) {
                em.remove(address);
            }

            em.getTransaction().commit();

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, id);

            if (person == null) {
                throw new PersonNotFoundException("No person with provided id found");
            }

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Person.getAll");
            List<Person> persons = query.getResultList();

            return new PersonsDTO(persons);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
        if (p.getfName().isEmpty() || p.getlName().isEmpty()) {
            throw new MissingInputException("First Name and/or Last Name is missing");
        } else if (p.getStreet().isEmpty() || p.getZip() <= 0 || p.getCity().isEmpty()) {
            throw new MissingInputException("Street and/or city is missing");
        }

        EntityManager em = getEntityManager();

        try {
            Person person = em.find(Person.class, p.getId());

            if (person == null) {
                throw new PersonNotFoundException("Could not edit, provided id does not exist");
            }

            Address address = person.getAddress();
                        
            em.getTransaction().begin();
            
            if (address.getPersons().contains(person) && address.getPersons().size() == 1) {
                em.remove(address);
            }

            address.removePerson(person);
            address = getAddress(p.getStreet(), p.getZip(), p.getCity());

            // Edit person
            person.setFirstName(p.getfName());
            person.setLastName(p.getlName());
            person.setPhone(p.getPhone());
            person.setLastEdited(new Date());

            // Edit address
            person.setAddress(address);

            em.merge(person);
            em.getTransaction().commit();

            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    private Address getAddress(String street, int zip, String city) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Address.getAddress");
            query.setParameter("street", street);
            query.setParameter("zip", zip);
            query.setParameter("city", city);

            Address address;
            List<Address> addresses = query.getResultList();

            if (addresses.isEmpty()) {
                address = new Address(street, zip, city);
            } else {
                int id = addresses.get(0).getId();
                address = em.find(Address.class, id);

                query = em.createNamedQuery("Person.getByAddress");
                query.setParameter("id", address.getId());

                List<Person> persons = query.getResultList();
                address.setPersons(persons);
            }

            return address;
        } finally {
            em.close();
        }
    }

}
