package de.telran.cabas.service.impl;

import de.telran.cabas.convert.Convertes;
import de.telran.cabas.dto.AreaRequestDTO;
import de.telran.cabas.dto.AreaResponseDTO;
import de.telran.cabas.dto.AreaWithCitiesResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    CityRepository cityRepository;

    @Override
    @Transactional
    public AreaResponseDTO create(AreaRequestDTO request) {

        if (areaRepository.existsByAreaName(request.getAreaName().toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Area with name [%s] already exists ", request.getAreaName()));
        }

        var area = Convertes.convertToAreaEntity(request);
        areaRepository.save(area);


        return Convertes.convertAreaToResponseDTO(area);
    }

    @Override
    public List<AreaWithCitiesResponseDTO> getAll() {
        var areas = areaRepository.findAll();
        if (areas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    " This area is not in the database ");

        }

        return areas
                .stream()
                .map(area -> {
                            var cityIds = getCityIdsByAreaId(area.getId());
                            return Convertes.convertAreaToAreaWithCitiesDTO(area, cityIds);
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public AreaWithCitiesResponseDTO getByName(String name) {
        var area = findAreaByNameOrThrow(name.toUpperCase());

        return Convertes.convertAreaToAreaWithCitiesDTO(area, getCityIdsByAreaId(area.getId()));


    }

    private Area findAreaByNameOrThrow(String name) {
        return areaRepository.findByAreaName(name.toUpperCase()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("There is no area with name [%s] ", name)
                )
        );


    }


    private List<Long> getCityIdsByAreaId(Long id) {
        return cityRepository.findAllByAreaId(id)
                .stream()
                .map(City::getId)
                .toList();
    }


}


