package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "CUSTOMER_ID")
    private long id;

    @Nationalized
    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "NOTES", length = 512)
    private String notes;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;

}
