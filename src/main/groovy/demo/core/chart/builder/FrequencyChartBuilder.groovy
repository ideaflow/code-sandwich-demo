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
package demo.core.chart.builder

import demo.api.FrictionChart
import demo.core.chart.ChartDataSet
import demo.core.chart.bucket.AggregatorBucket
import demo.core.ifm.ifmsource.IfmTask
import demo.core.model.BandType
import demo.core.timeline.TimeBand

class FrequencyChartBuilder implements IdeaFlowChartBuilder {

    List<AggregatorBucket> buckets
    ChartDataSet chartDataSet

    FrequencyChartBuilder(ChartDataSet chartDataSet) {
        configure(chartDataSet)
    }

    void configure(ChartDataSet chartDataSet) {
        this.chartDataSet = chartDataSet
        buckets = [new AggregatorBucket("[0-5]", { key, value -> value > 0 && value <= 5}),
                   new AggregatorBucket("[5-10]", { key, value -> value > 5 && value <= 10}),
                   new AggregatorBucket("[10-30]", { key, value -> value > 10 && value <= 30}),
                   new AggregatorBucket("[30-200]", { key, value -> value > 30 && value <= 200}),
                   new AggregatorBucket("[200m+]", { key, value -> value >= 200})]
    }

    FrictionChart build() {
        fillChart()
        return buildChart()

    }

    private void fillChart() {
        chartDataSet.filteredTasks.each { ifmTask ->
            loadTimeBands(chartDataSet.getFilteredBands(ifmTask))
        }
    }

    private FrictionChart buildChart() {

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

    private void loadTimeBands(List<TimeBand> timeBands) {
        timeBands.each { TimeBand band ->
            fillDataBuckets(band.bandType.name(), ((double)band.duration.duration)/60)
        }
    }

    private void fillDataBuckets(String groupKey, Double value) {
        buckets.each { bucket ->
            bucket.addSample(groupKey, value)
        }
    }


}
