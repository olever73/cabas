package de.teleran.cabas.service;

import de.teleran.cabas.dto.CityRequestDTO;
import de.teleran.cabas.dto.CityResponseDTO;
import de.teleran.cabas.entity.City;

import java.util.List;

public interface CityService {
    City create (CityRequestDTO cityRequestDTO);

    List<CityResponseDTO> getAll();

    CityResponseDTO getByName(String name);

    CityResponseDTO getById(Long id);



}
