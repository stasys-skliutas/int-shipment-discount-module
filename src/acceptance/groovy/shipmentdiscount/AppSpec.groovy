import shipmentdiscount.common.AppRunner

class AppSpec extends spock.lang.Specification {
    def 'acceptance tests work'() {
        when:
            def runResult = AppRunner.run(['-myProp', 'myValue'])
        then:
            with(runResult) {
                exitCode == 1
                errorOutput == 'Does not work' + System.lineSeparator()
                output.contains('Works' + System.lineSeparator())
                output.contains('-myProp')
                output.contains('myValue')
            }
    }
}