package rest;

import DTOs.PersonDTO;
import entities.Address;
import entities.Person;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api/person";
    private static List<Person> persons;
    private static List<PersonDTO> personDTOs;
    private static List<Address> addresses;

    private static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        persons = new ArrayList<>();
        personDTOs = new ArrayList<>();
        addresses = new ArrayList<>();

        httpServer = startServer();

        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        // Add test address data here
        addresses.add(new Address("Øksendrupvej 13", 1321, "København K"));

        // Add test person data here
        persons.add(new Person("Nicklas", "Nielsen", "11111111"));
        persons.add(new Person("Mathias", "Nielsen", "22222222"));
        persons.add(new Person("Nikolaj", "Larsen", "11223344"));

        try {
            em.getTransaction().begin();
            for (Person person : persons) {
                em.persist(person);
                person.setAddress(addresses.get(0));
                em.merge(person);
                em.flush();
                em.clear();
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        persons.forEach(person -> {
            personDTOs.add(new PersonDTO(person));
        });
    }

    @AfterEach
    public void tearDown() {
        persons.clear();
        personDTOs.clear();
        addresses.clear();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testAPI_online() {
        // Arrange + Act + Assert
        given().when().get("/person").then().statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    public void testGetAllPersons_size() {
        // Arrange + Act + Assert
        given()
                .contentType(ContentType.JSON)
                .get("/person/all")
                .then()
                .assertThat()
                .body("all.size()", is(personDTOs.size()));
    }

    @Test
    public void testGetAllPersons_content() {
        // Arrange
        List<PersonDTO> expected = personDTOs;

        // Act
        List<PersonDTO> actual = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/person/all")
                .then()
                .extract().body().jsonPath().getList("all", PersonDTO.class);

        // Assert
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testGetPersonById_found() {
        // Arrange
        PersonDTO expected = personDTOs.get(0);

        // Act
        PersonDTO actual = given()
                .when()
                .get("/person/id/" + expected.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().as(PersonDTO.class);

        // Assert
        assertThat(actual, is(expected));
    }

    @Test
    public void testGetPersonById_not_found() {
        // Arrange
        int id = personDTOs.get(personDTOs.size() - 1).getId() + 1;

        // Act + Assert
        given().when().get("/person/id/" + id).then().statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testAddPerson_added() {
        // Arrange
        Address address = new Address("Tårup Byvej 2", 1265, "København K");
        Person person = new Person("Sommer", "Kaiser", "88447755");
        person.setAddress(address);
        PersonDTO expected = new PersonDTO(person);

        // Act
        PersonDTO actual = given()
                .contentType(ContentType.JSON)
                .body(expected)
                .when()
                .post("/person")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().as(PersonDTO.class);

        // Assert
        assertThat(actual, is(expected));
    }

    @Test
    public void testAddPerson_invalid_firstName() {
        // Arrange
        Address address = new Address("Grønvangen 38", 6853, "Vejers Strand");
        Person person = new Person("", "Kaiser", "88447755");
        person.setAddress(address);
        PersonDTO personDTO = new PersonDTO(person);

        // Act + Assert
        given()
                .contentType(ContentType.JSON)
                .body(personDTO)
                .post("/person")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }

    @Test
    public void testAddPerson_invalid_lastName() {
        // Arrange
        Address address = new Address("Mølleløkken 93", 5270, "Odense N");
        Person person = new Person("Sommer", "", "88447755");
        person.setAddress(address);
        PersonDTO personDTO = new PersonDTO(person);

        // Act + Assert
        given()
                .contentType(ContentType.JSON)
                .body(personDTO)
                .post("/person")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }

    @Test
    public void testAddPerson_server_error() {
        // Arrange + Act + Assert
        given().contentType(ContentType.JSON).when().post("/person").then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode());
    }

    @Disabled
    @Test
    public void testEditPerson_edited() {
        // Arrange
        PersonDTO expected = personDTOs.get(0);
        expected.setfName("Lars");

        // Act
        PersonDTO actual = given()
                .contentType(ContentType.JSON)
                .body(expected)
                .when()
                .put("/person/id/" + expected.getId())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract().body().as(PersonDTO.class);

        // Assert
        assertThat(actual, is(expected));
    }

    @Test
    public void testEditPerson_not_found() {
        // Arrange
        PersonDTO personDTO = personDTOs.get(personDTOs.size() - 1);
        personDTO.setId(personDTO.getId() + 1);

        // Act + Assert
        given()
                .contentType(ContentType.JSON)
                .body(personDTO)
                .when()
                .put("/person/id/" + personDTO.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testEditPerson_invalid_firstName() {
        // Arrange
        PersonDTO personDTO = personDTOs.get(0);
        personDTO.setfName("");

        // Act + Assert
        given()
                .contentType(ContentType.JSON)
                .body(personDTO)
                .put("/person/id/" + personDTO.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }

    @Test
    public void testEditPerson_invalid_lastName() {
        // Arrange
        PersonDTO personDTO = personDTOs.get(0);
        personDTO.setlName("");

        // Act + Assert
        given()
                .contentType(ContentType.JSON)
                .body(personDTO)
                .put("/person/id/" + personDTO.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode());
    }

    @Test
    public void testEditPerson_server_error() {
        // Arrange + Act + Assert
        given().when().put("/person/id/1").then().statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode());
    }

    @Test
    public void testDeletePerson_deleted() {
        // Arrange
        int id = personDTOs.get(0).getId();

        // Act + Assert
        given().when().delete("/person/id/" + id).then().statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    public void testDeletePerson_invalid_id() {
        // Arrange
        int id = personDTOs.get(personDTOs.size() - 1).getId() + 1;

        // Act + Assert
        given().when().delete("/person/id/" + id).then().statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

}
