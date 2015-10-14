package demo.filesource

import demo.core.ifm.dsl.IdeaFlowReader
import demo.core.ifm.ifmsource.IfmTask
import demo.core.model.IdeaFlowModel


class IfmResourceTask implements IfmTask {

    String resourcePath

    IfmResourceTask(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    String getTaskName() {
        return resourcePath
    }

    @Override
    Date getStartDate() {
        return new Date()
    }

    @Override
    boolean isByAuthor(String author) {
        return resourcePath.contains(author)
    }

    @Override
    IdeaFlowModel readIfm() {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath)
        if (inputStream == null) {
            throw new FileNotFoundException("Unable to find: $resourcePath")
        } else {
            new IdeaFlowReader().readModel(inputStream)
        }
    }
}
