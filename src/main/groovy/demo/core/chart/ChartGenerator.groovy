package demo.core.chart

import demo.api.FrictionChart
import demo.core.chart.builder.IdeaFlowChartBuilder
import demo.core.dsl.IdeaFlowReader
import demo.core.model.IdeaFlowModel
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChartGenerator {

    //priorities = fix up unit tests, different chart builder

    @Autowired
    IfmFileManager fileManager = new IfmFileManager()

    IdeaFlowChartBuilder configure(IdeaFlowChartBuilder chartBuilder) {
        chartBuilder.configure()
        return chartBuilder
    }

    List<FrictionChart> generateCharts(List<IdeaFlowChartBuilder> chartBuilders) {
        List<File> ifmFiles = fileManager.fetchAllIfmFiles()
        ifmFiles = ifmFiles.sort { file ->
            file.lastModified()
        }
        ifmFiles.each { ifmFile ->
            fillCharts(ifmFile, chartBuilders);
        }
        chartBuilders.collect { chartBuilder ->
            chartBuilder.build()
        }
    }

    private void fillCharts(File ifmFile, List<IdeaFlowChartBuilder> chartBuilders) {
        IdeaFlowModel model = new IdeaFlowReader().readModel(ifmFile)
        Timeline timeline = new TimelineFactory().create(model)

        chartBuilders.each { chartBuilder ->
            chartBuilder.fillChart(timeline.getConflictBands())
            chartBuilder.fillChart(timeline.getGenericBands())
        }
    }

}
