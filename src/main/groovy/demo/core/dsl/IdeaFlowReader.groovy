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
package demo.core.dsl

import demo.core.model.BandEnd
import demo.core.model.BandStart
import demo.core.model.Conflict
import demo.core.model.EditorActivity
import demo.core.model.IdeaFlowModel
import demo.core.model.Idle
import demo.core.model.ModelEntity
import demo.core.model.Note
import demo.core.model.Resolution
import demo.core.model.StateChange
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class IdeaFlowReader {

	/**
	 * If the dsl content is too large, groovy will fail to compile the script due to jvm method
	 * length restrictions (java.lang.ClassFormatError: Invalid method Code length xxx) so chunk
	 * the content by lines and load it one block at a time.
	 */
	private static final int DEFAULT_CHUNK_SIZE = 500
	private static final String LINE_SEPARATOR = System.getProperty("line.separator")

	private int chunkSize

	IdeaFlowReader() {
		this(DEFAULT_CHUNK_SIZE)
	}

	IdeaFlowReader(int chunkSize) {
		this.chunkSize = chunkSize
	}

	IdeaFlowModel readModel(File modelFile) {
		readModel(modelFile, modelFile.text)
	}

	IdeaFlowModel readModel(File modelFile, String dslContents) {
		IdeaFlowModelLoader loader = new IdeaFlowModelLoader(modelFile)

		for (List<String> dslContentChunk : dslContents.readLines().collate(chunkSize)) {
			String partialDslContent = dslContentChunk.join(LINE_SEPARATOR)
			readPartialModel(loader, partialDslContent)
		}
		loader.model
	}

	private void readPartialModel(IdeaFlowModelLoader loader, String partialDslContent) {
		String wrappedDslContent = "ideaFlowModel {${partialDslContent}}"
		Script dslScript = new GroovyShell().parse(wrappedDslContent)

		dslScript.metaClass = createEMC(dslScript.class, { ExpandoMetaClass emc ->
			emc."ideaFlowModel" { Closure cl ->
				cl.delegate = loader
				cl.resolveStrategy = Closure.DELEGATE_FIRST

				cl()
			}
		})
		dslScript.run()
	}

	private ExpandoMetaClass createEMC(Class clazz, Closure cl) {
		ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)

		cl(emc)

		emc.initialize()
		return emc
	}

	private static class IdeaFlowModelLoader {

		IdeaFlowModel model
		private int entityIdCounter
		private DateTimeFormatter dateFormat

		IdeaFlowModelLoader(File modelFile) {
			model = new IdeaFlowModel(modelFile, new DateTime())
			entityIdCounter = 1
		}

		def initialize(Map initializeMap) {
			String dateFormatString = initializeMap['dateFormat']
			dateFormat = DateTimeFormat.forPattern(dateFormatString)

			String createdDateString = initializeMap['created'] as String
			model.created = dateFormat.parseDateTime(createdDateString)
		}

		private void replaceValueWithBooleanIfExists(Map map, String key) {
			if (map.containsKey(key)) {
				String value = map[key]
				map[key] = Boolean.valueOf(value)
			}
		}

		def editorActivity(Map editorActivityMap) {
			replaceValueWithBooleanIfExists(editorActivityMap, "modified")
			addModelEntity(EditorActivity, editorActivityMap)
		}

		def stateChange(Map eventMap) {
			addModelEntity(StateChange, eventMap)
		}

		def note(Map noteMap) {
			addModelEntity(Note, noteMap)
		}

		def conflict(Map eventMap) {
			addModelEntity(Conflict, eventMap)
		}

		def resolution(Map resolutionMap) {
			addModelEntity(Resolution, resolutionMap)
		}

		def bandStart(Map bandStartMap) {
			replaceValueWithBooleanIfExists(bandStartMap, "isLinkedToPreviousBand")
			addModelEntity(BandStart, bandStartMap)
		}

		def bandEnd(Map bandEndMap) {
			addModelEntity(BandEnd, bandEndMap)
		}

		def idle(Map idleMap) {
			addModelEntity(Idle, idleMap)
		}

		private void addModelEntity(Class type, Map initialMap) {
			Map constructorMap = createConstructorMap(initialMap)
			ModelEntity entity = type.newInstance(constructorMap)
			model.addModelEntity(entity)
		}

		private Map createConstructorMap(Map initialMap) {
			Map constructorMap = initialMap.clone() as Map
            constructorMap.each { key, value ->
                String strValue = (value as String).toLowerCase()
                if (strValue == 'true' || strValue == 'false') {
                    constructorMap[key] = Boolean.valueOf(strValue)
                }
            }
			constructorMap['created'] = toDate(constructorMap['created'] as String)
			constructorMap['id'] = entityIdCounter++
			constructorMap
		}

		private DateTime toDate(String dateString) {
			if (dateFormat == null) {
				throw new RuntimeException("Invalid IdeaFlowMap, initialize should be the first entry in the map")
			}

			dateFormat.parseDateTime(dateString)
		}

	}
}
