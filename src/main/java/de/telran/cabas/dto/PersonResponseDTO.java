package de.telran.cabas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonResponseDTO {


    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private GuardianResponseDTO guardian;

      private List<ChildResponseDTO> children;

     private Long cityId;

       private Long areaId;
}
