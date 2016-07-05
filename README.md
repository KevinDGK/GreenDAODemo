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
开发工具 Android Studio 2.1.2  
数据库查看的工具	SQLite Expert Professional 3.1.9.2085	

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
    /** 插入数据 */  
    private void submit() {

        String name = et_name.getText().toString().trim();
        String stuId = et_id.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();

        Student student = new Student(null, name, stuId, phone, new Date());

        /*
            数据库里的一条记录对应着一个Java对象，所以插入操作，仅仅需要插入一个对象即可。
         */
        DBUtil.getStudentDao().insert(student);

        Log.d("【submit】", "Inserted new student, ID: " + student.getId() + " , Name："+name);
    }  

    /** 查询所有数据 */  
    private void queryAll() {

        /*
            获取属性名称，这一步主要是为了查询的时候作为查询条件使用
            String number = StudentDao.Properties.Number.columnName;
            String orderBy = number + " COLLATE LOCALIZED ASC"; // 按照学号的升序排列

            该方法是调用数据库SQLiteDatabase的query()方法来进行查询的，比较繁琐，但是说明也是执行SQL语句的
            cursor = DBUtil.getDB().query(tablename, DBUtil.getStudentDao().getAllColumns(), null, null, null, null, orderBy);

            String[] from = { name , number };
            int[] to = { android.R.id.text1, android.R.id.text2 };
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
            lv.setAdapter(adapter);
         */

        // 以下是通过GreenDAO的方式查询数据库，所有的操作都是面向对象的方式
        Query<Student> query = DBUtil.getStudentDao().queryBuilder()
                                .where(StudentDao.Properties.Number.isNotNull())
                                .orderAsc(StudentDao.Properties.Number)     // 按照学号的升序排列
                                .build();

        list = query.list();

        if ((list != null) && (list.size()>0)) {
            if (adapter == null) {
                adapter = new MyLVAdapter();
                lv.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(MainActivity.this,"数据库里没有数据了!",Toast.LENGTH_SHORT).show();
        }
    }

    /** 查询一条数据 */  
    private void queryOne() {

        String stuId = et_stuid.getText().toString().trim();
        Log.i("【查询的学生的学号】",stuId);

        StudentDao studentDao = DBUtil.getStudentDao();

        // 一个可以被反复执行的查询的实体类
        Query<Student> query = studentDao.queryBuilder()
                                .where(StudentDao.Properties.Number.eq(stuId))  // 学号属性等于被查询的数值
                                .build();

        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        // 查询结果以list返回
        List<Student> list = query.list();

        String result = "";
        if ((list != null) && (list.size()>0)) {
            Student student = list.get(0);
            result = student.getId()+","+student.getName()+","+student.getNumber()+","+student.getPhone()+","+new SimpleDateFormat().format(student.getDate());
        } else {
            Toast.makeText(MainActivity.this,"查无此人!",Toast.LENGTH_SHORT).show();
        }
        tv_check_one.setText(result);

    }

    /** 删除一条数据 */  
    private void deleteOne() {

        String stuId = et_stuid.getText().toString().trim();
        Log.i("【删除的学生的学号】",stuId);

        StudentDao studentDao = DBUtil.getStudentDao();

        // 一个可以被反复执行的查询的实体类
        Query<Student> query = studentDao.queryBuilder()
                .where(StudentDao.Properties.Number.eq(stuId))  // 学号属性等于被查询的数值
                .build();

        List<Student> list = query.list();

        for (int i = 0; i < list.size(); i++) {
            studentDao.deleteByKey(list.get(i).getId());    //通过id删除掉记录
        }
    }

    /** 删除数据库所有数据 */  
    private void deleteAll() {

        Log.i("【删除数据库所有数据】"," ======== 清空了~~~ ========= ");
        DBUtil.getStudentDao().deleteAll();
        list.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
8.	运行效果  
	![](http://i.imgur.com/rdxCJDs.gif)  

### 预生成代码和创建表  
1.	所有的预生成代码都是由分离的Java模块生成的，在3.x中将会有较大的改善；  
2.	所有的对数据库的操作都是在Android项目中的，详细看demo中的MainActivity.java.  

## 问题
### 1.关于数据库创建的位置
一般情况下，为了安全，数据库创建在/data/data/包名/databases目录下即可，但是demo中为了随时将数据库从手机中取出，查看数据库的数据，所以暂时将数据库建立在sd卡中。  
详细代码，见 DBUtil.java  

### 2.简单封装，便于使用  
在代码中为了使得使用更加清晰和方便，我简单的封装了一下，将DAOs都封装到了DBUtil.java里面了。

### 3.数据库的初始化
关于数据库的初始化，官方建议在Application层完成，这样的话，就不用多次创建Session了，但是，如果应用中对数据库的操作较少，只要确保在第一次执行数据库操作之前完成初始化即可。

## 引用
> http://www.open-open.com/lib/view/open1438065400878.html  