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


class ChartData {
    List<BarSet> barSets = []

    void addBarSet(String bucketLabel, Map<String, Double> categoryValueMap) {
        BarSet barSet = new BarSet(label:bucketLabel)
        categoryValueMap.each { groupKey, value ->
            barSet.addBar(groupKey, value)
        }
        barSets.add(barSet)
    }

    static interface Bar {
        String getLabel()
        Double getTotal()
    }

    static class BarSet implements Bar {
        String label
        Map<String, Double> bars = [:]

        void addBar(String barType, Double value) {
            bars.put(barType, value)
        }

        Double getTotal() {
            Double total = 0;
            bars.values().each { Double value ->
                total += value
            }
            return total
        }

        Double getBarValue(String barType) {
            bars.get(barType) ?: 0 //default to 0
        }

        Integer getBarCount() {
            bars.size()
        }

    }
}
