package de.teleran.cabas.repository;

import de.teleran.cabas.entity.City;
import de.teleran.cabas.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {


    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Person> findAllByGuardianId(Long guardianId);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);



    List<Person> findAllByIdIsIn(List<Long> ids);

    Person findByEmail(String email);
    List<Person> findAllByCityIsIn(List<City> cities);

}
