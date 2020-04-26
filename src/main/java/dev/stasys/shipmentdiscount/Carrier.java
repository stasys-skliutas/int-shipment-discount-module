package dev.stasys.shipmentdiscount;

import java.util.List;

import static dev.stasys.shipmentdiscount.PackageSize.*;

public enum Carrier {
    LP(List.of(S, M, L)),
    MR(List.of(S, M, L));

    private final List<PackageSize> availableSizes;

    Carrier(List<PackageSize> availableSizes) {
        this.availableSizes = availableSizes;
    }

    public static Carrier valueOf(String carrierCode, PackageSize packageSize) {
        Carrier carrier = Carrier.valueOf(carrierCode);
        return carrier.availableSizes.contains(packageSize) ? carrier : null;
    }
}
