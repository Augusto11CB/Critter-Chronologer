package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(final Set<EmployeeSkill> skillsRequest, final LocalDate date) {
        final DayOfWeek dayRequest = date.getDayOfWeek();

        // TODO Create a Method Named Query for this operation
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> matchEmployees = Collections.emptyList();
        for (Employee employee : allEmployees) {
            if (employee.getSkills().containsAll(skillsRequest) && employee.getDaysAvailable().contains(dayRequest)) {
                matchEmployees.add(employee);
            }
        }
        return matchEmployees;
    }

}