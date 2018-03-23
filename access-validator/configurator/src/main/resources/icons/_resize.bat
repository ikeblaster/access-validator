@echo off
setlocal enabledelayedexpansion

for %%i in (*.png) do ( 
	set file=%%i
	set filewoext=!file:.png=!

	if "!file!"=="!file:_32=!" (
		magick !file! -scale 88x88 !file!
		magick !file! -scale 32x32 !filewoext!_32.png
	)

)