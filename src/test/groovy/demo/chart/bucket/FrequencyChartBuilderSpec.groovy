package demo.chart.bucket

import demo.api.FrictionChart
import demo.core.chart.ChartDataSet
import demo.core.chart.builder.FrequencyChartBuilder
import demo.core.model.BandType
import spock.lang.Specification
import test.support.ChartDataSetBuilder
import test.support.FixtureSupport

@Mixin(FixtureSupport)
class FrequencyChartBuilderSpec extends Specification {

    ChartDataSetBuilder dataSetBuilder = ChartDataSetBuilder.create()

    def "should ADD up all bands of the SAME type and SAME duration range."() {
        given:
            dataSetBuilder.newTask("task1")
                            .addTimeBand(BandType.conflict, 10)
                            .addTimeBand(BandType.conflict, 10)
            dataSetBuilder.newTask("task2")
                            .addTimeBand(BandType.conflict, 10)
                            .addTimeBand(BandType.conflict, 10)
            ChartDataSet dataSet = dataSetBuilder.build()
            FrequencyChartBuilder chartBuilder = new FrequencyChartBuilder(dataSet)

        when:

            FrictionChart chart = chartBuilder.build()
        then:
            chart.conflictSeries.size() == 5
            chart.conflictSeries.get(0) == 4.0d
    }

    def "should DISTRIBUTE band counts of different duration"() {
        given:
        dataSetBuilder.newTask("task1")
                .addTimeBand(BandType.conflict, SECONDS)
                .addTimeBand(BandType.conflict, SEVEN_MINUTES)
                .addTimeBand(BandType.conflict, TWENTY_MINUTES)
                .addTimeBand(BandType.conflict, TWENTY_MINUTES)
                .addTimeBand(BandType.conflict, ONE_HOUR)
                .addTimeBand(BandType.conflict, THREE_HOURS)
        ChartDataSet dataSet = dataSetBuilder.build()
        FrequencyChartBuilder chartBuilder = new FrequencyChartBuilder(dataSet)

        when:
        FrictionChart chart = chartBuilder.build()

        then:
        chart.conflictSeries.size() == 5
        chart.conflictSeries.get(0) == 1.0d
        chart.conflictSeries.get(1) == 1.0d
        chart.conflictSeries.get(2) == 2.0d
        chart.conflictSeries.get(3) == 1.0d
        chart.conflictSeries.get(4) == 1.0d
    }

    //TODO Implement this last test for FrequencyChartBuilder...

    def "should SEPARATE band counts of different types"() {
        given:
        dataSetBuilder.newTask("task1")
                .addTimeBand(BandType.conflict, 10)
                .addTimeBand(BandType.learning, 10)
                .addTimeBand(BandType.learning, 10)
                .addTimeBand(BandType.rework, 10)
        ChartDataSet dataSet = dataSetBuilder.build()
        FrequencyChartBuilder chartBuilder = new FrequencyChartBuilder(dataSet)

        when:
        FrictionChart chart = chartBuilder.build()

        then:
        chart.conflictSeries.size() == 5

        chart.conflictSeries.get(0) == 1.0d
        chart.learningSeries.get(0) == 2.0d
        chart.reworkSeries.get(0) == 1.0d
    }

}
