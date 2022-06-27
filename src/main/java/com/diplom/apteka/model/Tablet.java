package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
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
public class Tablet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tabletID;

    @NotBlank(message = "Поле Название не должно быть пустым")
    @Size(min=3, max=50, message = "Поле Название должно иметь кол-во букв 3-50")
//    @NotNull
//    @Valid
    private String title;

    @NotBlank(message = "Поле Описание не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Описание должно иметь кол-во букв 3-500")
    private String description;

    @NotBlank(message = "Поле Фармокологическая группа не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Фармокологическая должно иметь кол-во букв 3-500")
    private String pharmgroup;

    @NotBlank(message = "Поле Показания не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Показания должно иметь кол-во букв 3-500")
    private String indications;

    @NotBlank(message = "Поле Способ применения и дозировки не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Способ применения должно иметь кол-во букв 3-500")
    private String methodapplicanddose;

    @NotBlank(message = "Поле Побочные эффекты не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Побочные эффекты должно иметь кол-во букв 3-500")
    private String sideeffects;

    @NotBlank(message = "Поле Противопоказания не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Противопоказания должно иметь кол-во букв 3-500")
    private String contraindications;

    @NotBlank(message = "Поле Взаимодействие с другими препаратами не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Взаимодействие с другими препаратами должно иметь кол-во букв 3-500")
    private String interaction;

    @NotBlank(message = "Поле Меры предосторожности не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Меры предосторожности должно иметь кол-во букв 3-500")
    private String precautions;

    @NotBlank(message = "Поле Передозаровка не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Передозаровка должно иметь кол-во букв 3-500")
    private String overdose;

    @NotBlank(message = "Поле Упаковка не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Упаковка должно иметь кол-во букв 3-500")
    private String packaging;

    @NotBlank(message = "Поле Условия хранения не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Условия хранения должно иметь кол-во букв 3-500")
    private String storeconditions;

//    @NotBlank(message = "Поле Срок годности (лет) не должно быть пустым")
//    @Positive
    @Min(value = 1, message = "Поле Срок годности (лет) может иметь значение не менее 1")
    @Max(value = 10, message = "Поле Срок годности (лет) может иметь значение не менее 10")
    private Integer expdate;

    @NotBlank(message = "Поле Условия отпуска не должно быть пустым")
    @Size(min=3, max=500, message = "Поле Условия отпуска должно иметь кол-во букв 3-500")
    private String conditionsvacation;

//    @NotBlank(message = "Поле Возможность изготовления препарата не должно быть пустым")
    private String making;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fktabletid", nullable = false)
    private Tabletto tabletTo;

    @OneToMany(mappedBy = "tabletfabric", cascade = CascadeType.ALL)
    private List<Fabrications> fabrications;

    @OneToMany(mappedBy = "tabletbuying", cascade = CascadeType.ALL)
    private List<Buying> buyingstablets;

    @OneToMany(mappedBy = "tabletwarehouse", cascade = CascadeType.ALL)
    private List<Warehouse> warehousetablet;

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation, String making, Tabletto tabletTo, List<Fabrications> fabrications, List<Buying> buyingstablets, List<Warehouse> warehousetablet) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
        this.making = making;
        this.tabletTo = tabletTo;
        this.fabrications = fabrications;
        this.buyingstablets = buyingstablets;
        this.warehousetablet = warehousetablet;
    }

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation, String making, Tabletto tabletTo, List<Fabrications> fabrications, List<Buying> buyingstablets) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
        this.making = making;
        this.tabletTo = tabletTo;
        this.fabrications = fabrications;
        this.buyingstablets = buyingstablets;
    }

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation, String making, Tabletto tabletTo, List<Fabrications> fabrications) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
        this.making = making;
        this.tabletTo = tabletTo;
        this.fabrications = fabrications;
    }

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation, Tabletto tabletTo) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
        this.tabletTo = tabletTo;
    }

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation, String making) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
        this.making = making;
    }

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation, String making, Tabletto tabletTo) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
        this.making = making;
        this.tabletTo = tabletTo;
    }

    public Tablet(String title, String description, String pharmgroup, String indications, String methodapplicanddose, String sideeffects, String contraindications, String interaction, String precautions, String overdose, String packaging, String storeconditions, Integer expdate, String conditionsvacation) {
        this.title = title;
        this.description = description;
        this.pharmgroup = pharmgroup;
        this.indications = indications;
        this.methodapplicanddose = methodapplicanddose;
        this.sideeffects = sideeffects;
        this.contraindications = contraindications;
        this.interaction = interaction;
        this.precautions = precautions;
        this.overdose = overdose;
        this.packaging = packaging;
        this.storeconditions = storeconditions;
        this.expdate = expdate;
        this.conditionsvacation = conditionsvacation;
    }


}
