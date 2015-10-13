package demo.core.chart

import demo.api.FrictionChart
import demo.core.chart.bucket.RangeBucket
import demo.core.model.BandType
import demo.core.timeline.TimeBand

class FrequencyChart implements IdeaFlowChart {

    List<RangeBucket> buckets

    void configure() {
        buckets = [new RangeBucket(from: 0, to: 5),
                   new RangeBucket(from: 5, to: 10),
                   new RangeBucket(from: 10, to: 30),
                   new RangeBucket(from: 30, to: 200),
                   new RangeBucket(from: 200, to: Integer.MAX_VALUE)]
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
        chart.title = "Friction Frequency (Grouped by Minutes)"

        buckets.each { bucket ->
            chart.conflictSeries.add( bucket.getGroupFrequency(BandType.conflict.name()))
            chart.learningSeries.add( bucket.getGroupFrequency(BandType.learning.name()))
            chart.reworkSeries.add( bucket.getGroupFrequency(BandType.rework.name()))
        }

        chart.ticks = buckets.collect { bucket ->
            bucket.bucketDescription
        }

        return chart
    }

}
