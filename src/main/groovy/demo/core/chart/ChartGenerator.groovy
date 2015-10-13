package demo.core.chart

import demo.core.model.IdeaFlowModel
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory

class ChartGenerator {

    List<DataBucket> buckets

    ChartGenerator() {
        configureBuckets()
    }

    void configureBuckets() {
        buckets = [new DataBucket(from: 0, to: 5),
                   new DataBucket(from: 5, to: 10),
                   new DataBucket(from: 10, to: 30),
                   new DataBucket(from: 30, to: 200),
                   new DataBucket(from: 200, to: Integer.MAX_VALUE)]
    }

    /*
     * Can be called repeatedly with multiple models
     */
    void fillChart(IdeaFlowModel model) {
        Timeline timeline = new TimelineFactory().create(model)
        loadTimeBands(timeline.getConflictBands())
        loadTimeBands(timeline.getGenericBands())
    }

    void loadTimeBands(List<TimeBand> bands) {
        bands.each { TimeBand band ->
            fillDataBuckets(band.bandType.name(), band.duration.duration)
        }
    }


    void fillDataBuckets(String groupKey, int value) {
        buckets.each { bucket ->
            if (bucket.matches(value)) {
                bucket.addSample(groupKey, value)
            }
        }
    }

    BarChart generateFrequencyChart() {
        return generateStackedBarChart("frequencyMap")
    }

    BarChart generateAveragesChart() {
        return generateStackedBarChart("averageValueMap")
    }

    BarChart generateStackedBarChart(String accessor) {
        BarChart chart = new BarChart()

        buckets.each { DataBucket bucket ->
            chart.addStackedBar(bucket.bucketDescription, bucket."${accessor}")
        }
        return chart
    }

}
