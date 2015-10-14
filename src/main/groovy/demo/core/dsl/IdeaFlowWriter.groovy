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

class IdeaFlowWriter {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

	private BufferedWriter writer
	private DateTimeFormatter dateFormat

	IdeaFlowWriter(Writer writer) {
		this.writer = createBufferedWriter(writer)
		this.dateFormat = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT)
	}

	private BufferedWriter createBufferedWriter(Writer writer) {
		writer instanceof BufferedWriter ? writer as BufferedWriter : new BufferedWriter(writer)
	}

	void close() {
		writer.close()
	}

	void writeModel(IdeaFlowModel model) {
		writeInitialization(model.created)
		model.entityList.each { entity ->
			write(entity);
		}
	}

	void writeInitialization(DateTime created) {
		writer.print "initialize ("
		writer.print "dateFormat: \"${DEFAULT_DATE_FORMAT}\", "
		writer.print "created: '${dateFormat.print(created)}', "
		writer.println ")"
		writer.flush()
	}

	void write(StateChange event) {
		writeItem('stateChange', event, ['created', 'type'])
	}

	void write(Note note) {
		writeItem('note', note, ['created', 'comment'])
	}

	void write(Conflict conflict) {
		writeItem('conflict', conflict, ['created', 'mistakeType', 'question', 'cause', 'notes', 'isNested'])
	}

	void write(Resolution resolution) {
		writeItem('resolution', resolution, ['created', 'answer'])
	}

	void write(EditorActivity editorActivity) {
		writeItem('editorActivity', editorActivity, ['created', 'name', 'duration', 'modified'])
	}

	void write(BandStart bandStart) {
		writeItem('bandStart', bandStart, ['created', 'type', 'comment', 'isLinkedToPreviousBand'])
	}

	void write(BandEnd bandEnd) {
		writeItem('bandEnd', bandEnd, ['created', 'type'])
	}

	void write(Idle idle) {
		writeItem('idle', idle, ['created', 'duration', 'comment'])
	}

	private void writeItem(String name, ModelEntity entity, List orderedKeyList) {
		Map properties = getPropertiesToWrite(entity)
		assertActualPropertyKeysMatchDeclaredKeyList(entity, properties, orderedKeyList)

		writer.print "${name} ("
		orderedKeyList.each { String key ->
			writeItemEntry(key, properties[key])
		}
		writer.println ")"
		writer.flush()

	}

	private String sanitizeString(String string) {
		string.replace("\\", "\\\\")
				.replace("'", "\\'")
	}

	private void writeItemEntry(String key, value) {
		if (value != null) {
			if (value instanceof String) {
				writer.print "${key}: '''${sanitizeString(value)}''', "
			} else if (value instanceof Number) {
				writer.print "${key}: ${value}, "
			} else {
				if (value instanceof DateTime) {
					value = dateFormat.print(value)
				}
				writer.print "${key}: '${value}', "
			}
		}
	}

	private void assertActualPropertyKeysMatchDeclaredKeyList(ModelEntity entity, Map map, List declaredKeylist) {
		String simpleName = entity.class.simpleName
		List actualKeyList = map.keySet().asList()

		List additionalProperties = actualKeyList - declaredKeylist
		if (additionalProperties) {
			throw new RuntimeException("Object ${simpleName} declares unknown properties=${additionalProperties}.  Add properties to key list of method IdeaFlowWriter:write(${simpleName})")
		}

		List missingProperties = declaredKeylist - actualKeyList
		if (missingProperties) {
			throw new RuntimeException("IdeaFlowWriter:write(${simpleName}) is configured to write out properties=${missingProperties} which are not declared in corresponding class.")
		}
	}

	private Map getPropertiesToWrite(ModelEntity entity) {
		Map properties = entity.getProperties()
		properties.remove('id')
		properties.remove('class')
		properties.remove('metaClass')
		properties
	}

}
