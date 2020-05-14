@echo off
if "%~1" == "" goto :manually
java -jar TheUnusualEmoji.jar %*
exit /b

:manually
set /p moji="絵文字にする文字列を入力してください:"
set /p offset="ファイル名の終端に識別用のテキストを付ける場合それを入力してください(省略可):"
call :create %moji% %offset%
exit /b

:create
if "%~1" == "" exit /b
java -jar TheUnusualEmoji.jar %1 %2
exit /b
