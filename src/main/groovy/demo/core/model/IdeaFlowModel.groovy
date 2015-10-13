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
package demo.core.model

import org.joda.time.DateTime

class IdeaFlowModel {

	List<ModelEntity> entityList = []
	boolean isPaused = false
	File file
	DateTime created

	private Conflict activeConflict = null
	private BandStart activeBandStart = null



	IdeaFlowModel(File file, DateTime created) {
		this.file = file
		this.created = created
	}

	IdeaFlowModel() {}

	void addModelEntity(Conflict conflict) {
		addModelEntityInternal(conflict) {
			activeConflict = conflict
		}
	}

	void addModelEntity(Resolution resolution) {
		addModelEntityInternal(resolution) {
			activeConflict = null
		}
	}

	void addModelEntity(BandStart bandStart) {
		addModelEntityInternal(bandStart) {
			activeBandStart = bandStart
		}
	}

	void addModelEntity(BandEnd bandEnd) {
		addModelEntityInternal(bandEnd) {
			activeBandStart = null
		}
	}

	void addModelEntity(ModelEntity modelEntity) {
		addModelEntityInternal(modelEntity, null)
	}

	private void addModelEntityInternal(ModelEntity modelEntity, Closure action) {
		if (modelEntity && !isPaused) {
			entityList.add(modelEntity)
			action?.call()
		}
	}

	int size() {
		entityList.size()
	}

	Conflict getActiveConflict() {
		activeConflict
	}

	BandStart getActiveBandStart() {
		activeBandStart
	}

	boolean isOpenConflict() {
		activeConflict != null
	}

}
