package dev.stasys.shipmentdiscount;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppArgs {
    private final String[] args;

    public AppArgs(String[] args) {
        this.args = args;
    }

    public Path inputFilePath() {
        return args.length == 0 ? Paths.get("input.txt") : Paths.get(args[0]);
    }
}