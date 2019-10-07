package com.accenture.java.apicensus.mapper;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.dto.PersonDTO;
import com.accenture.java.apicensus.entity.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        if (personDTO != null) {
            Person person = new Person();
            person.setSsn(personDTO.getSsn());
            person.setName(personDTO.getName());
            person.setSurname(personDTO.getSurname());
            person.setBornDate(personDTO.getBornDate() != null
                ? personDTO.getBornDate().toString()
                : null);
            person.setCountry(personDTO.getCountry() != null
                ? personDTO.getCountry().name()
                : null);
            person.setGenre(personDTO.getGenre());

            return person;
        }
        return null;
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
        if (person != null) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setSsn(person.getSsn());
            personDTO.setName(person.getName());
            personDTO.setSurname(person.getSurname());
            personDTO.setBornDate(person.getBornDate() != null
                ? LocalDate.parse(person.getBornDate())
                : null);
            personDTO.setCountry(person.getCountry() != null
                ? Country.valueOf(person.getCountry())
                : null);
            personDTO.setGenre(person.getGenre());

            return personDTO;
        }
        return null;
    }
}
