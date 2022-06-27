package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table (name = "buying")
public class Buying implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyingID;

    @Size(min=3, max=50, message = "Поле Название должно иметь кол-во букв 3-50")
    private String title;

    @Size(min=0, max=100, message = "Поле Комментарий должно иметь кол-во букв не более 100")
    private String comment;
    private String iscomplited;
    private String orderdate;
    private String buyingorganiz;

    @Min(value = 1, message = "Поле Цена может иметь значение не менее 1")
    @Max(value = 100000, message = "Поле Цена может иметь значение не менее 100000")
//    @NotNull (message = "Поле Цена не должно быть пустым")
    private int price;

    @Min(value = 1, message = "Поле Количество может иметь значение не менее 1")
    @Max(value = 1000, message = "Поле Количество может иметь значение не менее 1000")
//    @NotNull (message = "Поле Количество не должно быть пустым")
    private int amount;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fktabletbuyid", nullable = true)
    private Tablet tabletbuying;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fkmedevicebuyid", nullable = true)
    private Medevice medevicebuying;

}
