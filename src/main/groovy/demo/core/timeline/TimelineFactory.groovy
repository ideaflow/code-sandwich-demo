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

import com.ideaflow.model.*
import demo.core.model.BandEnd
import demo.core.model.BandStart
import demo.core.model.BandType
import demo.core.model.Conflict
import demo.core.model.EditorActivity
import demo.core.model.IdeaFlowModel
import demo.core.model.Idle
import demo.core.model.ModelEntity
import demo.core.model.Note
import demo.core.model.Resolution
import demo.core.model.StateChange
import org.joda.time.DateTime

class TimelineFactory {

	Timeline timeline

	Timeline create(IdeaFlowModel ifm) {
		TimelineBuilder builder = new TimelineBuilder()
		ifm.entityList.each { ModelEntity entity ->
			builder.addEntity(entity)
		}
		builder.timeline
	}

	private static class TimelineBuilder {
		Timeline timeline = new Timeline()
		Map<BandType, GenericBand> activeGenericBands = [:]
		ConflictBand activeConflictBand
		int relativeTime = 0

		void addEntity(BandStart bandStart) {
			// what if band is already started???
			GenericBand genericBand = new GenericBand()
			genericBand.bandStart = bandStart
			genericBand.startPosition = createTimePositionWithRelativeTimeAsOffset(bandStart.created)
			activeGenericBands.put(bandStart.type, genericBand)
		}

		void addEntity(BandEnd bandEnd) {
			GenericBand genericBand = activeGenericBands.remove(bandEnd.type)

			if (genericBand != null) {
				genericBand.bandEnd = bandEnd
				genericBand.endPosition = createTimePositionWithRelativeTimeAsOffset(bandEnd.created)

				timeline.addGenericBand(genericBand)
			} else {
				// TODO: expode???
			}
		}

		void addEntity(EditorActivity editorActivity) {
			TimePosition time = createTimePositionWithRelativeTimeAsOffset(editorActivity.created)
			ActivityDetail activityDetail = new ActivityDetail(time, editorActivity)
			timeline.addActivityDetail(activityDetail)
			relativeTime += editorActivity.duration
		}

		void addEntity(Conflict conflict) {
			// TODO: if already set, exploded!!
			activeConflictBand = new ConflictBand()
			activeConflictBand.conflict = conflict
			activeConflictBand.startPosition = createTimePositionWithRelativeTimeAsOffset(conflict.created)
		}

		private TimePosition createTimePositionWithRelativeTimeAsOffset(DateTime actualTime) {
			new TimePosition(actualTime, relativeTime)
		}

		void addEntity(Resolution resolution) {
			// TODO: if no active conflict, explode!!
			activeConflictBand.resolution = resolution
			activeConflictBand.endPosition = createTimePositionWithRelativeTimeAsOffset(resolution.created)
			timeline.addConflictBand(activeConflictBand)
		}

		void addEntity(Note note) {
			TimePosition timePosition = createTimePositionWithRelativeTimeAsOffset(note.created)
			timeline.addEvent(new Event(timePosition, note))
		}

		void addEntity(Idle idle) {
			TimePosition timePosition = createTimePositionWithRelativeTimeAsOffset(idle.created)
			timeline.addIdleDetail(new IdleDetail(timePosition, idle) )
		}

		void addEntity(StateChange stateChange) {
		}

		void addEntity(ModelEntity unknown) {
			throw new RuntimeException("Unknown ModelEntity type ${unknown?.class}")
		}

	}

}
