package de.telran.cabas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonRequestDTO {



    @NotBlank(message = "firstName can't be blank")
    @Size(min = 2, max = 50, message = "fistName length must be between 2 and 50 chars")
    private String firstName;

    @NotBlank(message = "lastName can't be blank")
    @Size(min = 2, max = 50, message = "lastName length must be between 2 and 50 chars")
    private String lastName;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email form is incorrect")
    @Size(min = 10, max = 50, message = "email length must be between 10 and 50 chars")
    private String email;

    @NotNull(message = "dateOfBirth cannot be null")
    @Past(message = "dateOfBirth must be at the past from now")
    private LocalDate dateOfBirth;



    @NotBlank(message = "phoneNumber cannot be blank")
    @Pattern(regexp = "\\+\\d{2}( )\\d{3}( )\\d{8}", message = "phoneNumber must be like (+49 123 99456789)")
    private String phoneNumber;

    @Positive(message = "guardianId can be null or positive number")
    private Long guardianId;

}
