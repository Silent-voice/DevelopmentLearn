/*
    数据库操作
        创建
        CREATE DATABASE db_name;
        CREATE DATABASE IF NOT EXISTS db_name;

        删除：DROP DATABASE db_name;
        使用：USE DATABASE db_name;
             SHOW DATABASES;

    表操作：
        CREATE TABLE table_name (column_name1 column_type1, ..., );
        DROP TABLE table_name ;
        SHOW TABLES;
        DESC table_name;    查看表结构
        SHOW CREATE TABLE table_name;   查看表的创建语句

        CREATE TABLE IF NOT EXISTS runoob_tbl(
           runoob_id INT UNSIGNED AUTO_INCREMENT,     # 自增字段
           runoob_title VARCHAR(100) NOT NULL,        # 不能为NULL
           runoob_author VARCHAR(40) NOT NULL,
           submission_date DATE,
           PRIMARY KEY (runoob_id)                  # 主键
        )ENGINE=InnoDB DEFAULT CHARSET=utf8;            # 建表引擎和默认编码

        修改表中字段
            1. 添加一列 birth
                ALTER TABLE students ADD COLUMN birth VARCHAR(10) NOT NULL;
            2. 将列birth该名为birthday，并设置数据类型
                ALTER TABLE students CHANGE COLUMN birth birthday VARCHAR(20) NOT NULL;
            3. 删除列
                ALTER TABLE students DROP COLUMN birthday;

        创建快照：创建并复制表内容到一个新表，表结构相同
            CREATE TABLE students_of_class1 SELECT * FROM students WHERE class_id=1;

 */





/*

    查询操作：
        关键词的顺序： WHERE > GROUP BY > ORDER BY


        SELECT column_name1,column_name1 FROM table_name [WHERE <条件表达式>] [LIMIT N] [OFFSET M]
            1. * 可以代替column_name，指所有字段
            2. WHERE 设置匹配条件
            3. LIMIT 限制返回的记录数
            4. OFFSET 指定SELECT语句开始查询的数据偏移量，默认为0
        SELECT * FROM table_name;
        SELECT id, score, name FROM students;

        条件表达式
            1. <条件1> AND <条件2>     <条件1> OR <条件2>      NOT <条件>
                SELECT * FROM students WHERE score >= 80 AND gender = 'M';
                SELECT * FROM students WHERE NOT class_id = 2;      等价于 class_id <> 2  class_id != 2
                SELECT * FROM students WHERE (score < 80 OR score > 90) AND gender = 'M';
            2. 字符串比较默认是不区分大小写的，可以使用关键字BINARY指定区分大小写
                SELECT * from runoob_tbl WHERE BINARY runoob_author='RUNOOB.com';
            3. IN <value1, value2, ...> 枚举
                SELECT * FROM students WHERE score IN <60, 70, 80, 90>;
            4. BETWEEN value1 AND value2     >= value1 AND <= value2
                SELECT * FROM students WHERE score BETWEEN 60 AND 90;
            5. 正则匹配 LIKE，可以使用 % 代表任意字符
                SELECT * from runoob_tbl  WHERE runoob_author LIKE '%COM';  所有runoob_author以COM结尾的记录

        排序
            1. ORDER BY 对返回的查询结果进行排序，从低到高，DESC 倒序，
                SELECT id, name, gender, score FROM students ORDER BY score;
                SELECT id, name, gender, score FROM students ORDER BY score DESC;
            2. 可以指定多个排序字段，前面字段相同时按照后面字段排序
                SELECT id, name, gender, score FROM students ORDER BY score DESC, gender;
            3. ORDER BY 语句放在 WHERE 之后
                SELECT id, name, gender, score FROM students WHERE class_id = 1 ORDER BY score DESC;

        分页：
            1. LIMIT N OFFSET M， 从第M条记录开始查询，最多返回N条记录，M越大，查询效率会越慢
                SELECT id, name, gender, score
                FROM students
                ORDER BY score DESC
                LIMIT 3 OFFSET 0;
            2. OFFSET超过数据范围时返回空结果
            3. LIMIT N OFFSET M 可以简写为 LIMIT M, N


        聚合函数
            1. COUNT() 统计行数，返回仍然是一个二维表，column name默认是COUNT(...)，也可以自己设置
                SELECT COUNT(*) FROM students;
                SELECT COUNT(*) num FROM students;  设置column name为num
            2. SUM()/AVG()	计算某一列的合计值/平均值，该列必须为数值类型
                MAX()/MIN()	计算某一列的最大值/最小值，不限于数值类型

                SELECT AVG(score) average FROM students WHERE gender = 'M';
            3. 如果聚合查询的WHERE条件没有匹配到任何行，COUNT()会返回0，而SUM()、AVG()、MAX()和MIN()会返回NULL
            4. 查询到的结果也可以进行运算，  CEILING() 向上取整  FLOOR() 向下取整
                SELECT CEILING(COUNT(*) / 3) FROM students;

        分组：
            1. 按照某一列的取值将数据分组
                通过class_id对数据分组，分别进行计算，返回两列，一列是class_id，一列是相应组数据个数
                SELECT class_id, COUNT(*) num FROM students GROUP BY class_id;
                SELECT class_id, AVG(score) FROM students GROUP BY class_id ORDER BY AVG(score);

            2. 也可以使用多个列进行分组，组数 = 列1取值个数 * 列2取值个数 * ...
                SELECT class_id, gender, COUNT(*) num FROM students GROUP BY class_id, gender;


        多表查询
            1. 查询多表返回的是 每个表中的每行数据和另外一个表的每行数据拼接在一起
                行数 = 表1行数 * 表2行数
                列数 = 表1列数 + 表2列数
                SELECT * FROM students, classes;
            2. 指定返回字段，行数不变
                SELECT
                    s.id sid,           重新命名为sid
                    s.name,
                    s.gender,
                    s.score,
                    c.id cid,           重新命名为cid
                    c.name cname
                FROM students s, classes c;     设置表的别名
            3. 添加查询条件，此时行数变少了
                SELECT
                    s.id sid,
                    s.name,
                    s.gender,
                    s.score,
                    c.id cid,
                    c.name cname
                FROM students s, classes c
                WHERE s.gender = 'M' AND c.id = 1;      返回的每一行都满足该条件

        连接查询
            1. 在主表内查询，然后将连接表的指定行加入到返回结果中
                SELECT s.id, s.name, s.class_id, c.name class_name, s.gender, s.score
                FROM students s             主表
                INNER JOIN classes c        连接表
                ON s.class_id = c.id;       连接条件
            2. INNER JOIN  只返回同时存在于两张表的行数据
                RIGHT OUTER JOIN    返回右表都存在的行
                LEFT OUTER JOIN    返回左表都存在的行


        多表查询结果的组合
            1. 将两条查询语句的结果进行组合
            2. UNION [ALL | DISTINCT]，默认是DISTINCT，对数据进行了去重；ALL没有去重，返回多个表的所有结果
                SELECT country FROM Websites
                UNION    (UNION ALL)
                SELECT country FROM apps
                ORDER BY country;
                提取出两个表中的country字段的所有取值

 */



/*
    插入数据
        1. 基本语法：INSERT INTO <表名> (字段1, 字段2, ...) VALUES (值1, 值2, ...);
            INSERT INTO students (class_id, name, gender, score) VALUES (2, '大牛', 'M', 80);
        2. 字段如果有默认值，可以不出现在INSERT语句中，字段顺序不需要和表一样
        3. 一次性添加多组数据
            INSERT INTO students (class_id, name, gender, score) VALUES (1, '大宝', 'M', 87), (2, '二宝', 'M', 81);
        4. 常用插入命令，根据id判断记录是否已存在
            1. INSERT IGNORE 如果记录已存在就忽略该指令
                INSERT IGNORE INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'F', 99);
            2. REPLACE INTO 如果记录已存在，就先删除再插入新的值
                REPLACE INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'F', 99);
            3. INSERT INTO ... ON DUPLICATE KEY UPDATE ...  如果记录已存在，就按UPDATE后面的字段进行更新，否则按VALUES进行插入
                INSERT INTO students (id, class_id, name, gender, score) VALUES (1, 1, '小明', 'F', 99) ON DUPLICATE KEY UPDATE name='小明', gender='F', score=99;

    修改数据
        1. 基本语法：UPDATE <表名> SET 字段1=值1, 字段2=值2, ... WHERE ...;
            UPDATE students SET name='大牛', score=66 WHERE id=1;
            UPDATE students SET name='小牛', score=77 WHERE id>=5 AND id<=7;
        2. 更新字段可以使用表达式
            UPDATE students SET score=score+10 WHERE score<80;

    删除数据
        1. 基本语法：DELETE FROM <表名> WHERE ...;
            DELETE FROM students WHERE id>=5 AND id<=7;


    操作融合：
        1. 将查询结果插入其他表，要确保查询到的字段符合插入表的要求
            INSERT INTO statistics (class_id, average) SELECT class_id, AVG(score) FROM students GROUP BY class_id;

 */


public class base_learn {
}
