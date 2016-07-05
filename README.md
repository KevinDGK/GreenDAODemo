# GreenDAO—Android ORM框架(一)  
   
>内容介绍：GreenDAO框架的介绍和使用  
>最新更新时间：2016/7/5  12:00  
>官网：http://greenrobot.org/greendao/   
>版本：V2.2.1 Bug fix release (2016-05-24)  
>说明：主要内容均翻译自官网最新的开发文档  
>博客地址：http://blog.csdn.net/kevindgk  
>GitHub地址：https://github.com/KevinDGK/GreenDAODemo  
>版权声明：本文为原创文章，未经允许不得转载  

## 简介  
GreenDAO是一个开放的安卓代码库，来提供一个容易使用的SQLite数据库接口，帮助开发者更加高效的处理数据——将开发者从处理低级的数据库需求中解放出来，从而节省了开发时间。SQLite是一个很不错的嵌入式关系型数据库。不过，编写SPL和解析查询结果是相当的繁琐和耗时的任务。greenDAO通过使用ORM的方式，将开发人员解放出来。你可以使用一个简单的面向对象的API来完成数据的存储、更新、删除和查询操作(CRUD)。

## 前言
我个人感觉，从简单的使用角度来讲，GreenDAO要比OrmLite、SugarORM等稍微复杂一些，尤其是2.x版本，前期配置代码分离的代码生成模块要稍微麻烦。但是，考虑到它的性能和非常强大的特点，我觉得这点儿麻烦不算什么。而且，3.x版本使用了注解，会使得代码生成十分方便，不用分离项目。截止到目前，3.0处于beta版本，后期会持续更新。  
但是，一般来讲，Android端不会创建十分复杂的数据库，所以如果是简单的表结构和少量的数据，也可以采用其他的方法。同样是搬砖，但是也要学会灵活运用！  

## 特点  
- **对象关系映射(ORM)**  
  greenDAO本质是提供一个面向对象的接口，完成对象与保存在关系数据库中的数据的映射。就像数据模型中定义的一样，greenDAO会创建数据的Java实体类(JavaBean)，以及DAO类。这些会节省你很多无聊的代码，仅仅是数据的来回移动。另外，greenDAO会提供一些高级的ORM特点，例如会话缓存，预先加载，以及活跃的实体类(?)。  
- **最优的性能**    
  在已知的ORM框架中，是最快的。关于性能greenDAO不做任何妥协。数据库用于存储大量的数据,因此速度很重要。使用greenDAO，每秒可以插入、更新和加载几千个实体类。    
![](http://i.imgur.com/Mdl7pwJ.jpg)  
- **APIs简单**：强大的API包含了关系和连接；
- **内存消耗少**；
- **库文件小**(<100KB)；
- **支持数据库加密**：支持使用SQLCipher(SQLite数据库加密框架)对数据库加密；(友情提醒，该加密技术会使得apk变得很大)
- **强大的开发团体**；  

##  开发工具和开发环境  
Android Studio 2.1.2  
	

##　How to get started 
这个教程会帮助你完成一个简单的greenDAO例程。它可以在[https://github.com/greenrobot/greenDAO](https://github.com/greenrobot/greenDAO "项目GitHub地址")获得，这个项目包含两个子项目：DaoExample和DaoExampleGenerator，可以clone下来进行学习。如果你将DaoExample从git仓库中复制出来，可以作为一个Android项目单独运行。如你所见，这是一个简单的记笔记的APP。你可以通过写入一些文本来添加新的笔记，并且通过点击删除按钮删除笔记。

### Add greenDAO to your project
1.  在src/main目录下创建一个目录(右键New->Directory)，比如名称为java-dao-gen，该目录主要是用于存放生成的Bean、DAOs类。  
	![](http://i.imgur.com/O3Q8OOA.jpg)  

2.	Android工程添加greenDAO依赖，并且给android节点添加程序来源：  
	compile 'org.greenrobot:greendao:2.2.0'   
	sourceSets {  
        main {  
            java.srcDirs = ['src/main/java', 'src/main/java-dao-gen']  
        }  
    }  
	如下图：  
	![](http://i.imgur.com/tmqMWN7.jpg)   

3.	创建GreenDAO代码生成模块(Module)，这是一个纯Java工程   
	![](http://i.imgur.com/tLfdPL9.jpg)  
	选择JAVA Library  
	![](http://i.imgur.com/LmLOWvw.jpg)  
	包名和类名随意定义，比如：  
	包名：greendaogenerator  
	类名：DAOGenerator  

4.	代码生成模块添加依赖  
	compile 'org.greenrobot:greendao-generator:2.2.0'   
	![](http://i.imgur.com/MwLeD9d.jpg)  

5.	编写代码生成类DAOGenerator  
	注意，Java工程只有这一个类，这个类就是用来生成代码模板的，在这个类中可以创建数据库，以及通过对象、关系等创建表结构，详细代码可以参考代码中的注释。  
	![](http://i.imgur.com/nnuP9Px.jpg)  
6.	在DAOGenerator的main方法中点击鼠标右键，运行该方法，然后就会在我们的android工程中生成相应的Bean和DAOs类：  
	![](http://i.imgur.com/IMyUyM3.jpg)  

7.	在Android工程中进行数据库的CRUD操作  
	(详细见代码)

8.	运行效果  
	
### 预生成代码和创建表  
现在，
