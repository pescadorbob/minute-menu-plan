set -e
USE_SVN="TRUE"
EXPECTED_ARGS=1
ARG_VALUE=$1
FILE_PRE="./preDeploy.sh"
FILE_POST="./postDeploy.sh"
if [ -e $FILE_PRE ];
then
	echo "Now calling pre deployment script"
	$FILE_PRE
fi
cd $GRAILS_PROJECT_HOME
if [ $# == $EXPECTED_ARGS ] && [ $ARG_VALUE == "--no-svn" ];then
	USE_SVN="FALSE"
	echo Not Using SVN
fi
if [ "$USE_SVN" = "TRUE" ];then
	svn revert -R .
	svn up
fi
echo "Now creating the WAR file"
if [ -e /tmp/ROOT.war ]; then
	sudo rm -rf /tmp/ROOT.war
fi
#/opt/grails-$GRAILS_VERSION/bin/grails upgrade --non-interactive
/opt/grails-$GRAILS_VERSION/bin/grails prod war /tmp/ROOT.war --non-interactive
sudo rm -rf /var/lib/tomcat6/webapps/ROOT*
echo "Copying WAR to Tomcat webapps"
sudo cp /tmp/ROOT.war /var/lib/tomcat6/webapps
echo "Now restarting Tomcat server"
sudo /etc/init.d/tomcat6 restart
if [ -e $FILE_POST ];
then
	echo "Now calling post deployment script"
	$FILE_POST
fi
