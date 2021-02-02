package com.udacity.jdnd.course3.critter.pet.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "PET")
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue
    @Column(name = "PET_ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private PetType type;

    @Nationalized
    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer owner;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "NOTES", length = 512)
    private String notes;

}
