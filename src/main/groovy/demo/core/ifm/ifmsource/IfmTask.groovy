package demo.core.ifm.ifmsource

import demo.core.model.IdeaFlowModel

interface IfmTask {

    String getTaskName()
    Date getStartDate()

    boolean isByAuthor(String author)

    IdeaFlowModel readIfm()
}
