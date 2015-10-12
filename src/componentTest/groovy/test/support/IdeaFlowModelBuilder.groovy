package test.support

import com.ideaflow.model.*
import demo.core.model.BandEnd
import demo.core.model.BandStart
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

