package de.telran.cabas.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AreaRequestDTO {
    @NotNull(message = "areaName cannot be null")
    @NotBlank(message = "areaName cannot be blank")
    @Size(min = 3, max = 50, message = "areaName must contain between 3 and 50 chars")
    private String areaName;

    @NotBlank(message = "areaCode cannot be blank")
    @NotNull(message = "areaCode cannot be null")
    @Size(min = 2, max = 2, message = "The areaCode must be 2 chars")
    private String areaCode;

}
