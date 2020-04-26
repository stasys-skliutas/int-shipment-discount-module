package dev.stasys.shipmentdiscount.rules

import dev.stasys.shipmentdiscount.Discount
import dev.stasys.shipmentdiscount.ShipmentPrices
import dev.stasys.shipmentdiscount.Transaction
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

import static dev.stasys.shipmentdiscount.Carrier.LP
import static dev.stasys.shipmentdiscount.Carrier.MR
import static dev.stasys.shipmentdiscount.PackageSize.L
import static dev.stasys.shipmentdiscount.PackageSize.S

class ThirdShipmentViaLpFreeRuleSpec extends Specification {
    @Shared
    def shipmentPrices = new ShipmentPrices(
        (MR): [
            (S): new BigDecimal('1.00'),
            (L): new BigDecimal('2.11')
        ],
        (LP): [
            (S): new BigDecimal('1.50'),
            (L): new BigDecimal('3.00')
        ]
    )
    @Shared
    def rule = new ThirdShipmentViaLpFreeRule(shipmentPrices)

    def 'Third L shipment via LP should be free, but only once a calendar month'() {
        given:
            def transaction = new Transaction(LocalDate.parse(date), size, carrier)
        when:
            def discount = rule.apply(transaction, new Discount(shipmentPrices.priceFor(carrier, size)))
        then:
            discount.shipmentPrice == new BigDecimal(price)
            discount.appliedDiscount == new BigDecimal(discountAmount)
        where:
            date        | carrier | size || price | discountAmount
            '2020-02-01'| LP      | L    || '3.00'| '0.00' // first
            '2020-02-02'| LP      | S    || '1.50'| '0.00'
            '2020-02-03'| MR      | L    || '2.11'| '0.00'
            '2020-02-04'| LP      | L    || '3.00'| '0.00' // second
            '2020-02-05'| LP      | L    || '0.00'| '3.00' // third -> apply discount
            '2020-02-05'| LP      | L    || '3.00'| '0.00'
        and: // check if resets when different month
            '2020-04-01'| LP      | L    || '3.00'| '0.00' // first
            '2020-04-02'| LP      | L    || '3.00'| '0.00' // second
            '2020-04-03'| LP      | L    || '0.00'| '3.00' // third -> apply discount
    }
}
