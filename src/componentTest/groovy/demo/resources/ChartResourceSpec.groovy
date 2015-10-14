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

    def "chart methods should not explode"() {
        when:
        chartClient.getFilteredCharts("author", "hashtag");

        then:
        notThrown(Throwable)
    }

    def "filters should function via query params"() {
        when:
        List<FrictionChart> charts = chartClient.getFilteredCharts("two_each", null)
        then:
        charts.size() == 2
        FrictionChart frequencyChart = charts.get(0)
        frequencyChart.getConflictSeries().get(0) == 2.0d
    }

}
