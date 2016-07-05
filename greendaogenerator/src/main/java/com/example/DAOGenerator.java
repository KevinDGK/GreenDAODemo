package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DAOGenerator {
    public static void main(String[] args) throws Exception {

        /*
            1. 创建模式对象Schema，该模式用于向数据库中添加实体类
                - version:数据库版本
                - defaultJavaPackage:默认生成的代码在Android工程中相应目录下的包名
         */
        Schema schema = new Schema(1, "de.greenrobot.dao");

        // 使用这两行代码可以将bean和DAOs类分成两个包
//        Schema schema = new Schema(1, "de.greenrobot.bean");
//        schema.setDefaultJavaPackageDao("de.greenrobot.dao");

        //2. 使用Schema对象添加实体类
        addStuTable(schema);

        //3. 根据schema模式，使用DaoGenerator类生成所有的Bean和DAOs类，存放到outDir目录中，
        //   该目录为Android项目的java-dao-gen的绝对路径
        new DaoGenerator().generateAll(schema,
                "C:/Android/git-repositories/GreenDAODemo/app/src/main/java-dao-gen");
    }

    /** 使用Schema对象添加实体类 */
    private static void addStuTable(Schema schema) {

        //2.1 创建表
        //   一个实体类关联到数据库中的一张表，比如stu实体类对应着名称为"Student"的表
        Entity stu = schema.addEntity("Student");

        //2.2 添加属性
        //   greenDAO会自动根据实体类的属性值来创建表字段，并赋予默认值
        stu.addIdProperty();                      // 添加ID
        stu.addStringProperty("name").notNull();  // 添加属性name，为String类型，并且不为null
        stu.addStringProperty("number");
        stu.addStringProperty("phone");
        stu.addDateProperty("date");              // 添加属性date，为Date类型
    }
}
