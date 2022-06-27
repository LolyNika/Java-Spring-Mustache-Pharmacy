package com.diplom.apteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int cartID;

        @Min(value = 1, message = "Поле Количество может иметь значение не менее 1")
        @Max(value = 100, message = "Поле Количество может иметь значение не менее 100")
        private int amount;

        @Column(name = "idtransaction")
        private Long idtransaction;

        @Column(name = "isactive")
        private Integer isactive;

        @ManyToOne(optional = true)
        @JoinColumn(name = "fkwarehousecartid", nullable = true)
        private Warehouse warehousecart;

        @ManyToOne(optional = true)
        @JoinColumn(name = "fkpurchasecartid", nullable = true)
        private Purchase purchasecart;

        public Cart(int cartID, int amount, Long idtransaction, Integer isactive) {
                this.cartID = cartID;
                this.amount = amount;
                this.idtransaction = idtransaction;
                this.isactive = isactive;
        }

        public Cart(int amount, Long idtransaction, Integer isactive) {
                this.amount = amount;
                this.idtransaction = idtransaction;
                this.isactive = isactive;
        }

        public Cart(int amount, Long idtransaction, Integer isactive, Warehouse warehousecart
                , Purchase purchasecart
        ) {
                this.amount = amount;
                this.idtransaction = idtransaction;
                this.isactive = isactive;
                this.warehousecart = warehousecart;
                this.purchasecart = purchasecart;
        }
}
