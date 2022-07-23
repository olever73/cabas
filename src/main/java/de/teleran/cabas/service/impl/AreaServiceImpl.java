package de.teleran.cabas.service.impl;

import de.teleran.cabas.dto.AreaRequestDTO;
import de.teleran.cabas.dto.AreaWithCitiesResponseDTO;
import de.teleran.cabas.entity.Area;
import de.teleran.cabas.entity.City;
import de.teleran.cabas.repository.AreaRepository;
import de.teleran.cabas.repository.CityRepository;
import de.teleran.cabas.service.AreaService;
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
    public Area create(AreaRequestDTO request) {

        if(areaRepository.existsByAreaName(request.getAreaName().toUpperCase()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Area with name [%s] already exists ", request.getAreaName()));

       var area = Area.builder()
               .areaName(request.getAreaName().toUpperCase())
               .build();
       return areaRepository.save(area);

    }

    @Override
    public List<AreaWithCitiesResponseDTO> getAll() {
          var areas =areaRepository.findAll();
          if(areas.isEmpty()){
              throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                      " This area is not in the database " );

          }

        return areas
                .stream()
                .map(area -> {
                            var cityIds = getCityIdsByAreaId(area.getId());
                            return convertAreaToAreaWithCitiesDTO(area, cityIds);
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public AreaWithCitiesResponseDTO getByName(String name) {
        var  area= findAreaByName(name.toUpperCase());

        return convertAreaToAreaWithCitiesDTO(area,getCityIdsByAreaId(area.getId()));




    }
    private Area findAreaByName(String name){
        var area = areaRepository.findByAreaName(name.toUpperCase());

        if(area == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no area with name [%s] ", name));
        }
        return null;
    }


    private List<Long> getCityIdsByAreaId(Long id) {
        return cityRepository.findAllByAreaId(id)
                .stream()
                .map(City::getId)
                .collect(Collectors.toList());
    }



        public  AreaWithCitiesResponseDTO convertAreaToAreaWithCitiesDTO(Area area, List<Long> cityIds) {
            return AreaWithCitiesResponseDTO
                    .builder()
                    .areaName(area.getAreaName())
                    .areaId(area.getId())
                    .cityIds(cityIds)
                    .build();
        }
    }


