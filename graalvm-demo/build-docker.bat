@echo off
echo 正在构建GraalVM Demo Docker镜像...
echo 注意：确保Docker服务正在运行

set MAVEN_OPTS=%MAVEN_OPTS% --enable-native-access=ALL-UNNAMED

mvn spring-boot:build-image -DskipTests

if %ERRORLEVEL% == 0 (
    echo.
    echo Docker镜像构建成功！
    echo 您可以通过以下命令运行容器：
    echo docker run -p 8080:8080 yourname/myapp:1.0
) else (
    echo.
    echo 构建失败，请检查错误信息
)
pause