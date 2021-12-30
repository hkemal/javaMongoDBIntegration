package com.hkemal.javaMongoDBIntegration;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase infoDB = client.getDatabase("Info");
        //infoDB.createCollection("Personel");
        MongoCollection<Document> personelCollection = infoDB.getCollection("Personel");

        BasicDBObject data1 = new BasicDBObject()
                .append("name", "Stephan Hawking")
                .append("date", 1942)
                .append("country", "England");
        //personelCollection.insertOne(Document.parse(data1.toJson()));

        BasicDBObject data2 = new BasicDBObject()
                .append("name", "Isaac Newton")
                .append("date", 1642)
                .append("country", "England");

        BasicDBObject data3 = new BasicDBObject()
                .append("name", "Albert Einstein")
                .append("date", 1879)
                .append("country", "Germany");

        BasicDBObject data4 = new BasicDBObject()
                .append("name", "Nikola Tesla")
                .append("date", 1856)
                .append("country", "Serbia");

        BasicDBObject data5 = new BasicDBObject()
                .append("name", "Thomas Edison")
                .append("date", 1847)
                .append("country", "England");

        BasicDBObject data6 = new BasicDBObject()
                .append("name", "Lise Meitner")
                .append("date", 1878)
                .append("country", "Austria")
                .append("job", "scientist");

        Document parse1 = Document.parse(data1.toJson());
        Document parse2 = Document.parse(data2.toJson());
        Document parse3 = Document.parse(data3.toJson());
        Document parse4 = Document.parse(data4.toJson());
        Document parse5 = Document.parse(data5.toJson());
        Document parse6 = Document.parse(data6.toJson());


        //personelCollection.insertMany(Arrays.asList(parse1, parse2, parse3, parse4, parse5, parse6));

        FindIterable<Document> documents = personelCollection.find();
        documents.forEach((Block<? super Document>) item -> System.out.println(item.toJson()));

        System.out.println("*************************************");
        FindIterable<Document> documentsFiltered = personelCollection.find(new BasicDBObject("date", 1942));
        documentsFiltered.forEach((Block<? super Document>) item -> System.out.println(item.toJson()));

        Bson filter = Filters.eq("name", "Stephan Hawking");
        Bson update = Updates.set("date", 1952);
        personelCollection.updateOne(filter, update);

        Bson filterJob = Filters.exists("job");
        Bson updateAge = Updates.set("age", 90);
        personelCollection.updateOne(filterJob, updateAge);

        Bson deleteFilter = Filters.eq("name", "Stephan Hawking");
        //personelCollection.deleteOne(deleteFilter);
        personelCollection.deleteMany(deleteFilter);
        personelCollection.drop();
        infoDB.drop();
    }
}
