package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "tabletto")
public class Tabletto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tablettoid;

    private String title;

    @OneToMany(mappedBy = "tabletTo", fetch = FetchType.EAGER)
    private List<Tablet> tablets;

}
