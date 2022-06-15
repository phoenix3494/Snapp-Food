package model;

import java.io.Serializable;

public class DiscountCode implements Serializable {

    private String Code;


    private int discountPercentage;

    public DiscountCode(String code, int discountPercentage) {
        this.Code = code;
        this.discountPercentage = discountPercentage;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

}
