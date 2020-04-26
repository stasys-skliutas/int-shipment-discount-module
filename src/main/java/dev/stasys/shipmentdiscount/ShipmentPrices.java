package dev.stasys.shipmentdiscount;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

public class ShipmentPrices {
    private final Map<Carrier, Map<PackageSize, BigDecimal>> prices;

    public ShipmentPrices(Map<Carrier, Map<PackageSize, BigDecimal>> prices) {
        this.prices = prices;
    }

    public BigDecimal priceFor(Carrier carrier, PackageSize packageSize) {
        return prices.get(carrier).get(packageSize);
    }

    public BigDecimal lowestPriceForShipmentSize(PackageSize packageSize) {
        return Stream.of(Carrier.values())
            .map(carrier -> priceFor(carrier, packageSize))
            .min(BigDecimal::compareTo)
            .get();
    }
}
