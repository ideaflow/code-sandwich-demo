package test.support

import demo.core.chart.ChartDataSet
import demo.core.ifm.ifmsource.IfmTask


class ChartDataSetBuilder {

    List<IfmTaskBuilder> ifmTaskBuilders = []

    static ChartDataSetBuilder create() {
        return new ChartDataSetBuilder()
    }

    IfmTaskBuilder newTask(String taskName) {
        IfmTaskBuilder taskBuilder = IfmTaskBuilder.create().taskName(taskName)
        ifmTaskBuilders.add(taskBuilder)
        return taskBuilder
    }

    ChartDataSet build() {
        List<IfmTask> ifmTaskList = ifmTaskBuilders.collect { taskBuilder ->
            taskBuilder.build()
        }
        new ChartDataSet(ifmTaskList)
    }



}
