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

import groovy.util.logging.Slf4j

@Slf4j
class DataBucket {


    int from
    int to

    Map<String, Integer> frequencyMap = [:]
    Map<String, Double> averageValueMap = [:]


    boolean matches(Double value) {
        (value > from && value <= to)
    }

    String addSample(String groupKey, Double value) {
        initializeFrequencyToZero(groupKey)
        updateAverage(groupKey, value)
        updateSampleFrequency(groupKey)
    }

    void updateSampleFrequency(String key) {
        Integer frequency = frequencyMap.get(key)
        frequencyMap.put(key,  ++frequency)
    }

    void initializeFrequencyToZero(String groupKey) {
        Integer frequency = frequencyMap.get(groupKey)
        if (!frequency) {
            frequencyMap.put(groupKey, 0)
        }
    }


    void updateAverage(String groupKey, Double value) {
        Double groupAverage = averageValueMap.get(groupKey)

        if (groupAverage) {
            averageValueMap.put(groupKey, calculateAvg(groupKey, groupAverage, value))
        } else {
            averageValueMap.put(groupKey, value)
        }
    }

    private Double calculateAvg(String groupKey, Double groupAverage, Double value) {
        Integer groupFrequency = frequencyMap.get(groupKey);
        (groupFrequency * groupAverage + value)/(groupFrequency + 1)
    }

    Double getGroupAverage(String groupKey) {
        averageValueMap.get(groupKey)
    }

    String getBucketDescription() {
        if (to == Integer.MAX_VALUE) {
            "[$from +]"
        } else {
            "[$from - $to]"
        }
    }

    Integer getTotalSamples() {
        Integer total = 0
        frequencyMap.values().each { partialTotal ->
            total += partialTotal
        }
        return total
    }

}
