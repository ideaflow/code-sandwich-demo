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
import demo.core.chart.bucket.RangeBucket
import demo.core.model.BandType
import demo.core.timeline.TimeBand

class FrequencyChartBuilder {

    List<RangeBucket> buckets
    ChartDataSet chartDataSet

    FrequencyChartBuilder(ChartDataSet chartDataSet) {
        configure(chartDataSet)
    }

    void configure(ChartDataSet chartDataSet) {
        this.chartDataSet = chartDataSet
        buckets = [new RangeBucket(0, 5),
                   new RangeBucket(5, 10),
                   new RangeBucket(10, 30),
                   new RangeBucket(30, 120),
                   new RangeBucket(120, Integer.MAX_VALUE)]
    }

    FrictionChart build() {
        fillChart()
        return buildChart()

    }

    private void fillChart() {
        chartDataSet.tasks.each { ifmTask ->
            loadTimeBands(ifmTask.getTimeBands())
        }
    }

    private FrictionChart buildChart() {

        FrictionChart chart = new FrictionChart()
        chart.title = "Friction Frequency By Friction Type"

        buckets.each { RangeBucket bucket ->
            chart.conflictSeries.add( bucket.getGroupFrequency(BandType.conflict.name()))
            chart.learningSeries.add( bucket.getGroupFrequency(BandType.learning.name()))
            chart.learningSeries.add( bucket.getGroupFrequency(BandType.rework.name()))
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
