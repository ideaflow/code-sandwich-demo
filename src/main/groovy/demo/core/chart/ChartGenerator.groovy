package demo.core.chart

import demo.api.FrictionChart
import demo.core.chart.builder.IdeaFlowChartBuilder
import org.springframework.stereotype.Component

@Component
class ChartGenerator {

    IdeaFlowChartBuilder configure(ChartDataSet chartDataSet, IdeaFlowChartBuilder chartBuilder) {
        chartBuilder.configure(chartDataSet)
        return chartBuilder
    }

    List<FrictionChart> generateCharts(List<IdeaFlowChartBuilder> chartBuilders) {
        chartBuilders.each { builder ->
            List<File> sortedFiles = builder.getChartDataSet().ifmFileList.sort { file ->
                file.lastModified()
            }
            sortedFiles.each { ifmFile ->
                builder.fillChart(ifmFile)
            }

        }

        chartBuilders.collect { chartBuilder ->
            chartBuilder.build()
        }
    }

}
