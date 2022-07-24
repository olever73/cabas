package de.telran.cabas.dto;

import de.telran.cabas.entity.types.SeverityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CityResponseDTO {
    private String cityName;
    private SeverityType severityType;
    private Long cityId;
    private Long areaId;
}
