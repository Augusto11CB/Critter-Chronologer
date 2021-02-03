package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private PetRepository petRepository;

    public ScheduleService(final ScheduleRepository scheduleRepository, final PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
    }


    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        return scheduleRepository.findAllByPets_Id(petId);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {

        return petRepository.findAllByCustomer_Id(customerId)
                .stream()
                .flatMap(pet -> scheduleRepository.findAllByPets_Id(pet.getId()).stream())
                .collect(Collectors.toList());
    }

}
