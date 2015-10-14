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
package demo.core.chart.bucket


class MovingAverageBucket extends DataBucket {

    List<Double> movingAverageSeries = []
    List<Double> samplesToAverage = []

    private final String bucketKey
    private final int numberOfSamplesToAverage

    MovingAverageBucket(String bucketKey, int numberOfSamplesToAverage) {
        this.bucketKey = bucketKey
        this.numberOfSamplesToAverage = numberOfSamplesToAverage
    }

    @Override
    boolean matches(String groupKey, Double value) {
        return (bucketKey == groupKey)
    }

    @Override
    void addDataSample(String groupKey, Double value) {

        samplesToAverage.add(value)
        removeExtraSamples(samplesToAverage)

        movingAverageSeries.add( calculateMovingAverage() )

    }

    @Override
    int getTotalSamples() {
        movingAverageSeries.size()
    }

    private Double calculateMovingAverage() {
        samplesToAverage.sum() / samplesToAverage.size()
    }

    private removeExtraSamples(List<?> samplesToAverage) {
        if (samplesToAverage.size() > numberOfSamplesToAverage) {
            samplesToAverage.remove(0)
        }
    }
}
