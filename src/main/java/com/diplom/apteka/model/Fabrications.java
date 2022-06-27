package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "fabrications")
public class Fabrications implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fabricationsID;

    @Size(min=3, max=50, message = "Поле Название должно иметь кол-во букв 3-50")
    private String title;

    @Size(min=0, max=100, message = "Поле Комментарий должно иметь кол-во букв не более 100")
    private String comment;
    private String iscomplited;
    private String orderdate;

    @Size(min=3, max=100, message = "Поле ФИО заказчика должно иметь кол-во букв 3-100")
    private String fullnamecustomer;

    @Size(min=3, max=100, message = "Поле Контакты должно иметь кол-во букв 3-100")
    private String contactscustomer;

    @Min(value = 1, message = "Поле Цена может иметь значение не менее 1")
    @Max(value = 10000, message = "Поле Цена может иметь значение не менее 10000")
    private int price;

    @Min(value = 1, message = "Поле Количеств может иметь значение не менее 1")
    @Max(value = 100, message = "Поле Количество может иметь значение не менее 100")
    private int amount;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fktabletidfabric", nullable = true)
    private Tablet tabletfabric;

}
