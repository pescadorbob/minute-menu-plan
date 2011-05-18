A script has been created to take the backups: /home/ubuntu/backupDataToS3.sh 
This script runs every 4 hours
You can use 'crontab -e' to view/change the cron expression

Backup file includes both database & images dumps. Files are copied to S3 bucket /mmpbucket/sqlDumps/
This folder will consist of 42 files(Backup for last 7 days, 6 backups/day). The new file replaces the old file. For e.g. File created at 0000 hrs on Monday is named as tar_menuplannerDb_Mon_00.tar.gz This will replace the file with same name created on last Monday.

You can use this JDBC URL: 'jdbc:mysql://www.minutemenuplan.com:3306/menuplanner?user=root&password=mmdefault'[[BR]]
You will need to add remote access privileges to mysql installed on server. Let me know if you want me to do that. 
