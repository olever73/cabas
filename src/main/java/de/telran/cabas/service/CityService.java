package de.telran.cabas.service;

import de.telran.cabas.dto.CityRequestDTO;
import de.telran.cabas.dto.CityResponseDTO;
import de.telran.cabas.entity.City;

import java.util.List;

public interface CityService {
    CityResponseDTO create (CityRequestDTO cityRequestDTO);

    List<CityResponseDTO> getAll();

    CityResponseDTO getByName(String name);

    CityResponseDTO getById(Long id);



}
