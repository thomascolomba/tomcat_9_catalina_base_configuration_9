call ant -buildfile source_code\mymbean\build.xml clean compile jar
call copy .\source_code\mymbean\build\jar\MyBean.jar .\lib\
call ant -buildfile source_code\mymbeanreader\build.xml clean compile dist
call copy .\source_code\mymbeanreader\dist\mymbeanreader.war .\webapps\
call ant -buildfile source_code\mymbeanwriter\build.xml clean compile dist
call copy .\source_code\mymbeanwriter\dist\mymbeanwriter.war .\webapps\