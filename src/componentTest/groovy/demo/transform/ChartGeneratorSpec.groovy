package demo.transform

import demo.core.chart.BarChart
import demo.core.chart.ChartGenerator
import demo.core.model.BandType
import demo.core.model.IdeaFlowModel
import spock.lang.Specification
import test.support.IdeaFlowModelBuilder

class ChartGeneratorSpec extends Specification {

    private ChartGenerator chartGenerator = new ChartGenerator()
    private IdeaFlowModelBuilder modelBuilder = new IdeaFlowModelBuilder()


    def "should create frequency chart with count for the number of bands"() {
        given:

        IdeaFlowModel model = modelBuilder.addBand(BandType.conflict, 100)
                                          .addBand(BandType.conflict, 100).build()

        when:

        chartGenerator.fillChart(model)
        BarChart chart = chartGenerator.generateFrequencyChart()

        then:

        chart.bars.size() == 5
        chart.bars[3].label == "[30 - 200]"
        chart.bars[3].total == 2d
        chart.bars[3].barSections.size() == 1
    }

    def "should create multiple bar sections with different band types"() {
        given:

        IdeaFlowModel model = modelBuilder.addBand(BandType.conflict, 100)
                                          .addBand(BandType.learning, 100).build()

        when:

        chartGenerator.fillChart(model)
        BarChart chart = chartGenerator.generateFrequencyChart()

        then:

        chart.bars.size() == 5
        chart.bars[3].label == "[30 - 200]"
        chart.bars[3].total == 2d
        chart.bars[3].barSections.size() == 2
    }


    def "should aggregate data across multiple models"() {
        given:
        IdeaFlowModel model1 = new IdeaFlowModelBuilder().addBand(BandType.conflict, 100)
                                                         .addBand(BandType.learning, 100).build()
        IdeaFlowModel model2 = new IdeaFlowModelBuilder().addBand(BandType.learning, 100)
                                                         .addBand(BandType.rework, 100).build()
        when:

        chartGenerator.fillChart(model1)
        chartGenerator.fillChart(model2)
        BarChart chart = chartGenerator.generateFrequencyChart()

        then:

        chart.bars.size() == 5
        chart.bars[3].label == "[30 - 200]"
        chart.bars[3].total == 4d
        chart.bars[3].barSections.size() == 3
        chart.bars[3].barSections[BandType.learning.name()] == 2d
    }

    def "should calculate averages of similar band types"() {
        given:
        IdeaFlowModel model = modelBuilder.addBand(BandType.conflict, 50)
                                           .addBand(BandType.conflict, 70)
                                           .addBand(BandType.learning, 100)
                                           .addBand(BandType.learning, 120).build()
        when:

        chartGenerator.fillChart(model)
        BarChart chart = chartGenerator.generateAveragesChart()

        then:

        chart.bars.size() == 5
        chart.bars[3].label == "[30 - 200]"
        println chart.bars[3].barSections
        chart.bars[3].barSections.size() == 2
        chart.bars[3].barSections[BandType.conflict.name()] == 60d
        chart.bars[3].barSections[BandType.learning.name()] == 110d

    }

}
