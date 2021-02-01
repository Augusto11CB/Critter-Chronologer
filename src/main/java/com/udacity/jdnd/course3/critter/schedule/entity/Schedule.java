package com.udacity.jdnd.course3.critter.schedule.entity;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "SCHEDULE")
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "SCHEDULE_ID")
    private long id;

    @ManyToMany
    @JoinTable(
            name = "EMPLOYEE_SCHEDULE",
            joinColumns = {@JoinColumn(name = "SCHEDULE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_ID")}
    )
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "PET_SCHEDULE",
            joinColumns = {@JoinColumn(name = "SCHEDULE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PET_ID")}
    )
    private List<Pet> pets;

    @Column(name = "DATE")
    private LocalDate date;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "ACTIVITIES", joinColumns = @JoinColumn(name = "SCHEDULE_ID"))
    private Set<EmployeeSkill> activities;
}
