/*
 * Copyright 2015 New Iron Group, Inc.
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.core.timeline

import org.joda.time.DateTime

abstract class AbstractTimeBand implements TimeBand, TimeEntry {

	TimePosition startPosition
	TimePosition endPosition
	TimeDuration duration

	protected abstract void setActivityStartCreated(DateTime created)

	protected abstract void setActivityEndCreated(DateTime created)

	abstract String getId()

	abstract String getComment()

	void setStartPosition(TimePosition startPosition) {
		this.startPosition = startPosition
		setActivityStartCreated(startPosition.actualTime)
		initDuration()
	}

	void setEndPosition(TimePosition endPosition) {
		this.endPosition = endPosition
		setActivityEndCreated(endPosition.actualTime)
		initDuration()
	}

	TimePosition getTime() {
		return startPosition
	}

	boolean endsBefore(TimeBand timeBand) {
		endPosition.isBefore(timeBand.endPosition)
	}


	private void initDuration() {
		if (startPosition && endPosition) {
			duration = new TimeDuration(endPosition.relativeOffset - startPosition.relativeOffset)
		}
	}

}
