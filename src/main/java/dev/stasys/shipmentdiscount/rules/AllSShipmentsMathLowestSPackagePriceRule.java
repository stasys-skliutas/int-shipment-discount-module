package dev.stasys.shipmentdiscount.rules;

import dev.stasys.shipmentdiscount.Discount;
import dev.stasys.shipmentdiscount.PackageSize;
import dev.stasys.shipmentdiscount.ShipmentPrices;
import dev.stasys.shipmentdiscount.Transaction;

import java.math.BigDecimal;

/**
 * All S shipments should always match the lowest S package price among the providers
 */
public class AllSShipmentsMathLowestSPackagePriceRule extends Rule {

    public AllSShipmentsMathLowestSPackagePriceRule(ShipmentPrices shipmentPrices) {
        super(shipmentPrices);
    }

    @Override
    protected boolean isRuleApplicable(Transaction transaction, Discount discount, BigDecimal shippingPrice) {
        return transaction.packageSize == PackageSize.S;
    }

    @Override
    protected Discount apply(Transaction transaction, Discount discount, BigDecimal shippingPrice) {
        BigDecimal lowestPrice = shipmentPrices.lowestPriceForShipmentSize(PackageSize.S);
        return new Discount(
            lowestPrice,
            shipmentPrices.priceFor(transaction.carrier, transaction.packageSize).subtract(lowestPrice)
        );
    }
}
