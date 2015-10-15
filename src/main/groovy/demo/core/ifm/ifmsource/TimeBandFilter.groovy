package demo.core.ifm.ifmsource

import demo.core.timeline.TimeBand

interface TimeBandFilter {

    boolean matches(TimeBand timeBand)

}
