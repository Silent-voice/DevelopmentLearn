/*
    事务：ACID 4个特性
        1. A：Atomic，原子性，将所有SQL作为原子工作单元执行，要么全部执行，要么全部不执行；
        2. C：Consistent，一致性，事务完成后，所有数据的状态都是一致的，即A账户只要减去了100，B账户则必定加上了100；
        3. I：Isolation，隔离性，如果有多个事务并发执行，每个事务作出的修改必须与其他事务隔离；
           即对于并发的两个事务，每个事务执行时都任务其他并发事务不存在
        4. D：Duration，持久性，即事务完成后，对数据库数据的修改被持久化存储。


    不隔离事务可能会发生的问题
        脏读：
            1. 事务A读取事务B未提交(COMMIT)的数据。
            2. 即事务B对数据进行了修改，但还未提交，这时事务A读取了该修改后的数据。
            3. 无论事务B的操作是否会撤回(ROLLBACK)，事务A读取数据操作都叫做脏读

        不可重复读：
            1. 事务A在事务B开始之前读取了一次数据，在事务B提交(COMMIT)完成之后又读取了一次数据
            2. 两次读取的数据可能不同，这就是不可重复读问题

        幻读：
            1. 事务B插入了数据，事务B提交之后。
            2. 事务A查询相应数据，发现数据并不存在。
            3. 然后事务A对不存在的数据进行修改，发现数据又存在了。

        不可重复读与幻读的主要区别在于对应的操作：不可重读读针对的是修改操作，幻读对应的是新增和删除操作

    隔离级别
        Read Uncommitted：最低级别，可能会出现脏读、不可重复读、幻读
        Read Committed：可能会出现不可重复读、幻读
        Repeatable Read：可能会出现幻读，默认事务级别
        Serializable：事务顺序执行，效率低，但不会出现隔离问题

    设置事务隔离级别
        SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
        BEGIN;
        ...;
        COMMIT/ROLLBACK;

    基本操作
        1. BEGIN; ... COMMIT; 提交事务
            BEGIN;
            UPDATE accounts SET balance = balance - 100 WHERE id = 1;
            UPDATE accounts SET balance = balance + 100 WHERE id = 2;
            COMMIT;
        2. ROLLBACK; 事务回滚，撤销当前事务执行的SQL语句，已执行的操作会进行数据还原
            BEGIN;
            UPDATE accounts SET balance = balance - 100 WHERE id = 1;
            UPDATE accounts SET balance = balance + 100 WHERE id = 2;
            ROLLBACK;








 */








public class transaction_learn {
}
