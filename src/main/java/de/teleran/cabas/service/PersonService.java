package de.teleran.cabas.service;

import de.teleran.cabas.dto.*;

public interface PersonService {


   PersonResponseDTO create(PersonRequestDTO personDTO);

    void update (Long id, UpdatePersonRequestDTO personDTO);

    PersonResponseDTO changeGuardian(Long guardianId, ChangeGuardianRequestDTO changeGuardianRequestDTO);

    PersonResponseDTO getPersonById(Long id);

    PersonResponseDTO getPersonByEmail(String email);

    PersonResponseDTO moveToAnotherCity(ChangeCityRequestDTO cityDTO);

}
