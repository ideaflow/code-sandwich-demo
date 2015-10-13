package demo.core.chart

import demo.core.dsl.IdeaFlowReader
import demo.core.model.IdeaFlowModel


class DataRetriever {

    List<IdeaFlowModel> retrieveIfmModels() {
        initBaseDir()
        List<File> ifmFiles = retrieveIfmList()
        ifmFiles.collect { file ->
            new IdeaFlowReader().readModel(file)
        }
    }

    List<File> retrieveIfmList() {
        getBaseDir().listFiles( { dir, file -> file ==~ ".*ifm" } as FilenameFilter )
    }

    void initBaseDir() {
        File baseIdeaFlowDir = getBaseDir()
        baseIdeaFlowDir.mkdirs()
    }

    private File getBaseDir() {
        String userHome = System.getProperty("user.home")
        new File("$userHome/.ifmfiles")
    }
}
