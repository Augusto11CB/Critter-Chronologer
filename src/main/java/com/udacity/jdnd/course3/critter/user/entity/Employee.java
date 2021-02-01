package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;


@Data
@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "EMPLOYEE_ID")
    private long id;

    @Nationalized
    @Column(name = "NAME")
    private String name;

    // ElementCollection: Association Between a Single Entity and Non-Entity values
    // This annotation allows the persistance of Embeddables or enums
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "SKILLS", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private Set<EmployeeSkill> skills;


    @ElementCollection
    @CollectionTable(name = "DAYS_AVAILABLE", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private Set<DayOfWeek> daysAvailable;
}

