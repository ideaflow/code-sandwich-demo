package demo.core.ifm.ifmsource

import demo.core.ifm.dsl.IdeaFlowReader
import demo.core.model.IdeaFlowModel
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class IfmFileTask implements IfmTask {

    String taskName
    Date startDate

    private String relativePath
    private File ifmFile

    IfmFileTask (File baseDir, File ifmFile) {
        this.relativePath = ifmFile.absolutePath.substring(baseDir.absolutePath.length())
        this.taskName = ifmFile.name
        this.startDate = new Date(ifmFile.lastModified())
        this.ifmFile = ifmFile
    }

    IdeaFlowModel readIfm() {
        new IdeaFlowReader().readModel(ifmFile)
    }

    boolean isByAuthor(String author) {
        relativePath.contains(author)
    }
}
