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
package demo.core


import demo.core.dsl.IdeaFlowReader
import demo.core.model.EditorActivity
import demo.core.model.IdeaFlowModel
import demo.core.model.StateChange
import demo.core.model.StateChangeType

import spock.lang.Specification

class IdeaFlowReaderSpec extends Specification {

    void testStringBooleans_ShouldParseAsBooleans() {
        given:
        IdeaFlowReader reader = new IdeaFlowReader(1)
        String content = """
initialize (dateFormat: "yyyy-MM-dd'T'HH:mm:ss", created: '2014-08-19T00:00:00', )
editorActivity(created: '2014-08-19T00:00:00', name: 'somefile.txt', modified: 'false')
"""
        when:
        IdeaFlowModel model = reader.readModel(null, content)

        then:

        model.entityList[0] instanceof EditorActivity
        model.entityList[0].modified == false

    }



    def "should read complete content if line count larger than chunks size"() {
		given:
		IdeaFlowReader reader = new IdeaFlowReader(1)
		String content = """
initialize (dateFormat: "yyyy-MM-dd'T'HH:mm:ss", created: '2014-08-19T00:00:00', )
stateChange (created: '2014-08-19T00:00:00', type: 'startIdeaFlowRecording', )
"""

		when:
		IdeaFlowModel model = reader.readModel(null, content)

		then:
		model.entityList[0] instanceof StateChange
		model.entityList[0].type == StateChangeType.startIdeaFlowRecording
		model.entityList.size() == 1
	}
}
