package test.support

import demo.ComponentTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@ComponentTest
class IfmDataGeneratorSpec extends Specification {

    @Autowired
    private IfmDataGenerator ifmDataGenerator

    def "dataGenerator should annotate data with hashtags"() {
        when:
        File destDir = new File("/Users/janelle/generated3")
        ifmDataGenerator.extrapolateDataSet(destDir)

        then:
        destDir.list().size() > 0
    }
}
