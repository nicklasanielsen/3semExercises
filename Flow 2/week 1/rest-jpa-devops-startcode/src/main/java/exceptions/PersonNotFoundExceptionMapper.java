package exceptions;

import DTOs.ExceptionDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nicklas Nielsen
 */
@Provider
public class PersonNotFoundExceptionMapper implements ExceptionMapper<PersonNotFoundException> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(PersonNotFoundException exception) {
        Logger.getLogger(PersonNotFoundExceptionMapper.class.getName()).log(Level.SEVERE, null, exception);
        ExceptionDTO err = new ExceptionDTO(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage());

        return Response.status(Response.Status.NOT_FOUND).entity(GSON.toJson(err)).type(MediaType.APPLICATION_JSON).build();
    }

}
