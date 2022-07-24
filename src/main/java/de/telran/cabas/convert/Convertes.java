package de.telran.cabas.convert;

import de.telran.cabas.dto.*;
import de.telran.cabas.entity.Area;
import de.telran.cabas.entity.City;
import de.telran.cabas.entity.Person;
import de.telran.cabas.entity.types.SeverityType;

import java.util.List;
import java.util.stream.Collectors;

public class Convertes {

    public static AreaResponseDTO convertAreaToResponseDTO(Area area) {
        return AreaResponseDTO
                .builder()
                .areaName(area.getAreaName())
                .areaCode(area.getAreaCode())
                .id(area.getId())
                .severityType(area.getSeverityType())
                .build();
    }

    public static AreaWithCitiesResponseDTO convertAreaToAreaWithCitiesDTO(Area area, List<Long> cityIds) {
        return AreaWithCitiesResponseDTO
                .builder()
                .areaName(area.getAreaName())
                .areaId(area.getId())
                .areaCode(area.getAreaCode())
                .severityType(area.getSeverityType())
                .cityIds(cityIds)
                .build();
    }

    public static Area convertToAreaEntity(AreaRequestDTO request) {

        return Area.builder()
                .areaName(request.getAreaName().toUpperCase())
                .areaCode(request.getAreaCode().toUpperCase())
                .severityType(SeverityType.GREEN)
                .build();
    }

    public static CityResponseDTO convertCityToResponseDTO(City city) {

        return CityResponseDTO
                .builder()
                .cityName(city.getCityName())
                .cityId(city.getId())
                .areaId(city.getArea().getId())

                .severityType(city.getArea().getSeverityType())
                .build();
    }


    public static PersonResponseDTO convertPersonIntoResponseDTO(Person person, Long areaId, Person guardian, List<Person> children, Long cityId) {
        return PersonResponseDTO
                .builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .guardian(convertPersonToGuardianDTO(guardian))
                .children(convertListPersonIntoListChildDTO(children))
                .cityId(cityId)
                .areaId(areaId)
                .build();
    }

    public static List<ChildResponseDTO> convertListPersonIntoListChildDTO(List<Person> people) {
        return people
                .stream()
                .map(Convertes::convertPersonIntoChildResponseDTO)
                .toList();
    }


    private static GuardianResponseDTO convertPersonToGuardianDTO(Person person) {
    if(person==null){
        return null;
    }
    return GuardianResponseDTO.builder()
            .firstName(person.getFirstName())
            .lastName(person.getLastName())
            .email(person.getEmail())
            .phoneNumber(person.getPhoneNumber())
            .build();
    }


    public static ChildResponseDTO convertPersonIntoChildResponseDTO(Person person){

        return ChildResponseDTO
                .builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .phoneNumber(person.getPhoneNumber())
                .build();
    }

}
