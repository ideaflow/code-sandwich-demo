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
import demo.core.chart.bucket.MovingAverageBucket
import demo.core.model.BandType
import demo.core.timeline.TimeBand

class MovingAvgChartBuilder implements IdeaFlowChartBuilder {

    List<MovingAverageBucket> buckets
    ChartDataSet chartDataSet

    void configure(ChartDataSet chartDataSet) {
        this.chartDataSet = chartDataSet
        buckets = [new MovingAverageBucket(BandType.conflict.name(), 4),
                   new MovingAverageBucket(BandType.learning.name(), 4),
                   new MovingAverageBucket(BandType.rework.name(), 4),
        ]
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
