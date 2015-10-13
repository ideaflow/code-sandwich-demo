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


import demo.core.model.BandEnd
import demo.core.model.BandStart
import demo.core.model.BandType
import org.joda.time.DateTime

class GenericBand extends AbstractTimeBand implements Entity {

	BandStart bandStart
	BandEnd bandEnd

	protected void setActivityStartCreated(DateTime created) {
		bandStart.created = created
	}

	protected void setActivityEndCreated(DateTime created) {
		bandEnd.created = created
	}

	boolean isLinkedToPreviousBand() {
		bandStart.isLinkedToPreviousBand
	}

	BandType getBandType() {
		bandStart.type
	}

	String getId() {
		bandStart.getId()
	}

	String getComment() {
		bandStart.comment
	}

	String toString() {
		"GenericBand: $id, $bandType, $comment"
	}
}
