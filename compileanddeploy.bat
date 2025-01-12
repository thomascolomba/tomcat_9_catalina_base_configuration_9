call ant -buildfile source_code\mybean\build.xml clean compile jar
call copy .\source_code\mybean\build\jar\MyBean.jar .\lib\
call ant -buildfile source_code\mybeanreader\build.xml clean compile dist
call copy .\source_code\mybeanreader\dist\mybeanreader.war .\webapps\
call ant -buildfile source_code\mybeanwriter\build.xml clean compile dist
call copy .\source_code\mybeanwriter\dist\mybeanwriter.war .\webapps\