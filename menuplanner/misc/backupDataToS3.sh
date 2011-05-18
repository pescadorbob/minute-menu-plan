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
BACK_UP_FOLDER=/home/ubuntu/sqldumps/menuplanner`date +%a_%H`
TAR_FILE=/home/ubuntu/sqldumps/tar_menuplannerDb_`date +%a_%H`".tar.gz"
sudo rm -rf $BACK_UP_FOLDER
mkdir -p $BACK_UP_FOLDER
takeDbBackUp "menuplanner"
takeFolderBackup "/home/ubuntu/mpImages"
cd $BACK_UP_FOLDER
tarBackUpFolder . $TAR_FILE
copyFileToS3 $TAR_FILE

