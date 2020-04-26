package dev.stasys.shipmentdiscount.rules;

import dev.stasys.shipmentdiscount.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static dev.stasys.shipmentdiscount.rules.YearMonthKey.formKey;

/**
 * Third L shipment via LP should be free, but only once a calendar month.
 */
public class ThirdShipmentViaLpFreeRule extends Rule {
    private final Map<String, Integer> shipmentCountByMonth = new HashMap<>();

    public ThirdShipmentViaLpFreeRule(ShipmentPrices shipmentPrices) {
        super(shipmentPrices);
    }

    @Override
    protected boolean isRuleApplicable(Transaction transaction, Discount discount, BigDecimal shippingPrice) {
        String key = formKey(transaction.date);
        shipmentCountByMonth.putIfAbsent(key, 0);
        return transaction.carrier == Carrier.LP
            && transaction.packageSize == PackageSize.L
            && shipmentCountByMonth.computeIfPresent(formKey(transaction.date), (s, integer) -> ++integer) == 3;
    }

    @Override
    protected Discount apply(Transaction transaction, Discount discount, BigDecimal shippingPrice) {
        return new Discount(new BigDecimal("0.00"), shippingPrice);
    }
}
