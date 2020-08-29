package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import entity.Animal;
import java.util.Random;
import javax.persistence.Query;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Nicklas Nielsen
 */
@Path("animals_db")
public class AnimalFromDB {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnimalFromDB
     */
    public AnimalFromDB() {
    }

    @Path("animals")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            return new Gson().toJson(animals);
        } finally {
            em.close();
        }
    }
    
    @Path("animalbyid/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimal(@PathParam("id") int id) {
        EntityManager em = emf.createEntityManager();
        
        try{
            Animal animal = em.find(Animal.class, id);
            return new Gson().toJson(animal);
        } finally {
            em.close();
        }
    }
    
    @Path("animalbytype/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimalByType(@PathParam("type") String type){
        EntityManager em = emf.createEntityManager();
        
        try{
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.type = :type", Animal.class);
            query.setParameter("type", type);
            List<Animal> animals = query.getResultList();
            
            return new Gson().toJson(animals);
        } finally {
            em.close();
        }
    }
    
    @Path("random_animal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomAnimal(){
        EntityManager em = emf.createEntityManager();
        
        try{
            Query query = em.createQuery("SELECT COUNT(a) FROM Animal a");
            Long count = (Long) query.getSingleResult();
            
            Random random = new Random();
            int number = random.nextInt(count.intValue()) + 1;
            
            query = em.createQuery("SELECT a FROM Animal a WHERE a.id = :id");
            query.setParameter("id", number);
            
            return new Gson().toJson(query.getSingleResult());
        } finally{
            em.close();
        }
    }
}
