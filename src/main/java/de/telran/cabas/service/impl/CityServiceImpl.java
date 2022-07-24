package de.telran.cabas.service.impl;

import de.telran.cabas.convert.Convertes;
import de.telran.cabas.dto.CityRequestDTO;
import de.telran.cabas.dto.CityResponseDTO;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.repository.AreaRepository;
import de.telran.cabas.repository.CityRepository;
import de.telran.cabas.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    CityRepository cityRepository;

    AreaRepository areaRepository;

    @Override
    public CityResponseDTO create(CityRequestDTO cityRequestDTO) {

        if (cityRepository.existsByCityName(cityRequestDTO.getCityName().toUpperCase()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("City with name [%s] already exists ", cityRequestDTO.getCityName()));
        var area = findAreaOrThrow(cityRequestDTO.getAreaId());
        var city = City.builder()
                .cityName(cityRequestDTO.getCityName().toUpperCase())
                .build();

        cityRepository.save(city);
        return Convertes.convertCityToResponseDTO(city);
    }

    private Area findAreaOrThrow(Long areaId) {
        return areaRepository.findById(areaId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("There is no area with areaId [%s] ", areaId)
                )
        );

    }


    @Override
    public List<CityResponseDTO> getAll() {

        return cityRepository.findAll()
                .stream()
                .map(Convertes::convertCityToResponseDTO)
                .toList();
    }


    @Override
    public CityResponseDTO getByName(String name) {
        return Convertes.convertCityToResponseDTO(findCityByNameOrThrow(name.toUpperCase()));
    }

    private City findCityByNameOrThrow(String name) {
        var city = cityRepository.findByCityName(name.toUpperCase());
        if (city == null) {
           throw  new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("No city [%s] ", name)
            );
        }
        return city;

    }


    @Override
    public CityResponseDTO getById(Long id) {

        var city = cityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("City with id[%s] doesn't exist!", id)
                )
        );
        return Convertes.convertCityToResponseDTO(city);
    }
}
