package demo.core.chart

import groovy.io.FileType


class IfmFileManager {

    List<File> fetchAllIfmFiles() {
        initBaseDir()
        return retrieveIfmList()
    }

    private List<File> retrieveIfmList() {
        List<File> files = []
        getBaseDir().eachFileRecurse(FileType.FILES) { file ->
            if(file.name.endsWith('.ifm')) {
                files.add(file)
            }
        }
        return files;
    }

    private void initBaseDir() {
        File baseIdeaFlowDir = getBaseDir()
        baseIdeaFlowDir.mkdirs()
    }

    private File getBaseDir() {
        String userHome = System.getProperty("user.home")
        new File("$userHome/.ifmfiles")
    }
}
