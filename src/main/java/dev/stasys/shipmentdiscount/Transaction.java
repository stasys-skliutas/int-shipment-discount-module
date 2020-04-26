package dev.stasys.shipmentdiscount;

import java.time.LocalDate;

public class Transaction {
    public final LocalDate date;
    public final PackageSize packageSize;
    public final Carrier carrier;
    public final boolean ignored;

    private Transaction(LocalDate date, PackageSize packageSize, Carrier carrier) {
        this.ignored = false;
        this.date = date;
        this.packageSize = packageSize;
        this.carrier = carrier;
    }

    private Transaction() {
        this.ignored = true;
        this.date = null;
        this.packageSize = null;
        this.carrier = null;
    }

    public static Transaction parse(String line) {
        String[] columns = line.trim().split("\\s");
        if (columns.length != 3) {
            return new Transaction();
        }
        LocalDate date;
        try {
            date = LocalDate.parse(columns[0]);
        } catch (Exception e) {
            return new Transaction();
        }
        PackageSize packageSize = PackageSize.valueOf(columns[1]);
        Carrier carrier = Carrier.valueOf(columns[2].trim(), packageSize);
        if (carrier == null) {
            return new Transaction();
        }
        return new Transaction(date, packageSize, carrier);
    }
}
