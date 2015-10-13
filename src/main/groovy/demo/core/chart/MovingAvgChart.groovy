package demo.core.chart

import demo.api.FrictionChart
import demo.core.chart.bucket.MovingAverageBucket
import demo.core.model.BandType
import demo.core.timeline.TimeBand

class MovingAvgChart implements IdeaFlowChart {

    List<MovingAverageBucket> buckets

    void configure() {
        buckets = [new MovingAverageBucket(BandType.conflict.name(), 4),
                   new MovingAverageBucket(BandType.learning.name(), 4),
                   new MovingAverageBucket(BandType.rework.name(), 4),
        ]
    }


    void fillChart(List<TimeBand> bands) {
        bands.each { TimeBand band ->
            fillDataBuckets(band.bandType.name(), ((double)band.duration.duration)/60)
        }
    }

    private void fillDataBuckets(String groupKey, Double value) {
        buckets.each { bucket ->
            bucket.addSample(groupKey, value)
        }
    }

    FrictionChart generate() {
        FrictionChart chart = new FrictionChart()
        chart.title = "Average Friction By Type (Minutes)"

        chart.ticks = ['Troubleshooting', 'Learning', 'Rework']

        chart.conflictSeriesLabel = "Average Troubleshooting Time (minutes)"
        chart.learningSeriesLabel = "Average Learning Time (minutes)"
        chart.reworkSeriesLabel = "Average Rework Time (minutes)"

        chart.conflictSeries = buckets[0].movingAverageSeries
        chart.learningSeries = buckets[1].movingAverageSeries
        chart.reworkSeries = buckets[2].movingAverageSeries

        return chart
    }

}
