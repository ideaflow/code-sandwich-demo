package test.support

import demo.core.ifm.ifmsource.IfmTask
import demo.core.model.BandEnd
import demo.core.model.BandStart
import demo.core.model.BandType
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory

class IfmTaskBuilder {

    private IdeaFlowModelBuilder ifmBuilder = IdeaFlowModelBuilder.create()

    private StubIfmTask ifmTask = new StubIfmTask()

    static IfmTaskBuilder create() {
        return new IfmTaskBuilder()
    }

    IfmTaskBuilder defaults() {
        ifmTask.taskName
        return this
    }

    IfmTaskBuilder taskName(String taskName) {
        ifmTask.taskName = taskName
        return this
    }

    IfmTaskBuilder addTimeBand(BandType bandType, int totalDuration) {
        ifmBuilder.addBand(bandType, totalDuration)
        return this
    }

    IfmTaskBuilder addTimeBand(BandType bandType) {
        ifmBuilder.addBand(bandType, 10)
        return this
    }

    IfmTask build() {
        Timeline timeline = new TimelineFactory().create(ifmBuilder.build())
        ifmTask.timeBands = timeline.timeBands
        return ifmTask
    }

    IdeaFlowModelBuilder addBand(BandType bandType, int totalDuration) {
        addBandStart(new BandStart(bandType, "comment", false))
        addEditorActivity((int)totalDuration/2)
        addEditorActivity((int)totalDuration/2)
        addBandEnd(new BandEnd(bandType))
    }

    class StubIfmTask implements IfmTask {

        String taskName
        List<TimeBand> timeBands = []

        @Override
        String getTaskName() {
            return taskName
        }

        @Override
        List<TimeBand> getTimeBands() {
            return timeBands
        }
    }
}
