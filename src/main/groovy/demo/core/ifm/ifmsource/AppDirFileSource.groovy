package demo.core.ifm.ifmsource

import groovy.io.FileType
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
class AppDirFileSource implements IfmSource {

    private List<IfmTask> ifmTaskList

    AppDirFileSource() {
        File baseIdeaFlowDir = getBaseDir()
        baseIdeaFlowDir.mkdirs()
        initIfmTaskList()
    }

    List<IfmTask> allIfmTasks() {
        ifmTaskList
    }

    File getBaseDir() {
        new File("./.ifmfiles")
    }

    private void initIfmTaskList() {
        ifmTaskList = []
        log.info("Base IFM File directory loading from: ${getBaseDir().absolutePath}");
        getBaseDir().eachFileRecurse(FileType.FILES) { file ->
            if(file.name.endsWith('.ifm')) {
                ifmTaskList.add(new IfmFileTask(baseDir, file))
            }
        }
        log.info(ifmTaskList.size() + " files loaded.")
    }

}
