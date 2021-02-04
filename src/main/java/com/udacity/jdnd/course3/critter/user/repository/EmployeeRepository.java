package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> getAllByDaysAvailableContains(DayOfWeek dayOfWeek);
/*
    @Query(
            value = "SELECT * FROM Employees e " +
                    "INNER JOIN employee_days_available eda on eda" +
                    ".employee_id = e.id " +
                    "INNER JOIN employee_skills es on es.employee_id = e.id " +
                    "WHERE eda.day IN :daysAvailable AND es.skill IN " +
                    ":employeeSkills",
            nativeQuery = true
    )
    List<Employee> findAllBySkillsAndDaysAvailable(
            @Param("daysAvailable") Set<String> daysAvailable,
            @Param("employeeSkills") Set<String> employeeSkills);
*/

}
