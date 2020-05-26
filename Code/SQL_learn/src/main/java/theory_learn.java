/*
    主键：
        1. 表中不允许重复的字段，可用于唯一标识一条记录
        2. 主键一般不允许修改，不建议使用任何业务相关的字段作为主键，这种主键一般叫做 id
        3. id 常用类型有：自增整数类型、全局唯一GUID类型(全局唯一的字符串)
        4. 联合主键：使用多个列字段共同作为主键，只要不是所有列字段全相同即可，一般不建议使用联合主键，太复杂

        CREATE TABLE Persons
        (
        Id_P int NOT NULL,
        Name varchar(255) NOT NULL,
        PRIMARY KEY (Id_P)
        )       // 创建时设置主键
        ALTER TABLE table_name ADD PRIMARY KEY (column_name);    // 为某一列添加主键约束

    外键：
        1. 将表A中的字段column_a绑定到表B中的字段column_b，则column_a会自动添加约束，取值限制在column_b出现的取值范围内
        2. column_a称为表A的外键，用于表示1对多关系，一般不使用外键，会降低数据库性能


    索引：
        1. 提高查找效率，索引效率取决于数据是否散列，数据越不相同，索引效率越高。像性别这种字段就不适合索引
        2. 主键会自动创建索引
        3. 索引会提高查询速度，但会降低插入、更新和删除效率，需要同时去修改索引。

        ALTER TABLE table_name ADD INDEX index_name (column_name1, column_name2);     // 为指定列创建索引

    唯一约束：
        1. 使得非主键列也不允许重复， CONSTRAINT约束关键字
        ALTER TABLE table_name ADD CONSTRAINT cons_name UNIQUE (column_name);    //唯一约束

        ALTER TABLE table_name ADD UNIQUE INDEX index_name (column_name);    // 唯一约束，并创建索引



    建表引擎：
        InnoDB
            1. 支持事务、回滚、锁、并发等，主流引擎
        myisam
            1. 不支持事务和各种容错机制，查询效率较高


 */




public class theory_learn {

}
