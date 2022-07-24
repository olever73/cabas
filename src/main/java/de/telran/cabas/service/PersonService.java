package de.telran.cabas.service;

import de.telran.cabas.dto.*;

public interface PersonService {


   PersonResponseDTO create(PersonRequestDTO personDTO);

    void update (Long id, UpdatePersonRequestDTO personDTO);

    PersonResponseDTO changeGuardian( ChangeGuardianRequestDTO changeGuardianRequestDTO);

    PersonResponseDTO getPersonById(Long id);

    PersonResponseDTO getPersonByEmail(String email);

    PersonResponseDTO moveToAnotherCity(ChangeCityRequestDTO cityDTO);

}
