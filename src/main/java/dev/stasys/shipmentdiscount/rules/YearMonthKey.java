package dev.stasys.shipmentdiscount.rules;

import java.time.LocalDate;

class YearMonthKey {

    static String formKey(LocalDate date) {
        return "" + date.getYear() + date.getMonth();
    }
}
