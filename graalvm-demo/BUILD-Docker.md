# 构建Docker镜像指南

## 问题说明

在执行 `mvn spring-boot:build-image` 命令时可能会遇到以下问题：

1. 参数格式错误：`-Dspring-boot.build-image.imageName=yourname/myapp:1.0` 的格式不正确
2. Java Native Access警告：某些库需要额外的权限
3. Checksum验证失败：这通常是网络问题，不影响实际构建

## 解决方案

### 方法一：使用预设脚本（推荐）

根据您的操作系统选择对应的脚本：

**Windows CMD:**
```cmd
build-docker.bat
```

**PowerShell:**
```powershell
.\build-docker.ps1
```

### 方法二：直接使用Maven命令

在项目根目录下执行以下命令：

```bash
mvn spring-boot:build-image -DskipTests
```

如果要自定义镜像名称，可以使用：
```bash
mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=yourname/myapp:1.0
```

## 配置说明

项目中的 `pom.xml` 已经配置了以下构建选项：

```xml
<image>
    <builder>paketobuildpacks/builder-jammy-base:latest</builder>
    <name>yourname/myapp:1.0</name>
</image>
```

## 运行构建后的镜像

构建成功后，您可以使用以下命令运行容器：

```bash
docker run -p 8080:8080 yourname/myapp:1.0
```

然后在浏览器中访问 `http://localhost:8080` 即可看到应用运行。

## 注意事项

1. 确保Docker服务正在运行
2. 可能需要一些时间来下载构建器和依赖项
3. 如果遇到权限问题，可能需要以管理员身份运行命令提示符