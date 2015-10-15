package demo.core.ifm.ifmsource

import demo.core.timeline.TimeBand

interface IfmTask {

    String getTaskName()
    Date getStartDate()
    List<TimeBand> getTimeBands()

    boolean isByAuthor(String author)


}
