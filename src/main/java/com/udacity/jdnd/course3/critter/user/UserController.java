package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;
    private EmployeeService employeeService;
    private PetService petService;

    public UserController(final CustomerService customerService, final EmployeeService employeeService, final PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody final CustomerDTO customerDTO) {

        final Customer customer = this.toEntity(customerDTO);
        final Customer savedCustomer = customerService.saveCustomer(customer);

        return this.toDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {

        final List<Customer> customers = customerService.getAllCustomers();

        return customers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable final long petId) {

        final Customer ownerPet = customerService.getOwnerByPet(petId);

        return this.toDTO(ownerPet);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody final EmployeeDTO employeeDTO) {

        final Employee employee = this.toEntity(employeeDTO);

        final Employee savedEmployee = employeeService.saveEmployee(employee);

        return this.toDTO(savedEmployee);

    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable final long employeeId) {

        final Employee employee = employeeService.getEmployee(employeeId);

        return this.toDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody final Set<DayOfWeek> daysAvailable, @PathVariable final long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody @NotNull EmployeeRequestDTO employeeDTO) {
        final List<Employee> employees = employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate());

        if (!employees.isEmpty()) {
            return employees.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private Customer toEntity(@NotNull final CustomerDTO customerDTO) {

        final Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        if (Objects.nonNull(customerDTO.getPetIds())) {

            final List<Pet> pets = customerDTO.getPetIds()
                    .stream()
                    .map(petID -> petService.getPet(petID))
                    .collect(Collectors.toList());
            customer.setPets(pets);
        }
        return customer;
    }

    private CustomerDTO toDTO(final Customer customer) {

        final CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if (Objects.nonNull(customer.getPets())) {

            final List<Long> petIds = customer.getPets().stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());

            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    private Employee toEntity(final EmployeeDTO employeeDTO) {

        final Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }

    private EmployeeDTO toDTO(final Employee employee) {

        final EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }
}
