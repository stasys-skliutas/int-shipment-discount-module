package dev.stasys.shipmentdiscount;

import dev.stasys.shipmentdiscount.rules.AllSShipmentsMathLowestSPackagePriceRule;
import dev.stasys.shipmentdiscount.rules.DiscountsCannotExceedTenEuroRule;
import dev.stasys.shipmentdiscount.rules.Rule;
import dev.stasys.shipmentdiscount.rules.ThirdShipmentViaLpFreeRule;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static dev.stasys.shipmentdiscount.Carrier.LP;
import static dev.stasys.shipmentdiscount.Carrier.MR;
import static dev.stasys.shipmentdiscount.PackageSize.*;

public class App {

    public static void main(String[] args) {
        var shipmentPrices = new ShipmentPrices(Map.of(
            LP, Map.of(
                S, new BigDecimal("1.50"),
                M, new BigDecimal("4.90"),
                L, new BigDecimal("6.90")
            ),
            MR, Map.of(
                S, new BigDecimal("2.00"),
                M, new BigDecimal("3.00"),
                L, new BigDecimal("4.00")
            )
        ));

        List<Rule> rules = List.of(
            new AllSShipmentsMathLowestSPackagePriceRule(shipmentPrices),
            new ThirdShipmentViaLpFreeRule(shipmentPrices),
            new DiscountsCannotExceedTenEuroRule(shipmentPrices)
        );

        Path inputFilePath = new AppArgs(args).inputFilePath();
        validateIfExists(inputFilePath);
        try (Scanner s = new Scanner(inputFilePath)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                Transaction transaction = Transaction.parse(line.trim());
                if (transaction.ignored) {
                    System.out.println(line + " Ignored");
                    continue;
                }
                BigDecimal shipmentPrice = shipmentPrices.priceFor(transaction.carrier, transaction.packageSize);
                Discount discountApplied = rules.stream().reduce(
                        new Discount(shipmentPrice),
                        (discount, rule) -> rule.apply(transaction, discount),
                        (discount, discount2) -> discount2);
                System.out.println(line + " " + discountApplied);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    private static void validateIfExists(Path inputFilePath) {
        if (!Files.exists(inputFilePath)) {
            System.out.println("File " + inputFilePath + " was not found");
            System.exit(0);
        }
    }
}
