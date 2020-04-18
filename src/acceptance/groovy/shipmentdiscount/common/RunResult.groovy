package shipmentdiscount.common;

import groovy.transform.Immutable;

@Immutable
class RunResult {
    String output
    String errorOutput
    int exitCode
}
