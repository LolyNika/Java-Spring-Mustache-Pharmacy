package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleID;

    @Column(name = "authority")
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<Employee> employee_role;

}
