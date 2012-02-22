import org.apache.commons.io.FileUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder

includeTargets << grailsScript("Init")
includeTargets << grailsScript('_GrailsPackage')

target(main: "Copies the war to installed tomcat.  Starts tomcat") {
    depends(createConfig)
    File source = new File("target","${ConfigurationHolder.config.grails.project.war.file}")

    if(source.exists()){
        File destination = new File("${tomcatDir}/webapps","ROOT.war");
        FileUtils.copyFile(source,destination);
        Process process = "${tomcatDir}/bin/startup.bat".execute()

    }
}

setDefaultTarget(main)

