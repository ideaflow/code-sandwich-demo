package demo.filesource

import demo.core.ifm.ifmsource.IfmFileTask
import demo.core.ifm.ifmsource.IfmSource
import demo.core.ifm.ifmsource.IfmTask

class PackageIfmSource implements IfmSource {

    private List<IfmTask> ifmTaskList

    PackageIfmSource(Class testClass) {
        ifmTaskList = getIfmTaskList(testClass)
    }

    private List<IfmTask> getIfmTaskList(Class testClass) {
        File baseDir = new File(testClass.getResource("/").path)
        File testClassDir = new File(testClass.getResource(".").path)
        List<File> ifmFiles = testClassDir.listFiles().findAll { File file ->
            file.name.endsWith(".ifm")
        }
        ifmFiles.collect { File file ->
            new IfmFileTask(baseDir, file)
        }
    }

    @Override
    List<IfmTask> allIfmTasks() {
        return ifmTaskList
    }
}
