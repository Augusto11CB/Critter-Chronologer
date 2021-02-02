package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        final Pet pet = convertPetDTOToPet(petDTO);
        final Pet savedPet = petService.savePet(pet);

        return convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        final Pet pet = petService.getPet(petId);

        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        final List<Pet> pets = petService.getPets();
        return pets
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        final List<Pet> pets = petService.getPetsByOwner(ownerId);

        return pets
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }


    private Pet convertPetDTOToPet(PetDTO petDTO) {

//      final customerService.getCustomerById(petDTO.getOwnerId()); // TODO
        final Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
//      pet.setOwner();
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        final PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }
}
