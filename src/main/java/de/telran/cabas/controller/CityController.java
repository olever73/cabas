package de.telran.cabas.controller;


import de.telran.cabas.dto.AreaRequestDTO;
import de.telran.cabas.dto.AreaWithCitiesResponseDTO;
import de.telran.cabas.dto.CityRequestDTO;
import de.telran.cabas.dto.CityResponseDTO;
import de.telran.cabas.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class CityController {

    private final CityService service;

    @PostMapping("/cities")
   public CityResponseDTO create(@RequestBody @Valid CityRequestDTO request) {
        return service.create(request);
    }
    @GetMapping("/cities")
    public List<CityResponseDTO> getAll(){
       return service.getAll();
    }
   @GetMapping("/citiey/{id}")
   public CityResponseDTO getById(@PathVariable("id")
                                     @Positive(message = "areaId can be only positive number") Long id){
        return service.getById(id);
   }


    @GetMapping("/api/cities")
    public CityResponseDTO getByName(@RequestParam(name = "cityName")
                                         @NotBlank(message = "City name cannot be blank") String cityName) {
        return service.getByName(cityName);
    }

}