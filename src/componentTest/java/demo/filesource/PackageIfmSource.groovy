package demo.filesource

import demo.core.ifm.ifmsource.IfmSource
import demo.core.ifm.ifmsource.IfmTask
import org.springframework.stereotype.Component

@Component
class PackageIfmSource implements IfmSource {

    private List<IfmTask> ifmTaskList

    PackageIfmSource() {
        init()
    }

    void init() {
        List<String> resourcePaths = [
                "ex_tagged_experimentpain.ifm",
                "ex_timebands_two_each.ifm"
        ]

        ifmTaskList = resourcePaths.collect() { resourcePath ->
            new IfmResourceTask(resourcePath)
        }
    }

    @Override
    List<IfmTask> allIfmTasks() {
        return ifmTaskList
    }
}
