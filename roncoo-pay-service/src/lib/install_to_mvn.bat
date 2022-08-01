@echo off
SETLOCAL
PUSHD %cd%


cd %~dp0
call mvn install:install-file -DgroupId=alipay -DartifactId=alipay-sdk-java20151021120052 -Dversion=1.0 -Dpackaging=jar -Dfile=alipay-sdk-java20151021120052.jar
call mvn install:install-file -DgroupId=alipay -DartifactId=alipay-trade-sdk -Dversion=1.0 -Dpackaging=jar -Dfile=alipay-trade-sdk.jar


POPD
ENDLOCAL
@echo on
:EOF
