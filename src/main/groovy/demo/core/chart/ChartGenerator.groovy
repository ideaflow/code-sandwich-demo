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

import demo.api.FrictionChart
import demo.core.chart.builder.IdeaFlowChartBuilder
import demo.core.ifm.ifmsource.IfmTask
import org.springframework.stereotype.Component

import javax.validation.constraints.NotNull

@Component
class ChartGenerator {

    FrictionChart generateChart(@NotNull IdeaFlowChartBuilder chartBuilder) {
        generateCharts([chartBuilder]).get(0)
    }

    List<FrictionChart> generateCharts(List<IdeaFlowChartBuilder> chartBuilders) {
        chartBuilders.each { builder ->
            List<IfmTask> sortedTasks = builder.getChartDataSet().ifmTaskList.sort { task ->
                task.startDate
            }
            sortedTasks.each { ifmTask ->
                builder.fillChart(ifmTask)
            }

        }

        chartBuilders.collect { chartBuilder ->
            chartBuilder.build()
        }
    }

}
