package de.telran.cabas.service.impl;

import com.sun.istack.Nullable;
import de.telran.cabas.convert.Convertes;
import de.telran.cabas.dto.*;
import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.repository.PersonRepository;
import de.telran.cabas.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    private CityRepository cityRepository;

    @Override
    @Transactional
    public PersonResponseDTO create(PersonRequestDTO personDTO) {

        checkEmail(personDTO.getEmail());
        checkPhoneNumber(personDTO.getPhoneNumber());
        checkName(personDTO.getFirstName(), personDTO.getLastName());
        var guardian = getPersonByIdOrThrow(personDTO.getGuardianId());

        checkIfCanBeGuardian(guardian);

        Long guardianId = guardian == null ? null : guardian.getId();

        Person person =
                Person
                        .builder()
                        .firstName(personDTO.getFirstName())
                        .lastName(personDTO.getLastName())
                        .dateOfBirth(personDTO.getDateOfBirth())
                        .email(personDTO.getEmail())
                        .phoneNumber(personDTO.getPhoneNumber())
                        .guardianId(guardianId)
                        .city(null)
                        .build();
        personRepository.save(person);

        var children = getChildren(person);
        Long areaId = getAreaId(person.getCity());
        Long cityId = null;

        return Convertes.convertPersonIntoResponseDTO(person, areaId, guardian, children, cityId);
    }

    @Override
    @Transactional
    public void update(Long id, UpdatePersonRequestDTO personDTO) {
        var person = getPersonByIdOrThrow(id);

        if (person == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Path variable cannot be null"
            );
        }

        if (!person.getEmail().equals(personDTO.getEmail())) {
            checkEmail(personDTO.getEmail());
            person.setEmail(personDTO.getEmail());
        }
        if (!person.getPhoneNumber().equals(personDTO.getPhoneNumber())) {
            checkPhoneNumber(personDTO.getPhoneNumber());
            person.setPhoneNumber(personDTO.getPhoneNumber());
        }
        if (!person.getFirstName().equals(personDTO.getFirstName()) ||
                !person.getLastName().equals(personDTO.getLastName())) {

            checkName(personDTO.getFirstName(), personDTO.getLastName());
            person.setFirstName(personDTO.getFirstName());
            person.setLastName(personDTO.getLastName());
        }

        personRepository.save(person);
    }




    private void checkIfCanBeGuardian(Person person) {
        if (person== null){
            return;

        }
        if (ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) < 18) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("The person with id [%s] cannot be a guardian because of age ", person.getId()));
        }

        if (person.getGuardianId() != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("The person with id [%s] cannot be a guardian because it also has a guardian ",
                            person.getId()));
        }

    }

    private void checkPhoneNumber(String phoneNumber) {
        if(personRepository.existsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with phone number [%s] already exists", phoneNumber)
            );
        }
    }
    @Nullable
    private Person getPersonByIdOrThrow(Long id) {
        if (id == null) {
            return null;
        }

        return personRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No person with id [%s] found", id))
        );
    }
    private void checkEmail(String email) {
        if (personRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with email [%s] already exists", email)
            );
        }
    }


    private void checkName(String firstName, String lastName) {
        if (personRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Person with name [%s] already exists", firstName + " " + lastName));
        }
    }



    @Override
    @Transactional
    public PersonResponseDTO changeGuardian(ChangeGuardianRequestDTO changeGuardianRequestDTO) {
        var guardianId = changeGuardianRequestDTO.getFromGuardian();
        var fromGuardian = getPersonByIdOrThrow(guardianId);

        var existingChildren = getChildren(fromGuardian);
        var dtoChildren = changeGuardianRequestDTO.getChildrenIds();
        var toGuardian = getPersonByIdOrThrow(changeGuardianRequestDTO.getToGuardian());

        checkIfCanBeGuardian(toGuardian);
        checkIfChildrenMatch(guardianId, existingChildren, dtoChildren);

        var childrenToMove = personRepository.findAllByIdIsIn(dtoChildren);

        childrenToMove
                .forEach(child -> {
                    child.setGuardianId(toGuardian.getId());
                    child.setCity(toGuardian.getCity());
                });

        personRepository.saveAll(childrenToMove);
        var areaId = getAreaId(toGuardian.getCity());
        var allChildren = getChildren(toGuardian);
        var cityId = getCityId(toGuardian);

        return Convertes.convertPersonIntoResponseDTO(toGuardian, areaId, null, allChildren, cityId);
    }


    private void checkIfChildrenMatch(Long guardianId,
                                      List<Person> existingChildren,
                                      List<Long> childrenIdsToCheck) {


        if (existingChildren.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("The person with id [%s] has no children! ", guardianId));
        }

        List<Long> existingIds = existingChildren
                .stream()
                .map(Person::getId)
                .toList();

        List<Long> temp = childrenIdsToCheck
                .stream()
                .filter(x -> !existingIds.contains(x))
                .toList();

        if (!temp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("People with id's %s dont belong to person with id ", temp) +
                            String.format("[%s]", guardianId));
        }
    }
    @Override
    public PersonResponseDTO getPersonById(Long id) {
        var person = getPersonByIdOrThrow(id);
        return Convertes.convertPersonIntoResponseDTO(person,
                getAreaId(person.getCity()),
                getPersonByIdOrThrow(person.getGuardianId()),
                getChildren(person),
                getCityId(person));
    }

    @Override
    public PersonResponseDTO getPersonByEmail(String email) {
        var person = findPersonByEmailOrThrow(email);
        return Convertes.convertPersonIntoResponseDTO(person,
                getAreaId(person.getCity()),
                getPersonByIdOrThrow(person.getGuardianId()),
                getChildren(person),
                getCityId(person));
    }

    private Person findPersonByEmailOrThrow(String email) {
        return  personRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("There is no person with email [%s]", email)
                ));
    }
    @Override
    public PersonResponseDTO moveToAnotherCity(ChangeCityRequestDTO cityDTO) {
        var person = getPersonByIdOrThrow(cityDTO.getPersonId());

        if (person.getGuardianId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A person having a guardian - cannot be moved alone"
            );
        }

        checkIfFromCityMatch(person.getCity(), cityDTO.getFromCityId());
        var city = checkIfToCityMatch(cityDTO.getToCityId());

        var children = getChildren(person);
        children.forEach(child -> child.setCity(city));
        personRepository.saveAll(children);

        person.setCity(city);
        personRepository.save(person);

        return Convertes.convertPersonIntoResponseDTO(person, getAreaId(city), null, children, city.getId());
    }

    private City checkIfToCityMatch(Long cityToMatchId) {
        return cityRepository.findById(cityToMatchId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("[toCity]: The city with id [%s] doesn't exist! ", cityToMatchId)));
    }

    private void checkIfFromCityMatch(City city, Long cityToCheckId) {
        if (city != null && cityToCheckId == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("[fromCity] parameter doesn't match! The person lives in the city with id [%s]"
                            , city.getId()));
        }

        if (city == null && cityToCheckId != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("[formCity]: The person doesn't live in the city with id [%s], " +
                            "but in the city with id [null]", cityToCheckId));
        }

        if (city != null && !city.getId().equals(cityToCheckId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("[fromCity]: The person lives in the city with id [%s], ", city.getId()) +
                            String.format("but not in the city with id [%s]. ", cityToCheckId));
        }
    }

    private Long getCityId(Person person) {
        return person.getCity() == null ? null : person.getCity().getId();
    }


    private List<Person> getChildren(Person person) {
        Long id = person.getId();
        return personRepository.findAllByGuardianId(id);
    }

   private Long getAreaId(City city){
        if (city==null){
            return null;
        }
        return city.getArea().getId();
   }

}
