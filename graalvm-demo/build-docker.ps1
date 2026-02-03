Write-Host "正在构建GraalVM Demo Docker镜像..." -ForegroundColor Green
Write-Host "注意：确保Docker服务正在运行" -ForegroundColor Yellow

$env:MAVEN_OPTS = "--enable-native-access=ALL-UNNAMED"

$buildResult = mvn spring-boot:build-image  -DskipTests

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "Docker镜像构建成功！" -ForegroundColor Green
    Write-Host "您可以通过以下命令运行容器：" -ForegroundColor Green
    Write-Host "docker run -p 8080:8080 yourname/myapp:1.0"
} else {
    Write-Host ""
    Write-Host "构建失败，请检查错误信息" -ForegroundColor Red
}