package dev.stasys.shipmentdiscount

import spock.lang.Specification

import java.nio.file.Paths

class AppArgsSpec extends Specification {

    def "default to input.txt when no arguments provided"() {
        when:
            def result = new AppArgs().inputFilePath()
        then:
            result == Paths.get("input.txt")
    }

    def "use first argument as file provided"() {
        given:
            def appArgs = ['/bin/myInputFile.txt', 'fileToIgnore.txt']
        when:
            def result = new AppArgs(appArgs as String[]).inputFilePath()
        then:
            result == Paths.get(appArgs[0])
    }
}
