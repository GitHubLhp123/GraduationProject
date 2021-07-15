package myproject.project.utils.server;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoDBJDBC {
    public static void main(String args[]) {
        try {
//            // 连接到 mongodb 服务
//            MongoClient mongoClient = new MongoClient("1.15.112.44", 27017);
//
//            // 连接到数据库
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
//
//            System.out.println(mongoDatabase.getName());
//            System.out.println("Connect to database successfully");
//
//            System.out.println("Connect to database successfully");
//            mongoDatabase.createCollection("test");
//            System.out.println("集合创建成功");
//            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
//            System.out.println("集合 test 选择成功");
//            //插入文档
//            /**
//             * 1. 创建文档 org.bson.Document 参数为key-value的格式
//             * 2. 创建文档集合List<Document>
//             * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
//             * */
//            Document document = new Document("title", "MongoDB").
//                    append("description", "database").
//                    append("likes", 100).
//                    append("by", "Fly");
//            List<Document> documents = new ArrayList<Document>();
//            documents.add(document);
//            collection.insertMany(documents);
//            System.out.println("文档插入成功");


        } catch (Exception e) {
            System.out.println("==============");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}