package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medevice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medeviceID;

    @NotBlank(message = "Поле Название не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Название должно иметь кол-во букв 3-50")
    private String title;

    @NotBlank(message = "Поле Описание не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Описание должно иметь кол-во букв 3-50")
    private String description;

    @NotBlank(message = "Поле Производитель не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Производитель должно иметь кол-во букв 3-50")
    private String manufacturer;

    @NotBlank(message = "Поле Меры предосторожности не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Меры предосторожности должно иметь кол-во букв 3-50")
    private String precautions;

    @NotBlank(message = "Поле Упаковка не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Упаковка должно иметь кол-во букв 3-50")
    private String packaging;

    @NotBlank(message = "Поле Условия хранения не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Условия хранения должно иметь кол-во букв 3-50")
    private String storageconditions;

    @Min(value = 1, message = "Поле Срок годности (лет) может иметь значение не менее 1")
    @Max(value = 10, message = "Поле Срок годности (лет) может иметь значение не менее 10")
    private String expdate;

    @NotBlank(message = "Поле Условия отпуска не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Условия отпуска должно иметь кол-во букв 3-50")
    private String conditionsvacations;

    @OneToMany(mappedBy = "medevicebuying", cascade = CascadeType.ALL)
    private List<Buying> buyingsmedevices;

    @OneToMany(mappedBy = "medevicewarehouse", cascade = CascadeType.ALL)
    private List<Warehouse> warehousemedevice;

    public Medevice(String title, String description, String manufacturer, String precautions, String packaging, String storageconditions, String expdate, String conditionsvacations, List<Buying> buyingsmedevices, List<Warehouse> warehousemedevice) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.precautions = precautions;
        this.packaging = packaging;
        this.storageconditions = storageconditions;
        this.expdate = expdate;
        this.conditionsvacations = conditionsvacations;
        this.buyingsmedevices = buyingsmedevices;
        this.warehousemedevice = warehousemedevice;
    }

    public Medevice(String title, String description, String manufacturer, String precautions, String packaging, String storageconditions, String expdate, String conditionsvacations, List<Buying> buyingsmedevices) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.precautions = precautions;
        this.packaging = packaging;
        this.storageconditions = storageconditions;
        this.expdate = expdate;
        this.conditionsvacations = conditionsvacations;
        this.buyingsmedevices = buyingsmedevices;
    }

}

