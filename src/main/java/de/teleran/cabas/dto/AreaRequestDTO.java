package de.teleran.cabas.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AreaRequestDTO {

    private String areaName;

    //private String areaCode;
/*


    @Size(min = 3, max = 50, message = "areaName must contain between 3 and 50 chars")
    private String areaName;

    @NotBlank(message = "areaCode cannot be blank")
    @NotNull(message = "areaCode cannot be null")
    @Size(min = 2, max = 2, message = "The areaCode must be 2 chars")
    private String areaCode;
}

 */

}
