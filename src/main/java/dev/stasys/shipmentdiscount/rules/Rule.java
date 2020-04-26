package dev.stasys.shipmentdiscount.rules;

import dev.stasys.shipmentdiscount.Discount;
import dev.stasys.shipmentdiscount.ShipmentPrices;
import dev.stasys.shipmentdiscount.Transaction;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public abstract class Rule implements BiFunction<Transaction, Discount, Discount> {
    protected final ShipmentPrices shipmentPrices;

    public Rule(ShipmentPrices shipmentPrices) {
        this.shipmentPrices = shipmentPrices;
    }

    @Override
    public Discount apply(Transaction transaction, Discount discount) {
        BigDecimal shipmentPrice = shipmentPrices.priceFor(transaction.carrier, transaction.packageSize);
        if (isRuleApplicable(transaction, discount, shipmentPrice)) {
            return apply(transaction, discount, shipmentPrice);
        }
        postProcessNonAppliedRule(transaction, discount, shipmentPrice);
        return discount;
    }

    protected void postProcessNonAppliedRule(Transaction transaction, Discount discount, BigDecimal shipmentPrice) {
        // Override to do some processing for non-applicable rule
    }

    protected abstract boolean isRuleApplicable(Transaction transaction, Discount discount, BigDecimal shipmentPrice);
    protected abstract Discount apply(Transaction transaction, Discount discount, BigDecimal shipmentPrice);
}
