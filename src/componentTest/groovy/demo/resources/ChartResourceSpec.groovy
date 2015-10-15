package demo.resources

import demo.ComponentTest
import demo.api.FrictionChart
import demo.client.ChartClient
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@ComponentTest
class ChartResourceSpec extends Specification {

    @Autowired
    private ChartClient chartClient

    def "should generate frequency chart that counts timebands"() {
        when:
        FrictionChart chart = chartClient.getFrequencyChart();

        then:
        chart.conflictSeries.get(0) == 9.0d
    }

}
