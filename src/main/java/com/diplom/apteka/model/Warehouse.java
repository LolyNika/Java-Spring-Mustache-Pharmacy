package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseID;

    @Min(value = 1, message = "Поле Цена может иметь значение не менее 1")
    @Max(value = 100000, message = "Поле Цена может иметь значение не менее 100000")
    private int price;

    @Min(value = 1, message = "Поле Количество может иметь значение не менее 1")
    @Max(value = 1000, message = "Поле Количество может иметь значение не менее 1000")
    private int stockavailability;

    @ManyToOne(optional = true
//            , fetch = FetchType.EAGER
    )
    @JoinColumn(name = "fktabletwarehouseid", nullable = true)
    private Tablet tabletwarehouse;

    @ManyToOne(optional = true
//            , fetch = FetchType.EAGER
    )
    @JoinColumn(name = "fkmedevicewarehouseid", nullable = true)
    private Medevice medevicewarehouse;

    @OneToMany(mappedBy = "warehousecart", cascade = CascadeType.ALL)
    private List<Cart> cartList;


}
