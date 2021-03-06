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

import groovy.transform.EqualsAndHashCode
import org.joda.time.DateTime

@EqualsAndHashCode
class TimePosition {

	final int relativeOffset
	final DateTime actualTime

	TimePosition(DateTime actualTime, int relativeOffset) {
		this.actualTime = actualTime
		this.relativeOffset = relativeOffset
	}

	TimePosition(int hours, int minutes, int seconds) {
		this(DateTime.now(), (hours * 60 * 60) + (minutes * 60) + seconds)
	}

	boolean isBefore(TimePosition position) {
		actualTime.isBefore(position.actualTime)
	}

	boolean isAfter(TimePosition position) {
		actualTime.isAfter(position.actualTime)
	}

	boolean isEqual(TimePosition position) {
		actualTime.isEqual(position.actualTime)
	}

	String toString() {
		"TimePosition: ${relativeOffset}, ${actualTime}"
	}

}
