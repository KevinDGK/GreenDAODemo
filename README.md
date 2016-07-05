# GreenDAO—Android ORM框架(一)  

>当前版本：2.2.1  
>内容介绍：GreenDAO框架的介绍和使用  
>官网：http://greenrobot.org/greendao/  
>说明：主要内容均翻译自官网最新的开发文档  
>博客地址：http://blog.csdn.net/kevindgk  
>GitHub地址：https://github.com/KevinDGK/GreenDAODemo  
>版权声明：本文为原创文章，未经允许不得转载  

## 简介  
GreenDAO是一个开放的安卓代码库，来提供一个容易使用的SQLite数据库接口，帮助开发者更加高效的处理数据——将开发者从处理低级的数据库需求中解放出来，从而节省了开发时间。SQLite是一个很不错的嵌入式关系型数据库。不过，编写SPL和解析查询结果是相当的繁琐和耗时的任务。greenDAO通过使用ORM的方式，将开发人员解放出来。你可以使用一个简单的面向对象的API来完成数据的存储、更新、删除和查询操作(CRUD)。

## 特点  
- 最优的性能：也许是最快的Android ORM框架；
- APIs简单：强大的API包含了关系和连接；
- 内存消耗少；
- 库文件小(<100KB)；
- 数据库加密：支持使用SQLCipher(SQLite数据库加密框架)对数据库加密；
- 强大的开发团体；

##　How to get started 
这个教程会帮助你完成一个简单的greenDAO例程。它可以在[https://github.com/greenrobot/greenDAO](https://github.com/greenrobot/greenDAO "项目GitHub地址")获得，这个项目包含两个子项目：DaoExample和DaoExampleGenerator，可以clone下来进行学习。如果你将DaoExample从git仓库中复制出来，可以作为一个Android项目单独运行。如你所见，这是一个简单的记笔记的APP。你可以通过写入一些文本来添加新的笔记，并且通过点击删除按钮删除笔记。

### Add greenDAO to your project  
	项目添加依赖：compile 'org.greenrobot:greendao:2.2.0'  
	代码生成项目添加依赖：compile 'org.greenrobot:greendao-generator:2.2.0'  

### 预生成代码和创建表  
现在，
