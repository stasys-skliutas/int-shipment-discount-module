package dev.stasys.shipmentdiscount;

import java.util.Arrays;

import static java.util.Optional.ofNullable;

public class App {
    public static void main(String[] args) {
        System.out.println("Works");
        ofNullable(args).ifPresent(strings -> System.out.println(Arrays.toString(args)));
        System.err.println("Does not work");
        System.exit(1);
    }
}
