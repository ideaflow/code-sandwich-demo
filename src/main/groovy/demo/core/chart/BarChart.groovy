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


class BarChart {
    String title
    List<StackedBar> bars = []

    void addStackedBar(String label, Map<String, Double> valueMap) {
        StackedBar bar = new StackedBar(label)
        valueMap.each { groupKey, value ->
            bar.addSection(groupKey, value)
        }
        bars.add(bar)
    }

    static interface Bar {
        String getLabel()
        Double getTotal()
    }

    static class StackedBar implements Bar {
        String label
        Map<String, Double> barSections = [:]

        StackedBar(String label) {
            this.label = label
        }

        void addSection(String barType, Double value) {
            barSections.put(barType, value)
        }

        Double getTotal() {
            Double total = 0;
            barSections.values().each { Double value ->
                total += value
            }
            return total
        }

    }
}
