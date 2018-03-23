@echo off

call :make_composite if_database_299089.png, if_folder_299060.png
call :make_composite if_database_299089.png, if_sign-add_299068.png
call :make_composite if_database_299089.png, if_sign-remove.png
call :make_composite if_database_299089.png, if_sign-check_299110.png


exit /B

:make_composite
composite -filter box -geometry 51x51+37+37 %2 %1 %~n1%~n2.png
exit /B