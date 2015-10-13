package demo.core.chart

import demo.api.FrictionChart
import demo.core.timeline.TimeBand

interface IdeaFlowChart {

    void configure()

    void fillChart(List<TimeBand> bands)

    FrictionChart generate()

}