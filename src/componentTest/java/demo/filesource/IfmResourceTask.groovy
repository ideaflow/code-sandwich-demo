package demo.filesource

import demo.core.ifm.dsl.IdeaFlowReader
import demo.core.ifm.ifmsource.IfmTask
import demo.core.model.IdeaFlowModel
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory


class IfmResourceTask implements IfmTask {

    String resourcePath
    List<TimeBand> timeBands

    IfmResourceTask(String resourcePath) {
        this.resourcePath = resourcePath;
        initData()
    }

    @Override
    String getTaskName() {
        return resourcePath
    }

    @Override
    Date getStartDate() {
        return new Date()
    }

    @Override
    boolean isByAuthor(String author) {
        return resourcePath.contains(author)
    }

    @Override
    List<TimeBand> getUnfilteredTimeBands() {
        return timeBands
    }

    private initData() {
        IdeaFlowModel model = readIfm()
        Timeline timeline = new TimelineFactory().create(model)
        timeBands = timeline.getTimeBands()
    }

    private IdeaFlowModel readIfm() {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath)
        if (inputStream == null) {
            throw new FileNotFoundException("Unable to find: $resourcePath")
        } else {
            return new IdeaFlowReader().readModel(inputStream)
        }
    }
}
