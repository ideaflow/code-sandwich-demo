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
package demo.chart


import demo.core.model.BandType
import demo.core.model.IdeaFlowModel
import spock.lang.Specification
import test.support.IdeaFlowModelBuilder

//TODO rewrite this class
class ChartGeneratorSpec extends Specification {
//
//    private ChartDataGenerator chartGenerator = new ChartDataGenerator()
//    private IdeaFlowModelBuilder modelBuilder = new IdeaFlowModelBuilder()
//
//
//    def "should create frequency chart with count for the number of bands"() {
//        given:
//
//        IdeaFlowModel model = modelBuilder.addBand(BandType.conflict, 100)
//                                          .addBand(BandType.conflict, 100).build()
//
//        when:
//
//        chartGenerator.fillChart(model)
//        ChartData chart = chartGenerator.generateFrequencyChartData()
//
//        then:
//
//        chart.bars.size() == 5
//        chart.bars[3].label == "[30 - 200]"
//        chart.bars[3].total == 2d
//        chart.bars[3].barSections.size() == 1
//    }
//
//    def "should create multiple bar sections with different band types"() {
//        given:
//
//        IdeaFlowModel model = modelBuilder.addBand(BandType.conflict, 100)
//                                          .addBand(BandType.learning, 100).build()
//
//        when:
//
//        chartGenerator.fillChart(model)
//        ChartData chart = chartGenerator.generateFrequencyChartData()
//
//        then:
//
//        chart.bars.size() == 5
//        chart.bars[3].label == "[30 - 200]"
//        chart.bars[3].total == 2d
//        chart.bars[3].barSections.size() == 2
//    }
//
//
//    def "should aggregate data across multiple models"() {
//        given:
//        IdeaFlowModel model1 = new IdeaFlowModelBuilder().addBand(BandType.conflict, 100)
//                                                         .addBand(BandType.learning, 100).build()
//        IdeaFlowModel model2 = new IdeaFlowModelBuilder().addBand(BandType.learning, 100)
//                                                         .addBand(BandType.rework, 100).build()
//        when:
//
//        chartGenerator.fillChart(model1)
//        chartGenerator.fillChart(model2)
//        ChartData chart = chartGenerator.generateFrequencyChartData()
//
//        then:
//
//        chart.bars.size() == 5
//        chart.bars[3].label == "[30 - 200]"
//        chart.bars[3].total == 4d
//        chart.bars[3].barSections.size() == 3
//        chart.bars[3].barSections[BandType.learning.name()] == 2d
//    }
//
//    def "should calculate averages of similar band types"() {
//        given:
//        IdeaFlowModel model = modelBuilder.addBand(BandType.conflict, 50)
//                                           .addBand(BandType.conflict, 70)
//                                           .addBand(BandType.learning, 100)
//                                           .addBand(BandType.learning, 120).build()
//        when:
//
//        chartGenerator.fillChart(model)
//        ChartData chart = chartGenerator.generateAveragesChartData()
//
//        then:
//
//        chart.bars.size() == 5
//        chart.bars[3].label == "[30 - 200]"
//        println chart.bars[3].barSections
//        chart.bars[3].barSections.size() == 2
//        chart.bars[3].barSections[BandType.conflict.name()] == 60d
//        chart.bars[3].barSections[BandType.learning.name()] == 110d
//
//    }

}
