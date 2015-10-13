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
