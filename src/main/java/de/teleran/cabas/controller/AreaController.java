package de.teleran.cabas.controller;

import de.teleran.cabas.dto.AreaRequestDTO;
import de.teleran.cabas.dto.AreaResponseDTO;
import de.teleran.cabas.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AreaController {
    @Autowired
         private AreaService areaService;
    @PostMapping ("/areas")
    public AreaResponseDTO create(@RequestBody AreaRequestDTO request){
        return areaService.create(request);
    }

}
