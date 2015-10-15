/*
 * Copyright 2015 New Iron Group, Inc.
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.core.chart

import demo.ComponentTest
import demo.api.FrictionChart
import demo.core.chart.builder.FrequencyChartBuilder
import demo.core.chart.builder.MovingAvgChartBuilder
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@ComponentTest
class ChartGeneratorSpec extends Specification {

    @Autowired
    private ChartDataSetFactory chartDataSetFactory
    private ChartDataSet defaultDataSet

    def setup() {
        defaultDataSet = chartDataSetFactory.defaultDataSet()
    }

    def "should generate frequency chart that counts bands"() {
        given:
        ChartDataSet filteredDataSet = defaultDataSet.filterByAuthor("two_each")

        when:
        FrictionChart frequencyChart = new FrequencyChartBuilder(filteredDataSet).build()

        then:
        frequencyChart.conflictSeries.get(0) == 2.0d
        frequencyChart.learningSeries.get(0) == 2.0d
        frequencyChart.reworkSeries.get(0) == 2.0d
    }

    def "should generate series chart that creates data points per timeband"() {
        given:
        ChartDataSet filteredDataSet = defaultDataSet.filterByAuthor("two_each")

        when:
        FrictionChart movingAvgChart = new MovingAvgChartBuilder(filteredDataSet).build()

        then:
        movingAvgChart.conflictSeries.size() == 2
        movingAvgChart.learningSeries.size() == 2
        movingAvgChart.reworkSeries.size() == 2
    }

}
