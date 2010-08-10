import java.lang.Process

eventCreateWarStart = {warName, File stagingDir ->
	Process process = "svn info".execute()
	File file = new File("${stagingDir}/versionInfo.txt")
	file.text = process.getText()
	println "Adding current revision number information in web-app/versionInfo.txt"
}

eventClasspathStart = {
  classpathSet = false
  rootLoader.addURL(new File('grails-app/migrations').toURI().toURL())
}
