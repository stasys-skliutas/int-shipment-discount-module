package dev.stasys.shipmentdiscount.rules


import dev.stasys.shipmentdiscount.Discount
import dev.stasys.shipmentdiscount.ShipmentPrices
import dev.stasys.shipmentdiscount.Transaction
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

import static dev.stasys.shipmentdiscount.Carrier.LP
import static dev.stasys.shipmentdiscount.Carrier.MR
import static dev.stasys.shipmentdiscount.PackageSize.*

class AllSShipmentsMathLowestSPackagePriceRuleSpec extends Specification {
    @Shared
    def shipmentPrices = new ShipmentPrices(
        (MR): [
            (S): new BigDecimal('1.19'),
            (M): new BigDecimal('2.22'),
            (L): new BigDecimal('3.99')
        ],
        (LP): [
            (S): new BigDecimal('3.00'),
            (M): new BigDecimal('5.00')
        ]
    )
    @Shared
    def rule = new AllSShipmentsMathLowestSPackagePriceRule(shipmentPrices)

    def 'all S shipments should always match the lowest S package price among the providers'() {
        given:
            def transaction = new Transaction(LocalDate.parse(date), size, carrier)
        when:
            def discount = rule.apply(transaction, new Discount(shipmentPrices.priceFor(carrier, size)))
        then:
            discount.shipmentPrice == new BigDecimal(price)
            discount.appliedDiscount == new BigDecimal(discountAmount)
        where:
            date         | carrier | size || price | discountAmount
            '2020-02-01' | MR      | S    || '1.19'| '0.00' // lowest priced S shipment
            '2020-02-02' | MR      | M    || '2.22'| '0.00'
            '2020-03-03' | MR      | L    || '3.99'| '0.00'
            '2020-03-04' | LP      | S    || '1.19'| '1.81'
    }
}
