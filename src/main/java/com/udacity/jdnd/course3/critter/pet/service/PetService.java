package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(final Pet pet) {

        final Pet savedPet = petRepository.save(pet);
/*      TODO - Verify if it will be necessary
        Optional<Customer> ownerOp = Optional.of(pet.getOwner());
        if (ownerOp.isPresent()) {
            final Customer owner = ownerOp.get();
            Optional<Customer> customerOptional = customerRepository.findById(owner.getId());

            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                customer.addPet(pet);
                customerRepository.save(customer);
            }
        }
*/
        return savedPet;

    }

    public Optional<Pet> findPetById(long petId) {
        return petRepository.findById(petId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findAllByCustomer_Id(ownerId);
    }

}