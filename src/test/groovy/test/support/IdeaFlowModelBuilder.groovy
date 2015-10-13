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
package test.support

import com.ideaflow.model.*
import demo.core.model.BandEnd
import demo.core.model.BandStart
import demo.core.model.BandType
import demo.core.model.Conflict
import demo.core.model.EditorActivity
import demo.core.model.IdeaFlowModel
import demo.core.model.Idle
import demo.core.model.Note
import demo.core.model.Resolution
import demo.core.model.StateChange
import org.joda.time.DateTime

class IdeaFlowModelBuilder {

	IdeaFlowModel ifm = new IdeaFlowModel()
	FixtureSupport fs = new FixtureSupport()

	static IdeaFlowModelBuilder create() {
		return new IdeaFlowModelBuilder()
	}

	IdeaFlowModelBuilder defaults() {
		ifm.created = new DateTime(fs.NOW)
		ifm.file = new File(fs.FILE)
		return this
	}

	IdeaFlowModelBuilder addBand(BandType bandType, int totalDuration) {
		addBandStart(new BandStart(bandType, "comment", false))
		addEditorActivity((int)totalDuration/2)
		addEditorActivity((int)totalDuration/2)
		addBandEnd(new BandEnd(bandType))
	}

	IdeaFlowModelBuilder addBandEnd(BandEnd bandEnd) {
		ifm.addModelEntity(bandEnd)
		return this
	}

	IdeaFlowModelBuilder addIdle(Idle idle) {
		ifm.addModelEntity(idle)
		return this
	}

	IdeaFlowModelBuilder addBandStart(BandStart bandStart) {
		ifm.addModelEntity(bandStart)
		return this
	}

	IdeaFlowModelBuilder addConflict(Conflict conflict) {
		ifm.addModelEntity(conflict)
		return this
	}

	IdeaFlowModelBuilder addResolution(Resolution resolution) {
		ifm.addModelEntity(resolution)
		return this
	}

	IdeaFlowModelBuilder addEditorActivity(int duration) {
		EditorActivity activity = fs.createEditorActivity('name', duration, fs.NOW)
		return addEditorActivity(activity)
	}

	IdeaFlowModelBuilder addEditorActivity(EditorActivity activity) {
		ifm.addModelEntity(activity)
		return this
	}

	IdeaFlowModelBuilder addNote(Note note) {
		ifm.addModelEntity(note)
		return this
	}

	IdeaFlowModelBuilder addStateChange(StateChange stateChange) {
		ifm.addModelEntity(stateChange)
		return this
	}

	IdeaFlowModel build() {
		return ifm
	}
}

