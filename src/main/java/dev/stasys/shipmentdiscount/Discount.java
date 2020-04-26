package dev.stasys.shipmentdiscount;

import java.math.BigDecimal;

public class Discount {
    public final BigDecimal shipmentPrice;
    public final BigDecimal appliedDiscount;

    public Discount(BigDecimal shipmentPrice, BigDecimal appliedDiscount) {
        this.shipmentPrice = shipmentPrice;
        this.appliedDiscount = appliedDiscount;
    }

    public Discount(BigDecimal shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
        this.appliedDiscount = new BigDecimal("0.00");
    }

    @Override
    public String toString() {
        return shipmentPrice + " " + ((appliedDiscount.equals(new BigDecimal("0.00"))) ? "-" : appliedDiscount);
    }
}
