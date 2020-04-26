import shipmentdiscount.common.AppRunner

class AppSpec extends spock.lang.Specification {

    def 'processing empty file results in empty result'() {
        when:
            def result = AppRunner.run(['empty.txt'])
        then:
            with(result) {
                exitCode == 0
                errorOutput.trim().length() == 0
                output.trim().length() == 0
            }
    }

    def 'processing non-existing file results in warning'() {
        when:
            def result = AppRunner.run(['ji34ji2j42i.txt'])
        then:
            with(result) {
                exitCode == 0
                output.trim().matches("File ji34ji2j42i\\.txt was not found")
            }
    }

    def 'calculate discounts'() {
        given:
            def expectedFileLines = [
                '2015-02-01 S MR 1.50 0.50',
                '2015-02-02 S MR 1.50 0.50',
                '2015-02-03 L LP 6.90 -',
                '2015-02-05 S LP 1.50 -',
                '2015-02-06 S MR 1.50 0.50',
                '2015-02-06 L LP 6.90 -',
                '2015-02-07 L MR 4.00 -',
                '2015-02-08 M MR 3.00 -',
                '2015-02-09 L LP 0.00 6.90',
                '2015-02-10 L LP 6.90 -',
                '2015-02-10 S MR 1.50 0.50',
                '2015-02-10 S MR 1.50 0.50',
                '2015-02-11 L LP 6.90 -',
                '2015-02-12 M MR 3.00 -',
                '2015-02-13 M LP 4.90 -',
                '2015-02-15 S MR 1.50 0.50',
                '2015-02-17 L LP 6.90 -',
                '2015-02-17 S MR 1.90 0.10',
                '2015-02-24 L LP 6.90 -',
                '2015-02-29 CUSPS Ignored',
                '2015-03-01 S MR 1.50 0.50',
            ]
        when:
            def result = AppRunner.run(['input.txt']).output.split('\\R')
        then:
            result.size() == 21
            result.eachWithIndex{ String entry, int i ->
                assert entry == expectedFileLines[i]
            }
    }
}