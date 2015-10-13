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
import org.joda.time.DateTime

class TimeBandContainer extends AbstractTimeBand {

	private GenericBand primaryGenericBand
	private ConflictBand primaryConflict
	private List<TimeBand> timeBands = []

	TimeBandContainer(GenericBand primaryGenericBand) {
		this(primaryGenericBand, null)
	}

	TimeBandContainer(GenericBand primaryGenericBand, ConflictBand primaryConflict) {
		this.primaryConflict = primaryConflict
		this.primaryGenericBand = primaryGenericBand
		addTimeBand(primaryGenericBand)
		if (primaryConflict != null) {
			addTimeBand(primaryConflict)
		}
	}

	GenericBand getPrimaryGenericBand() {
		primaryGenericBand
	}

	ConflictBand getPrimaryConflict() {
		primaryConflict
	}

	void addTimeBand(TimeBand timeBand) {
		timeBands.add(timeBand)
	}

	List<TimeBand> getTimeBands() {
		timeBands.clone() as List
	}

	boolean isEmpty() {
		timeBands.size() == (primaryConflict ? 2 : 1)
	}

	@Override
	protected void setActivityStartCreated(DateTime created) {
		// TODO: should this explode?
	}

	@Override
	protected void setActivityEndCreated(DateTime created) {
		// TODO: should this explode?
	}

	@Override
	BandType getBandType() {
		(primaryConflict ?: primaryGenericBand).bandType
	}

	@Override
	String getId() {
		"${primaryGenericBand.id}-container"
	}

	@Override
	String getComment() {
		primaryGenericBand.comment
	}

	String toString() {
		"Container: ${primaryGenericBand.comment}"
	}
}
