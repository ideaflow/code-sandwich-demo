package demo.core.chart.builder

import demo.api.FrictionChart
import demo.core.chart.ChartDataSet
import demo.core.chart.bucket.AggregatorBucket
import demo.core.model.BandType
import demo.core.timeline.TimeBand

class FrequencyChartBuilder implements IdeaFlowChartBuilder {

    List<AggregatorBucket> buckets
    ChartDataSet chartDataSet

    void configure(ChartDataSet chartDataSet) {
        this.chartDataSet = chartDataSet
        buckets = [new AggregatorBucket("[0-5]", { key, value -> value > 0 && value <= 5}),
                   new AggregatorBucket("[5-10]", { key, value -> value > 5 && value <= 10}),
                   new AggregatorBucket("[10-30]", { key, value -> value > 10 && value <= 30}),
                   new AggregatorBucket("[30-200]", { key, value -> value > 30 && value <= 200}),
                   new AggregatorBucket("[200m+]", { key, value -> value >= 200})]
    }

    void fillChart(File ifmFile) {
        List<TimeBand> bands = chartDataSet.timeBandsMap.get(ifmFile);
        bands.each { TimeBand band ->
            fillDataBuckets(band.bandType.name(), ((double)band.duration.duration)/60)
        }
    }

    private void fillDataBuckets(String groupKey, Double value) {
        buckets.each { bucket ->
                bucket.addSample(groupKey, value)
        }
    }

    FrictionChart build() {
        FrictionChart chart = new FrictionChart()
        chart.title = "Friction Frequency By Friction Type"

        chart.conflictSeriesLabel = "Conflict Frequency"
        chart.learningSeriesLabel = "Learning Frequency"
        chart.reworkSeriesLabel = "Rework Frequency"

        buckets.each { bucket ->
            chart.conflictSeries.add( bucket.getGroupFrequency(BandType.conflict.name()))
            chart.learningSeries.add( bucket.getGroupFrequency(BandType.learning.name()))
            chart.reworkSeries.add( bucket.getGroupFrequency(BandType.rework.name()))
        }

        chart.ticks = buckets.collect { bucket ->
            bucket.description
        }

        return chart
    }

}
