package demo.core.chart.builder

import demo.api.FrictionChart
import demo.core.timeline.TimeBand

interface IdeaFlowChartBuilder {

    void configure()

    void fillChart(List<TimeBand> bands)

    FrictionChart build()

}