package dev.stasys.shipmentdiscount.rules;

import dev.stasys.shipmentdiscount.Discount;
import dev.stasys.shipmentdiscount.ShipmentPrices;
import dev.stasys.shipmentdiscount.Transaction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static dev.stasys.shipmentdiscount.rules.YearMonthKey.formKey;

/**
 * Accumulated discounts cannot exceed 10 € in a calendar month. If there are not enough funds to fully cover a
 * discount this calendar month, it should be covered partially.
 */
public class DiscountsCannotExceedTenEuroRule extends Rule {
    private static final BigDecimal DISCOUNT_MONTHLY_LIMIT = new BigDecimal("10.00");
    private final Map<String, BigDecimal> discountCountPerMonth = new HashMap<>();

    public DiscountsCannotExceedTenEuroRule(ShipmentPrices shipmentPrices) {
        super(shipmentPrices);
    }

    @Override
    protected boolean isRuleApplicable(Transaction transaction, Discount discount, BigDecimal shippingPrice) {
        String key = formKey(transaction.date);
        discountCountPerMonth.computeIfAbsent(key, k -> new BigDecimal("0.00"));
        BigDecimal monthTotalAppliedDiscounts = discountCountPerMonth.get(key);
        return monthTotalAppliedDiscounts.add(discount.appliedDiscount).compareTo(DISCOUNT_MONTHLY_LIMIT) > 0;
    }

    @Override
    protected Discount apply(Transaction transaction, Discount discount, BigDecimal shippingPrice) {
        BigDecimal monthTotalAppliedDiscounts = discountCountPerMonth.get(formKey(transaction.date));
        BigDecimal diff = monthTotalAppliedDiscounts.add(discount.appliedDiscount).subtract(DISCOUNT_MONTHLY_LIMIT);
        return new Discount(
            discount.shipmentPrice.add(diff),
            discount.appliedDiscount.subtract(diff)
        );
    }

    @Override
    protected void postProcessNonAppliedRule(Transaction transaction, Discount discount, BigDecimal shipmentPrice) {
        BigDecimal monthTotalAppliedDiscounts = discountCountPerMonth.get(formKey(transaction.date));
        discountCountPerMonth.put(formKey(transaction.date), monthTotalAppliedDiscounts.add(discount.appliedDiscount));
    }
}
