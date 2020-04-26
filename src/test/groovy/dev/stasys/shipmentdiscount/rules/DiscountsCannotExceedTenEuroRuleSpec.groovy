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

class DiscountsCannotExceedTenEuroRuleSpec extends Specification {
    @Shared
    def shipmentPrices = new ShipmentPrices(
        (MR): [
            (S): new BigDecimal('3.00'),
            (L): new BigDecimal('6.00')
        ],
        (LP): [
            (S): new BigDecimal('4.00'),
        ]
    )

    def 'discount does not change when total discount amount below 10 Eur'() {
        given:
            def rule = new DiscountsCannotExceedTenEuroRule(shipmentPrices)
            def transaction = new Transaction(LocalDate.parse('2020-03-01'), L, MR)
            def previousDiscount = new Discount(shipmentPrices.priceFor(MR, L).subtract(new BigDecimal('6.00')), new BigDecimal('8.00'))
        when:
            def discount = rule.apply(transaction, previousDiscount)
        then:
            discount.appliedDiscount == new BigDecimal('8.00')
            discount.shipmentPrice == new BigDecimal('0.00')
    }

    def 'discount does not change when total discount amount is 10 Eur'() {
        given:
            def rule = new DiscountsCannotExceedTenEuroRule(shipmentPrices)
            def transaction = new Transaction(LocalDate.parse('2020-03-01'), L, MR)
            def previousDiscount = new Discount(shipmentPrices.priceFor(MR, L).subtract(new BigDecimal('6.00')), new BigDecimal('10.00'))
        when:
            def discount = rule.apply(transaction, previousDiscount)
        then:
            discount.appliedDiscount == new BigDecimal('10.00')
            discount.shipmentPrice == new BigDecimal('0.00')
    }

    def 'discount covered partially when total discount amount exceeds 10 Eur'() {
        given:
            def rule = new DiscountsCannotExceedTenEuroRule(shipmentPrices)
            def transaction = new Transaction(LocalDate.parse('2020-03-01'), L, MR)
            def previousDiscount = new Discount(shipmentPrices.priceFor(MR, L).subtract(new BigDecimal('6.00')), new BigDecimal('12.00'))
        when:
           def discount = rule.apply(transaction, previousDiscount)
        then:
            discount.appliedDiscount == new BigDecimal('10.00')
            discount.shipmentPrice == new BigDecimal('2.00')
    }
}
