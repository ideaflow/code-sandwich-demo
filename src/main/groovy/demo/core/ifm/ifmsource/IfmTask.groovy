package demo.core.ifm.ifmsource

import demo.core.timeline.TimeBand

interface IfmTask {

    String getTaskName()
    List<TimeBand> getTimeBands()


}
