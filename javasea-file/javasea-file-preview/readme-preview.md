# 文档在线预览
思路：

1、使用OpenOffice（JODConverter）将不同类型的附件转换为.pdf后缀的文件（PDF）；

2、使用SwfTools将pdf文件转换为swf文件;

3、使用FlexPaper 在jsp中预览swf文件；

## 工具准备

### 安装swftools

下载：<https://swftools.en.softonic.com/>

> 参考：<https://blog.csdn.net/zhizaibide1987/article/details/28901511>

### 安装openoffice(windows)

下载openoffice：

http://www.openoffice.org/download/index.html

下载后一步步安装即可。

今日program目录下，执行如下进行启动服务

```shell
soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
```

查看服务是否启动正常

```shell
C:\Program Files (x86)\OpenOffice 4\program>netstat -ano | findstr 8100
  TCP    127.0.0.1:8100         0.0.0.0:0              LISTENING       19792
```

### 安装openoffice(centos)

#### 下载openoffice：

http://www.openoffice.org/download/index.html

```shell
wget https://sourceforge.net/projects/openofficeorg.mirror/files/4.1.7/binaries/zh-CN/Apache_OpenOffice_4.1.7_Linux_x86-64_install-rpm_zh-CN.tar.gz
```

改名：

```shell
mv Apache_OpenOffice_4.1.7_Linux_x86-64_install-rpm_zh-CN.tar.gz   openoffice.tar.gz
```

解压：

```shell
tar -xvf openoffice.tar.gz
```

生成了一个`zh-CN`目录

```shell
[root@zhirui-test11 zh-CN]# pwd
/data/zh-CN
[root@zhirui-test11 zh-CN]# ll
total 4
drwxrwxr-x. 2 root root   35 Sep 16 06:17 licenses
drwxrwxr-x. 2 root root   51 Sep 16 06:17 readmes
drwxrwxr-x. 3 root root 4096 Sep 16 06:17 RPMS
[root@zhirui-test11 zh-CN]# 
```

进入`RPMS`目录，执行`yum localinstall *.rpm`命令

```shell
[root@zhirui-test11 zh-CN]# cd RPMS/
[root@zhirui-test11 RPMS]# yum localinstall *.rpm
```

安装的时候全选`y`，查看当前目录有个`desktop-integration`目录，进入该目录

```shell
[root@zhirui-test11 RPMS]# ll
total 147536
drwxr-xr-x. 2 root root      243 Sep 16 06:17 desktop-integration
[root@zhirui-test11 RPMS]# cd desktop-integration
[root@zhirui-test11 desktop-integration]# ll
total 4436
-rw-r--r--. 1 root root 1093087 Aug 20 22:37 openoffice4.1.7-freedesktop-menus-4.1.7-9800.noarch.rpm
-rw-r--r--. 1 root root 1109222 Aug 20 22:38 openoffice4.1.7-mandriva-menus-4.1.7-9800.noarch.rpm
-rw-r--r--. 1 root root 1164709 Aug 20 22:37 openoffice4.1.7-redhat-menus-4.1.7-9800.noarch.rpm
-rw-r--r--. 1 root root 1168622 Aug 20 22:38 openoffice4.1.7-suse-menus-4.1.7-9800.noarch.rpm
[root@zhirui-test11 desktop-integration]# 
```

再次用`localinstall`进行安装

```shell
[root@zhirui-test11 desktop-integration]# yum localinstall openoffice4.1.7-redhat-menus-4.1.7-9800.noarch.rpm
```

#### 启动

安装成功会生产`/opt/openoffice4`目录

```shell
[root@zhirui-test11 desktop-integration]# cd /opt/
[root@zhirui-test11 opt]# ll
total 0
drwx--x--x. 4 root root  28 Oct 23 13:23 containerd
drwxr-xr-x. 7 root root 109 Sep 16 05:09 openoffice4
```

临时启动： 临时启动之后画面就不会动了, 不要认为是死机。只要不报错就是好现象

```shell
[root@zhirui-test11 data]/opt/openoffice4/program/soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
```

当然也可以用`nohup`进行启动，根据情况选择启动方式

```shell
[root@zhirui-test11 data]# nohup /opt/openoffice4/program/soffice -headless -accept="socket,host=0.0.0.0,port=8100;urp;" -nofirststartwizard &
```

> 启动时，可能会报错如下：
>
> ```shell
> /opt/openoffice4/program/soffice.bin: error while loading shared libraries: libXext.so.6: cannot open shared object file: No such file or directory
> ```
>
> 还需要安装个工具：
>
> ```shell
> [root@zhirui-test11 data]# yum install libXext.x86_64
> ```
>
> 再次启动，会报错如下：
>
> ```shell
> no suitable windowing system found, exiting.
> ```
>
> 从字面上的意思就是缺少一个窗口化的系统。所以就安装一个。
>
> ```shell
> yum groupinstall "X Window System"
> ```

启动后查看进程：

```shell
[root@zhirui-test11 data]# ps -ef|grep openoffice
root     18289  9617  0 14:43 pts/0    00:00:00 /bin/sh /opt/openoffice4/program/soffice -headless -accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard
root     18298 18289  0 14:43 pts/0    00:00:00 /opt/openoffice4/program/soffice.bin -headless -accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard
root     18380  9617  0 14:45 pts/0    00:00:00 grep --color=auto openoffice
```

#### 启动报错问题

启动时，可能会报错如下：

```shell
/opt/openoffice4/program/soffice.bin: error while loading shared libraries: libXext.so.6: cannot open shared object file: No such file or directory
```

还需要安装个工具：

```shell
[root@zhirui-test11 data]# yum install libXext.x86_64
```

再次启动，会报错如下：

```shell
no suitable windowing system found, exiting.
```

从字面上的意思就是缺少一个窗口化的系统。所以就安装一个。

```shell
yum groupinstall "X Window System"
```

#### linux环境下的openoffice解析乱码问题

- 原因是linux缺少winds环境的字体库

> 该死的微软，功能更新太快，Apache更新没跟上，就有很多新加的格式解析不出来

- 解决方案：
   复制window系统的字体库
   C:\Windows\Fonts  到  /usr/share/fonts 然后

```bash
fc-cache #  更新缓存，重启oppenoffice，搞定！
```

然后找到安装目录下的program 文件夹

在目录下运行

soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
如果运行失败，可能会有提示，那就加上   .\   在运行试一下

这样openoffice的服务就开启了。

## 测试
访问url： http://localhost:8080/test/choose



## 参考

<https://www.jianshu.com/p/b5db6d43f8d1>

<https://cloud.tencent.com/developer/article/1119313>

<https://blog.csdn.net/guo147369/article/details/78486337>

<https://github.com/liaowp/OnlinePreview>