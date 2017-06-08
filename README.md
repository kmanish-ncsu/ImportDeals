# ImportDealsWebApp

Tech stack used:

Spring(DI, MVC) Hibernate(JPA api's) JSP, Servlets Transactions Maven

Steps:

Create a mysql database "progresssoft"
Run the DDL's from DDLs.sql, commit
Clone & Import the project from https://github.com/kmanish-ncsu/Psoft.ImportDeals into IDE (Intellij/Eclipse)
Build & deploy
Home URL : "http://localhost:8080/importdeals/all"
Create 2 folders, one for unprocessed files & one for processed files. Replace the "unprocessed.file.folder" & "processed.file.folder" values respectively in psoft.properties file
Drop the 2 csv files provided into "unprocessed.file.folder" and refresh page. You should see the files under "Unprocessed files"
To process a file, click on "Process". Page displays the time taken to process the file. (100,000 rows take 3 seconds)
To view a processed file's details, click on "Detail"
Click "Home" to go to homepage
To see latest currency count (deals per Ordering currency) click on "Curreny Count" on HomePage; click "Home" to go to HomePage
To reprocess with same files, move all files from "processed.file.folder" to "unprocessed.file.folder" AND run the #TRUNCATE commands given in DDLs.sql
Files are not processed automatically but are triggered for processing on clicking the "Process" button from homepage As of now, it takes 3 seconds to process & populate database for a 100,000 row csv deal file.
