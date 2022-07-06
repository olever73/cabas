package de.teleran.cabas.service;

import de.teleran.cabas.dto.AreaRequestDTO;
import de.teleran.cabas.dto.AreaResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface AreaService {
     AreaResponseDTO create(AreaRequestDTO request);
}
