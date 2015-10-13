package demo.core.timeline

import demo.core.model.BandType

interface TimeBand {

	TimePosition getStartPosition()

	TimePosition getEndPosition()

	TimeDuration getDuration()

	void setStartPosition(TimePosition startPosition)

	void setEndPosition(TimePosition endPosition)

	BandType getBandType()

	String getId()

	String getComment()

}
