package demo.core.ifm.ifmsource

import demo.core.ifm.dsl.IdeaFlowReader
import demo.core.model.IdeaFlowModel
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class IfmFileTask implements IfmTask {

    String taskName

    private String relativePath
    private File ifmFile

    private List<TimeBand> timeBands

    IfmFileTask (File baseDir, File ifmFile) {
        this.relativePath = ifmFile.absolutePath.substring(baseDir.absolutePath.length())
        this.taskName = ifmFile.name
        this.ifmFile = ifmFile
        initData()
    }

    private initData() {
        IdeaFlowModel model = new IdeaFlowReader().readModel(ifmFile)
        Timeline timeline = new TimelineFactory().create(model)
        timeBands = timeline.getTimeBands()
    }

    @Override
    List<TimeBand> getTimeBands() {
        return timeBands
    }

}
