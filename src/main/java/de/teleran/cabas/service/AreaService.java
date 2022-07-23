package de.teleran.cabas.service;

import de.teleran.cabas.dto.AreaRequestDTO;
import de.teleran.cabas.dto.AreaWithCitiesResponseDTO;
import de.teleran.cabas.entity.Area;

import java.util.List;

public interface AreaService {
     Area create(AreaRequestDTO request);
     List<AreaWithCitiesResponseDTO> getAll();
    AreaWithCitiesResponseDTO getByName(String name);
}
