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

import demo.core.model.*

import org.joda.time.DateTime
import org.reflections.Reflections

class FixtureSupport {

	static final String FILE = "file"
	static final String OTHER_FILE = "otherFile"
	static final String FILE1 = "file1"
	static final String FILE2 = "file2"
	static final String FILE3 = "file3"
	static final String FILE4 = "file4"


	static final long SHORT_DELAY = 5;
	static final long LONG_DELAY = 999999;

	static final long NOW
	static final long TIME1
	static final long TIME2
	static final long TIME3
	static final long TIME4

	static {
		def cal = Calendar.getInstance()
		cal.set(Calendar.MILLISECOND, 0)
		NOW = cal.getTime().time

		TIME1 = NOW + LONG_DELAY
		TIME2 = TIME1 + LONG_DELAY
		TIME3 = TIME2 + LONG_DELAY
		TIME4 = TIME3 + LONG_DELAY

	}


	Note createNote() {
		createNote("note")
	}

	Note createNote(String comment) {
		createNote(comment, NOW)
	}

	Note createNote(String comment, long time) {
		Note note = new Note(comment)
		setCreated(note, time)
		note.comment = comment
		note
	}

	StateChange createStateChange() {
		createStateChange(StateChangeType.startIdeaFlowRecording)
	}

	StateChange createStateChange(StateChangeType type) {
		createStateChange(type, NOW)
	}

	StateChange createStateChange(StateChangeType type, long time) {
		StateChange event = new StateChange(type)
		setCreated(event, time)
		event
	}

	Conflict createConflict() {
		createConflict(NOW)
	}

	Conflict createConflict(long time) {
		createConflict(time, 'question')
	}

	Conflict createConflict(long time, String question) {
		Conflict conflict = new Conflict(question)
		setCreated(conflict, time)
		conflict
	}

	Resolution createResolution() {
		createResolution(NOW)
	}

	Resolution createResolution(long time) {
		createResolution(time, 'answer')
	}

	Resolution createResolution(long time, String answer) {
		Resolution resolution = new Resolution(answer)
		setCreated(resolution, time)
		resolution
	}

	private void setCreated(def item, long time) {
		item.created = new DateTime(time)
	}

	EditorActivity createEditorActivity(String name) {
		createEditorActivity(name, NOW)
	}

	EditorActivity createEditorActivity(String name, long time) {
		createEditorActivity(name, 5, time)
	}

	EditorActivity createEditorActivity(String name, int duration, long time) {
		new EditorActivity(new DateTime(time), name, false, duration)
	}

	BandStart createBandStart() {
		createBandStart(BandType.learning, NOW)
	}

	BandStart createBandStart(BandType type, long time) {
		BandStart start = new BandStart(type, 'comment', false)
		start.created = new DateTime(time)
		start
	}

	BandEnd createBandEnd() {
		createBandEnd(BandType.learning, NOW)
	}

	BandEnd createBandEnd(BandType type, long time) {
		BandEnd end = new BandEnd(type)
		end.created = new DateTime(time)
		end
	}

	Idle createIdle() {
		createIdle(NOW, 'comment', 5)
	}

	Idle createIdle(long time, String comment, int duration) {
		new Idle(new DateTime(time), comment, duration)
	}


	List<ModelEntity> getModelEntitySubClassInstances() {
		Reflections reflections = new Reflections(ModelEntity.package.name)
		reflections.getSubTypesOf(ModelEntity).collect { Class subType ->
			subType.newInstance() as ModelEntity
		}
	}

}
