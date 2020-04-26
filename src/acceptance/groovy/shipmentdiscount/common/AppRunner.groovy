package shipmentdiscount.common

class AppRunner {
    private static final String EXECUTABLE_PATH = System.getProperty('jar.path')

    static RunResult run(List<String> args = []) {
        def cmd = 'java -jar ' + EXECUTABLE_PATH + ' ' + args.join(' ')
        def process = cmd.execute([], new File(EXECUTABLE_PATH).parentFile)
        def outputStream = new StringBuffer()
        def errorStream = new StringBuffer()
        process.waitForProcessOutput(outputStream, errorStream)
        println outputStream.toString()
        System.err.println(errorStream.toString())
        return new RunResult(outputStream.toString(), errorStream.toString(), process.exitValue())
    }
}