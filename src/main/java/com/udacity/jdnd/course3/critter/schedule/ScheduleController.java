package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private PetService petService;
    private ScheduleService scheduleService;
    private EmployeeService employeeService;

    public ScheduleController(PetService petService, ScheduleService scheduleService, EmployeeService employeeService) {
        this.petService = petService;
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody final ScheduleDTO scheduleDTO) {

        final Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        final Schedule savedSchedule = scheduleService.createSchedule(schedule);

        return convertScheduleToScheduleDTO(savedSchedule);

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable final long petId) {
        return scheduleService.getScheduleForPet(petId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable final long employeeId) {
        return scheduleService.getScheduleForEmployee(employeeId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable final long customerId) {
        return scheduleService.getScheduleForCustomer(customerId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {

        final ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getEmployees() != null) {
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        }
        if (schedule.getPets() != null) {
            scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        }

        return scheduleDTO;
    }

    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {

        final Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getEmployeeIds() != null) {

            schedule.setEmployees(scheduleDTO.getEmployeeIds().stream()
                    .map(employeeId -> employeeService.getEmployee(employeeId))
                    .collect(Collectors.toList()));
        }
        if (scheduleDTO.getPetIds() != null) {

            schedule.setPets(scheduleDTO.getPetIds().stream()
                    .map(petID -> petService.getPet(petID))
                    .collect(Collectors.toList()));
        }

        return schedule;
    }
}
