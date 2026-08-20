@echo off
cd /d E:\ETF\PIA\PrintingHouse
call backend\mvnw.cmd -f backend\pom.xml spring-boot:run
pause
