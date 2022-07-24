package de.telran.cabas.controller;

import de.telran.cabas.dto.AreaRequestDTO;
import de.telran.cabas.dto.AreaResponseDTO;
import de.telran.cabas.dto.AreaWithCitiesResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AreaController {

    private final AreaService service;

    @PostMapping("/areas")
    public AreaResponseDTO create(@RequestBody @Valid AreaRequestDTO request) {
        return service.create(request);
    }

    @GetMapping("/api/areasAll")
    public List<AreaWithCitiesResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/api/areas")
    public AreaWithCitiesResponseDTO getByName(@RequestParam(name = "areaName")
                                               @NotBlank(message = "areaName cannot be blank") String areaName) {
     return service.getByName(areaName);
    }

}
