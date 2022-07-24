package de.telran.cabas.service;

import de.telran.cabas.dto.AreaRequestDTO;
import de.telran.cabas.dto.AreaResponseDTO;
import de.telran.cabas.dto.AreaWithCitiesResponseDTO;
import de.telran.cabas.entity.Area;

import java.util.List;

public interface AreaService {
     AreaResponseDTO create(AreaRequestDTO request);
     List<AreaWithCitiesResponseDTO> getAll();
    AreaWithCitiesResponseDTO getByName(String name);
}
