package DTOs;

import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonsDTO {

    private List<PersonDTO> all = new ArrayList<>();

    public PersonsDTO(List<Person> persons) {
        persons.forEach(person -> {
            all.add(new PersonDTO(person));
        });
    }

    public List<PersonDTO> getAll() {
        return all;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonsDTO other = (PersonsDTO) obj;
        if (!Objects.equals(this.all, other.all)) {
            return false;
        }
        return true;
    }

}
