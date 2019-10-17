package com.accenture.java.apicensus.mapper;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.dto.PersonDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class responsible for transforming the
 * DTO to ENTITY and vice versa.
 *
 * @author Gian F. S.
 */
@Component
public class PersonMapper {

    /**
     * Transforms the Person DTO to ENTITY.
     * <br><br>
     * If the DTO is <b>null</b>, null will be returned;
     * If not returns a Person ENTITY instance.
     *
     * @param personDTO the person DTO instance to transform.
     *
     * @return a Person ENTITY; or <b>null</b> if the personDTO param
     *          is null.
     */
    public Person toEntity(PersonDTO personDTO) {
        Person person = null;
        if (personDTO != null) {
            person = Person.builder().ssn(personDTO.getSsn()).name(personDTO.getName()).surname(personDTO.getSurname())
                .bornDate(personDTO.getBornDate() != null ? personDTO.getBornDate().toString() : null)
                .country(personDTO.getCountry() != null ? personDTO.getCountry().name() : null)
                .genre(personDTO.getGenre()).build();
        }
        return person;
    }

    /**
     * Transforms the Person DTO list to ENTITY list.
     * <br><br>
     * For each DTO, call the toEntity method.
     *
     * @param persons the persons DTO instance to transform.
     * @return a list with Entities; or <b>null</b> if persons param
     * is null.
     * @see java.util.stream.Stream
     * @see #toEntity(PersonDTO)
     */
    public List<Person> toEntity(List<PersonDTO> persons) {
        List<Person> result = null;
        if (persons != null) {
            result = persons.stream().map(this::toEntity).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * Transforms the Person ENTITY to DTO.
     * <br><br>
     * If the ENTITY is <b>null</b>, null will be returned;
     * If not returns a Person DTO instance.
     *
     * @param person the person ENTITY instance to transform.
     *
     * @return a Person DTO; or <b>null</b> if the person param
     *          is null.
     */
    public PersonDTO toDTO(Person person) {
        PersonDTO personDTO = null;
        if (person != null) {
            personDTO = PersonDTO.builder().ssn(person.getSsn()).name(person.getName()).surname(person.getSurname())
                .bornDate(person.getBornDate() != null ? LocalDate.parse(person.getBornDate()) : null)
                .country(person.getCountry() != null ? Country.valueOf(person.getCountry()) : null)
                .genre(person.getGenre()).build();
        }
        return personDTO;
    }

    /**
     * Transforms the Person ENTITY list to DTO list.
     * <br><br>
     * For each ENTITY, call the toDTO method.
     *
     * @param persons the persons ENTITY instance to transform.
     * @return a list with DTOs; or <b>null</b> if persons param
     * is null.
     * @see java.util.stream.Stream
     * @see #toDTO(Person)
     */
    public List<PersonDTO> toDTO(List<Person> persons) {
        List<PersonDTO> result = null;
        if (persons != null) {
            result = persons.stream().map(this::toDTO).collect(Collectors.toList());
        }
        return result;
    }
}
