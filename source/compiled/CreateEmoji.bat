@echo off
if "%~1" == "" goto :manually
java -jar TheUnusualEmoji.jar %*
exit /b

:manually
set /p moji="�G�����ɂ��镶�������͂��Ă�������:"
set /p offset="�t�@�C�����̏I�[�Ɏ��ʗp�̃e�L�X�g��t����ꍇ�������͂��Ă�������(�ȗ���):"
call :create %moji% %offset%
exit /b

:create
if "%~1" == "" exit /b
java -jar TheUnusualEmoji.jar %1 %2
exit /b
