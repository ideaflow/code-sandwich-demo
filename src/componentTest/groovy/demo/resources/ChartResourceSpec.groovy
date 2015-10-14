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
        FrictionChart chart = chartClient.getFrequencyChart("two_each", null);

        then:
        chart.conflictSeries.get(0) == 2.0d
    }

    def "should generate series chart with one point per timeband"() {
        when:
        FrictionChart chart = chartClient.getSeriesChart("two_each", null)

        then:
        chart.getConflictSeries().size() == 2
    }

}
