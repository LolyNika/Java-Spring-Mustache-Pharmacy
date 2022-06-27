package com.diplom.apteka.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
//@Getter
//@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseID;

    private String product;
    private int discount;
    private int sum;
    private String datepurchase;

    @OneToMany(mappedBy = "purchasecart")
    private List<Cart> cartList;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fkemployeeid")
    private Employee employee;

    public Purchase(int medeviceID, String product, int discount, int sum) {
        this.purchaseID = medeviceID;
        this.product = product;
        this.discount = discount;
        this.sum = sum;
    }

    public Purchase(String product, int discount, int sum, String datepurchase) {
        this.product = product;
        this.discount = discount;
        this.sum = sum;
        this.datepurchase = datepurchase;
    }

    public Purchase(String product, int discount, int sum) {
        this.product = product;
        this.discount = discount;
        this.sum = sum;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public String getProduct() {
        return product;
    }

    public int getDiscount() {
        return discount;
    }

    public int getSum() {
        return sum;
    }

    public String getDatepurchase() {
        return datepurchase;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setDatepurchase(String datepurchase) {
        this.datepurchase = datepurchase;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
