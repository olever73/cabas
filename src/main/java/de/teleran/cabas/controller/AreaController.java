package de.teleran.cabas.controller;

import de.teleran.cabas.dto.AreaRequestDTO;
import de.teleran.cabas.entity.Area;
import de.teleran.cabas.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AreaController {

         private final AreaService areaService;
    @PostMapping ("/areas")
    public Area create(@RequestBody AreaRequestDTO request){
        return areaService.create(request);
    }

}
