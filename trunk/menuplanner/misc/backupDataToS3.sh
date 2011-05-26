takeDbBackUp() {
	DB_NAME=$1
	BACKUP_FILE_NAME=$DB_NAME"Db_"`date +%a_%H`
	echo $BACKUP_FILE_NAME
	mysqldump --user=root --password=mmdefault $DB_NAME > $BACK_UP_FOLDER/$BACKUP_FILE_NAME.sql;
}
takeFolderBackup() {
	sudo cp -R $1 $BACK_UP_FOLDER/
}
takeCommandBackUp(){
	$1 > $BACK_UP_FOLDER/$2
}
tarBackUpFolder() {
	tar czf $2 $1
}
copyFileToS3() {
	s3cmd put $1 s3://mmpbucket/sqlDumps/
}
log() {
	echo $1 >> $LOG_FILE
}

LOG_FILE=~/backupLogs/logFile.txt
log "Back up Started at `date`"
BACK_UP_FOLDER=/home/ubuntu/sqldumps/menuplanner`date +%a_%H`
TAR_FILE=/home/ubuntu/sqldumps/tar_menuplannerDb_`date +%a_%H`".tar.gz"
sudo rm -rf $BACK_UP_FOLDER
mkdir -p $BACK_UP_FOLDER
takeDbBackUp "menuplanner"
takeFolderBackup "/home/ubuntu/mpImages"
log "Back ups moved to $BACK_UP_FOLDER at `date`"
cd $BACK_UP_FOLDER
log "creating tar at `date`"
tarBackUpFolder . $TAR_FILE
log "Copying tar to s3 at `date`"
copyFileToS3 $TAR_FILE
log "Back up Ended at `date`"
