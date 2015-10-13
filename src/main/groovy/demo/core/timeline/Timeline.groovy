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

class Timeline {
	List<ConflictBand> conflictBands = []
	List<GenericBand> genericBands = []
	List<TimeBandContainer> timeBandContainers = []
	List<Event> events = []
	List<ActivityDetail> activityDetails = []
	List<IdleDetail> idleDetails = []

	void addGenericBand(GenericBand timeBand) {
		genericBands.add(timeBand)
	}

	void addActivityDetail(ActivityDetail activityDetail) {
		activityDetails.add(activityDetail)
	}

	void addIdleDetail(IdleDetail idleDetail) {
		idleDetails.add(idleDetail)
	}

	void addConflictBand(ConflictBand conflictBand) {
		conflictBands.add(conflictBand)
	}

	void addEvent(Event event) {
		events.add(event)
	}

	void addTimeBandContainer(TimeBandContainer timeBandContainer) {
		timeBandContainers.add(timeBandContainer)
	}

	List<TimeBand> getTimeBands() {
		sortTimeBands(conflictBands + genericBands + timeBandContainers)
	}

	List<TimeEntry> getSequencedTimelineDetail() {
		sortTimeEntries(conflictBands + genericBands + events + activityDetails + idleDetails)
	}

	List<TimeEntry> getSequencedTimeline() {
		sortTimeEntries(conflictBands + genericBands + timeBandContainers + events)
	}

	private List<TimeBand> sortTimeBands(List<TimeBand> timeBands) {
		timeBands.sort {
			TimeBand timeBand ->
				timeBand.startPosition.relativeOffset
		}
	}

	private List<TimeEntry> sortTimeEntries(List<TimeEntry> timeEntries) {
		timeEntries.sort {
			TimeEntry timeEntry ->
				timeEntry.time.relativeOffset
		}
	}

}
