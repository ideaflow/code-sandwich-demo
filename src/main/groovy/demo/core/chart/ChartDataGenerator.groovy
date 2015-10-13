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
package demo.core.chart

import demo.core.model.IdeaFlowModel
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory

class ChartDataGenerator {

    List<DataBucket> buckets

    ChartDataGenerator() {
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
            fillDataBuckets(band.bandType.name(), ((double)band.duration.duration)/60)
        }
    }


    void fillDataBuckets(String groupKey, Double value) {
        buckets.each { bucket ->
            if (bucket.matches(value)) {
                bucket.addSample(groupKey, value)
            }
        }
    }

    ChartData generateFrequencyChartData() {
        ChartData chart = generateMultiBarChart("frequencyMap")
        return chart
    }

    ChartData generateAveragesChartData() {
        ChartData chart = generateMultiBarChart("averageValueMap")
        return chart
    }

    ChartData generateMultiBarChart(String accessor) {
        ChartData chart = new ChartData()

        buckets.each { DataBucket bucket ->
            chart.addBarSet(bucket.bucketDescription, bucket."${accessor}")
        }
        return chart
    }

}