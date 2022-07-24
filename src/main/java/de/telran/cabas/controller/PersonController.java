package de.telran.cabas.controller;


import de.telran.cabas.dto.*;
import de.telran.cabas.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class PersonController {
    private final PersonService service;

    @PostMapping("/people")
    public PersonResponseDTO create(@RequestBody @Valid PersonRequestDTO request) {
        return service.create(request);
    }

    @PutMapping("/people{id}")
    public void update(@PathVariable("id")
                       @Positive(message = "Id can be only positive number") Long id,
                       @RequestBody @Valid UpdatePersonRequestDTO personDTO) {
        service.update(id, personDTO);
    }
    @PatchMapping("/people/guardians/changeGuardian")
    private PersonResponseDTO changeGuardian(@RequestBody @Valid ChangeGuardianRequestDTO changeGuardianRequestDTO){
        return service.changeGuardian(changeGuardianRequestDTO);
    }
    @GetMapping("/people/{id}")
    public PersonResponseDTO getById(@PathVariable("id")
                                     @Positive(message = "Id in URL must be positive") Long id) {
        return service.getPersonById(id);
    }

    @GetMapping("/people")
    public PersonResponseDTO getByEmail(@RequestParam("email")
                                        @NotNull(message = "email cannot be null")
                                        @Email(message = "email form is incorrect") String email) {
        return service.getPersonByEmail(email);
    }

    @PostMapping("/people/move")
    public PersonResponseDTO moveToAnotherCity(@RequestBody @Valid ChangeCityRequestDTO changeDTO) {
        return service.moveToAnotherCity(changeDTO);
    }


}
