package demo.core.chart

import demo.api.FrictionChart
import demo.core.dsl.IdeaFlowReader
import demo.core.model.IdeaFlowModel
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory

class ChartGenerator {

    IfmFileManager fileManager = new IfmFileManager()

    List<IdeaFlowChart> chartGenerators = []

    void configure(IdeaFlowChart chartGenerator) {
        chartGenerator.configure()
        chartGenerators.add( chartGenerator )
    }

    List<FrictionChart> generateCharts() {
        List<File> ifmFiles = fileManager.fetchAllIfmFiles()
        ifmFiles = ifmFiles.sort { file ->
            file.lastModified()
        }
        ifmFiles.each { ifmFile ->
            fillCharts(ifmFile);
        }
        chartGenerators.collect { chartGenerator ->
            chartGenerator.generate()
        }
    }

    private void fillCharts(File ifmFile) {
        IdeaFlowModel model = new IdeaFlowReader().readModel(ifmFile)
        Timeline timeline = new TimelineFactory().create(model)

        chartGenerators.each { chartGenerator ->
            chartGenerator.fillChart(timeline.getConflictBands())
            chartGenerator.fillChart(timeline.getGenericBands())
        }
    }

}
