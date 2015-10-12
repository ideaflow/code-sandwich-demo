package demo.core.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(callSuper = true)
class Conflict extends ModelEntity {

	String question
	String mistakeType
	String cause
	String notes
    boolean isNested

	Conflict() {}

	Conflict(String question) {
        this(question, false)
	}

    Conflict(String question, boolean isNested) {
   		this.question = question
        this.isNested = isNested
   	}

	String toString() {
		"Conflict: $id, $created, $question"
	}

}
