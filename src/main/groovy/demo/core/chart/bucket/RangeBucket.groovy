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

import groovy.util.logging.Slf4j

@Slf4j
class RangeBucket {

    int from
    int to

    Map<String, Integer> frequencyMap = [:]

    RangeBucket(int from, int to) {
        this.from = from
        this.to = to
    }

    void addSample(String groupKey, Double value) {
       //TODO add the sample to the frequency map
    }

    boolean matchesRange(Double value) {
        value >= from && value < to
    }

    Integer getGroupFrequency(String groupKey) {
        frequencyMap.get(groupKey) ?: 0
    }

    String getDescription() {
        if (to == Integer.MAX_VALUE) {
            "[$from+]"
        } else {
            "[$from-$to]"
        }
    }


}
