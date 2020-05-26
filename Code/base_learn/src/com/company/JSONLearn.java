package com.company;

/*
    Jackson
    1. 读取XML和JSON数据的第三方库，能够自动将数据填充到一个自定义的类中
 */


import java.io.InputStream;
import java.util.List;
import org.codehaus.jackson.*;

public class JSONLearn {

//    public void fun1(){
//        InputStream input = Main.class.getResourceAsStream("/book.xml");
//        JacksonXmlModule module = new JacksonXmlModule();
//        XmlMapper mapper = new XmlMapper(module);
//
//        InputStream input = Main.class.getResourceAsStream("/book.json");
//        ObjectMapper mapper = new ObjectMapper();
//// 反序列化时忽略不存在的JavaBean属性:
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        Book book = mapper.readValue(input, Book.class);
//    }

}

// 自定义一个数据类型
class Book {
    public long id;
    public String name;
    public String author;
    public String isbn;
    public List<String> tags;
    public String pubDate;
}
