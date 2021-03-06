package demo.core.ifm.ifmsource

import groovy.io.FileType
import org.springframework.stereotype.Component

@Component
class HomeDirFileSource implements IfmSource {

    private List<IfmTask> ifmTaskList

    HomeDirFileSource() {
        init()
    }

    List<IfmTask> allIfmTasks() {
        ifmTaskList
    }

    File getBaseDir() {
        String userHome = System.getProperty("user.home")
        new File("$userHome/.ifmfiles")
    }

    private void init() {
        File baseIdeaFlowDir = getBaseDir()
        baseIdeaFlowDir.mkdirs()
        initIfmTaskList()
    }

    private void initIfmTaskList() {
        ifmTaskList = []
        getBaseDir().eachFileRecurse(FileType.FILES) { file ->
            if(file.name.endsWith('.ifm')) {
                ifmTaskList.add(new IfmFileTask(baseDir, file))
            }
        }
    }

}
