package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DAOGenerator {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "de.greenrobot.dao");

        addStuTable(schema);

//        new DaoGenerator().generateAll(schema, "C:/Android/git-repositories/GreenDAODemo/app/src/main/java-gen");
        new DaoGenerator().generateAll(schema, "C:\\Android\\git-repositories\\GreenDAODemo\\app\\src\\main\\java-gen");
    }

    private static void addStuTable(Schema schema) {
        Entity stu = schema.addEntity("Student");
        stu.addIdProperty();
        stu.addStringProperty("name").notNull();
        stu.addStringProperty("number");
        stu.addStringProperty("phone");
        stu.addDateProperty("date");
    }
}
