package demo.core.ifm.ifmsource

import demo.core.timeline.TimeBand

class FilteredIfmTask implements IfmTask {

    private IfmTask delegate
    private TimeBandFilter filter

    FilteredIfmTask(IfmTask delegate, TimeBandFilter filter) {
        this.delegate = delegate
        this.filter = filter
    }

    @Override
    String getTaskName() {
        delegate.taskName
    }

    @Override
    Date getStartDate() {
        delegate.startDate
    }

    @Override
    boolean isByAuthor(String author) {
        delegate.isByAuthor(author)
    }

    @Override
    List<TimeBand> getUnfilteredTimeBands() {
        delegate.unfilteredTimeBands.findAll { TimeBand timeBand ->
            filter.matches(timeBand)
        }
    }

}
