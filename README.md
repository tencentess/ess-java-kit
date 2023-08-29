# 腾讯电子签企业版API接入工具包

## 项目说明
项目通过maven引入了腾讯云sdk，补充了调用电子签企业版API所需要的内容，并提供了调用的样例。使用前请先在项目中导入腾讯云sdk。

```
<dependency>
    <!-- go to https://search.maven.org/search?q=tencentcloud-sdk-java and get the latest version. -->
    <!-- 请到https://search.maven.org/search?q=tencentcloud-sdk-java查询所有版本 -->
    <groupId>com.tencentcloudapi</groupId>
    <artifactId>tencentcloud-sdk-java-ess</artifactId>
    <version>3.1.528</version>
</dependency>
```


## 通过 maven 安装腾讯云sdk
通过 maven 获取腾讯云sdk是使用 Java SDK 的推荐方法，maven 是 Java 的依赖管理工具，支持您项目所需的依赖项，并将其安装到项目中。关于 maven 详细可参考 maven 官网。
1. 安装maven

 1.1  windows环境请访问[maven官网](https://maven.apache.org/download.cgi)下载安装包安装。

   设置 Maven 环境变量 
   
添加环境变量 MAVEN_HOME：
   右键 "计算机"，选择 "属性"，之后点击 "高级系统设置"，点击"环境变量"，来设置环境变量，有以下系统变量需要配置：

新建系统变量 MAVEN_HOME，变量值为安装目录如：E:\Maven\apache-maven-3.3.9

编辑系统变量 Path，添加变量值：;%MAVEN_HOME%\bin

注意：注意多个值之间需要有分号隔开，然后点击确定。



 1.2  Linux环境在命令行中执行以下命令安装。
```
# wget http://mirrors.hust.edu.cn/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
# tar -xvf  apache-maven-3.3.9-bin.tar.gz
# sudo mv -f apache-maven-3.3.9 /usr/local/
```
编辑 /etc/profile 文件 sudo vim /etc/profile，在文件末尾添加如下代码：
```
export MAVEN_HOME=/usr/local/apache-maven-3.3.9
export PATH=${PATH}:${MAVEN_HOME}/bin
```
保存文件，并运行如下命令使环境变量生效：
```
# source /etc/profile
```
在控制台输入如下命令，如果能看到 Maven 相关版本信息，则说明 Maven 已经安装成功：
```
# mvn -v
```
2. 在pom.xml中引入以下依赖

```
<dependency>
    <!-- go to https://search.maven.org/search?q=tencentcloud-sdk-java and get the latest version. -->
    <!-- 请到https://search.maven.org/search?q=tencentcloud-sdk-java查询所有版本 -->
    <groupId>com.tencentcloudapi</groupId>
    <artifactId>tencentcloud-sdk-java-ess</artifactId>
    <version>3.1.528</version>
</dependency>
```

## 目录文件说明
### com.tencent.ess.api
api目录是对电子签企业版所有API的简单封装，以及调用的Example（可以直接运行main函数进行测试）。
如果需要API更加高级的功能，需要结合业务修改api的封装。

对使用文件和模板发起合同的封装：

**ByFileQuickStart一键使用文件发起流程：上传文件获取fileId -> 创建签署流程 -> 获取签署链接**

**ByTemplateQuickStart一键使用模板id发起流程:创建流程 -> 创建电子文档 -> 等待文档异步合成 -> 开启流程 -> 获取签署链接**

注意部分参数可能与用户配置的不一致，需要调整

### com.tencent.ess.byfile
byfile目录是电子签企业版的核心场景之一：通过文件发起的快速上手样例。
业务方可以结合自己的业务调整，用于正式对接。

### com.tencent.ess.bytemplate
byfile目录是电子签企业版的核心场景之一 ：通过模版发起的快速上手样例。
业务方可以结合自己的业务调整，用于正式对接。

### com.tencent.ess.callback
callback目录是电子签企业版对接的回调解密部分。
业务方需要配置好回调地址和加密key，就可以接收到相关的回调了。

### com.tencent.ess.common
用于构造默认电子签客户端调用实例。

### com.tencent.ess.config
里面定义调用电子签企业版API需要的一些核心参数。

### resources
blank.pdf是一个空白的pdf用于快速发起合同的测试。

## 电子签企业版官网入口
[腾讯电子签开发者中心](https://qian.tencent.com/developers/company/overview)