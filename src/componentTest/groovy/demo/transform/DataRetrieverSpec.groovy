package demo.transform

import demo.core.chart.DataRetriever
import spock.lang.Specification


class DataRetrieverSpec extends Specification {

    private DataRetriever dataRetriever = new DataRetriever()

    //TODO make this not load a static file from the home directory.
    def "should load ifm files in baseDir"() {
        when:

        List models = dataRetriever.retrieveIfmModels()

        then:
        models.size() == 1
    }
}
