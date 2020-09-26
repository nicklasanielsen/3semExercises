package rest;

import DTOs.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import facades.PersonFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nicklas Nielsen
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDefault() {
        return Response.ok().build();
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        return Response.ok(FACADE.getAllPersons()).build();
    }

    @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonById(@PathParam("id") int id) throws PersonNotFoundException {
        return Response.ok(FACADE.getPerson(id)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(String person) throws MissingInputException {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO personAdded = FACADE.addPerson(personDTO.getfName(), personDTO.getlName(), personDTO.getPhone(),
                personDTO.getStreet(), personDTO.getZip(), personDTO.getCity());

        return Response.ok(personAdded).build();
    }

    @PUT
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPerson(@PathParam("id") int id, String person) throws PersonNotFoundException, MissingInputException {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personDTO.setId(id);

        PersonDTO editedPerson = FACADE.editPerson(personDTO);

        return Response.ok(editedPerson).build();
    }

    @DELETE
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        FACADE.deletePerson(id);
        return Response.ok("{\"status\": \"removed\"}").build();
    }

}
