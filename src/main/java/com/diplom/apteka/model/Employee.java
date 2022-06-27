package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
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
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long employeeID;

    @Size(min=3, max=50, message = "Поле Логин должно иметь кол-во букв 3-50")
    private String username;

    @Size(min=3, max=500, message = "Поле Пароль должно иметь кол-во букв 3-50")
    private String password;

    @Size(min=3, max=50, message = "Поле Фамилия должно иметь кол-во букв 3-50")
    private String surname;

    @Size(min=3, max=50, message = "Поле Имя должно иметь кол-во букв 3-50")
    private String name;

    @Size(min=3, max=50, message = "Поле Отчество должно иметь кол-во букв 3-50")
    private String patronymic;

    @Size(min=3, max=50, message = "Поле Должность должно иметь кол-во букв 3-50")
    private String position;

    // Параметр optional которой говорит JPA, является ли значение в этом поле обязательным или нет
    // Со стороны владельца к аннотации @OneToOne добавляется так же параметр cascade,  который говорит JPA, что делать с владеемыми объектами при операциях над владельцем
    // Размеется, CascadeType.ALL это не единственный возможный тип каскадирования - https://easyjava.ru/data/jpa/jpa-entitymanager-upravlyaem-sushhnostyami/
    @ManyToOne(optional = true)
    @JoinColumn(name = "roleID")
    private Role role;

    @OneToMany(mappedBy = "purchaseID", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Purchase> purchases;

    public Employee(String username, String password, String surname, String name, String patronymic, String position, Role role) {
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
        this.role = role;
    }

    public Employee(String username, String password, String surname, String name, String patronymic, String position) {
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
    }
}
