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

import com.ideaflow.model.Conflict
import com.ideaflow.model.ModelEntity
import demo.core.dsl.IdeaFlowWriter
import demo.core.model.Conflict
import demo.core.model.ModelEntity
import spock.lang.Specification
import test.support.FixtureSupport

@Mixin(FixtureSupport)
class IdeaFlowWriterSpec extends Specification {

	static class DummyEvent extends ModelEntity {
	}


	private IdeaFlowWriter writer = new IdeaFlowWriter(new StringWriter())

    void testWrite_ShouldErrorIfObjectContainsAdditionalProperty() {
        given:
		Conflict conflict = createConflict()
		conflict.metaClass.newProperty = "value"

        when:
        writer.write(conflict)

        then:
        RuntimeException ex = thrown()
        assert ex.message.contains("Object Conflict declares unknown properties=[newProperty]")
	}

    void testWrite_ShouldErrorIfObjectMissingDeclaredProperty() {
        given:
		DummyEvent dummy = new DummyEvent()

        when:
        writer.writeItem('dummy', dummy, ['created', 'missingProperty'])

        then:
        RuntimeException ex = thrown()
        assert ex.message == "IdeaFlowWriter:write(DummyEvent) is configured to write out properties=[missingProperty] which are not declared in corresponding class."
	}

}
