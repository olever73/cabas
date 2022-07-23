package de.teleran.cabas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CityRequestDTO {
    private String cityName;

    // private  Long areaId;


    /*
     @NotNull(message = "ciryName cannot be null")
    @NotBlank(message = "cityName cannot be blank")
    @Size(min = 3, max = 50, message = "cityName field must contain 3-50 chars")
    private String cityName;

    @NotNull(message = "areaId cannot be null")
    @Positive(message = "areaId can be only positive number")
    private Long areaId;
     */
}
