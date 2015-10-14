package demo.core.chart.builder

import demo.api.FrictionChart
import demo.core.chart.ChartDataSet
import demo.core.timeline.TimeBand

interface IdeaFlowChartBuilder {

    ChartDataSet getChartDataSet()

    void configure(ChartDataSet chartDataSet)

    void fillChart(File ifmFile)

    FrictionChart build()

}