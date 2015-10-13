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

import demo.core.model.BandType
import demo.core.model.Conflict
import demo.core.model.Resolution
import org.joda.time.DateTime


class ConflictBand extends AbstractTimeBand implements Entity {

	private Conflict conflict
	private Resolution resolution

	protected void setActivityStartCreated(DateTime created) {
		conflict.created = created
	}

	protected void setActivityEndCreated(DateTime created) {
		resolution.created = created
	}

	String getId() {
		conflict.getId()
	}

	String getComment() {
		conflict.question
	}

	String getQuestion() {
		conflict.question
	}

	String getAnswer() {
		resolution.answer
	}

	String getMistakeType() {
		conflict.mistakeType
	}

	String getCause() {
		conflict.cause
	}

	String getNotes() {
		conflict.notes
	}

	BandType getBandType() {
		BandType.conflict
	}

	String toString() {
		"ConflictBand: $question"
	}

}
