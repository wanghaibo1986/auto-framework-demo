package utils.mongodb;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;


import utils.mongodb.beans.DBConfigBean;
import utils.mongodb.beans.DBConfigBean.Config;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBConnector {
	
	private static MongoClient mongoClient = null;
    private DB db = null;
    private DBCollection collection = null;
    private DBCursor cursor;
    
    public enum MongoDBEnum{
    	QA1("qa1Config"),
    	Automation("automationConfig"),
    	Local("localConfig");
    	
    	private String db;
    	
    	MongoDBEnum(String db) { this.db = db; }
    	
    	public String getDB() { return this.db; }
    }
    
    public MongoDBConnector() {
    	connect();
    }
    
    public MongoDBConnector(MongoDBEnum dbe) {
    	connect(dbe);
    }
    
    public MongoClient getMongoClient() {
    	return mongoClient;
    }
    
    public MongoClient connect() {
    	Config dbConfig = new DBConfigBean().read().getDefaultDBConfig();
    	String dbName = dbConfig.getDbName();
    	String userName = dbConfig.getUserName();
    	String password = dbConfig.getPassword();
    	String serverAndPort = dbConfig.getServerAndPort();
    	MongoCredential credential = MongoCredential.createCredential(userName, dbName, password.toCharArray());
    	if (userName.equals("")) {
    		try {  
            	mongoClient = new MongoClient("127.0.0.1" , 27017);  
            } catch (UnknownHostException e){  
                e.printStackTrace();              
            }
    	} else {
			try {
				mongoClient = new MongoClient(new ServerAddress(serverAndPort), Arrays.asList(credential));
			} catch (Exception e) {}
    	}
		return mongoClient;
    }
    
    public MongoClient connect(MongoDBEnum dbe) {
    	Config dbConfig = new DBConfigBean().read().getDBConfig(dbe.getDB());
    	String dbName = dbConfig.getDbName();
    	String userName = dbConfig.getUserName();
    	String password = dbConfig.getPassword();
    	String serverAndPort = dbConfig.getServerAndPort();
    	if (userName.equals("")) {
    		try {  
            	mongoClient = new MongoClient("127.0.0.1" , 27017);  
            } catch (UnknownHostException e){  
                e.printStackTrace();              
            }
    	} else {
    		MongoCredential credential = MongoCredential.createCredential(userName, dbName, password.toCharArray());
			try {
				mongoClient = new MongoClient(new ServerAddress(serverAndPort), Arrays.asList(credential));
			} catch (Exception e) {
				System.out.println("Failed to connect: " + serverAndPort + "\n VPN should be connected.");
			}
    	}
		return mongoClient;
    }
    
    public DB getDB(String dbName) {
    	if(mongoClient!=null) {  
            db = mongoClient.getDB(dbName);
    	} else {  
			System.out.println("Please connect to MongoDB and then fetch the collection");  
    	}
    	return db;
    }
    
    public DBCollection getCollection(String dbName, String collectionName) {
    	return mongoClient.getDB(dbName)
    			   		  .getCollection(collectionName);
    }
    
    public DBCursor getCursor(String dbName, String collectionName) {
    	return mongoClient.getDB(dbName)
                          .getCollection(collectionName)
                          .find();
    }
    
    public DBCursor getCursor(String dbName, String collectionName, BasicDBObject query) {
    	return mongoClient.getDB(dbName)
                          .getCollection(collectionName)
                          .find(query);
    }
      
    // 列出dbName数据库中的所有集合  
    public void listAllCollections(String dbName) {  
        if(mongoClient!=null){  
            db = mongoClient.getDB(dbName);  
            Set<String> collections = db.getCollectionNames();  
              
            for(String c : collections){  
                System.out.println(c);  
            }  
        }  
    }
    
    public void listAllCollections() {
    	Config dbConfig = new DBConfigBean().read().getDefaultDBConfig();
    	System.out.println("dbName: " + dbConfig.getDbName());
        if(mongoClient!=null){  
            db = mongoClient.getDB(dbConfig.getDbName());  
            Set<String> collections = db.getCollectionNames();  
              
            for(String c : collections){  
                System.out.println(c);  
            }  
        }  
    }
    
    // 列出所有文档  
    public void listAllDocuments(String dbName, String collectionName) {
    	cursor = getCursor(dbName, collectionName);
    	try {
    		while(cursor.hasNext()) {
    			System.out.println(cursor.next());
    		}
    	} finally {
    		cursor.close();
    	} 
    }
    
    public void listDocument(String dbName, String collectionName, BasicDBObject query) {
    	cursor = getCursor(dbName, collectionName, query);
    	try {
    		while(cursor.hasNext()) {
    			System.out.println(cursor.next());
    		}
    	} finally {
    		cursor.close();
    	}
    }
    
    public Boolean insertDocument(String dbName, String collectionName, DBObject doc) {
    	try {
	    	mongoClient.getDB(dbName)
	    	           .getCollection(collectionName)
	    	           .insert(doc);
	    	return true;
    	} catch (Exception e) {
    		System.out.println("Failed to insert below document:\n" + doc);
    		return false;
    	}
    }
    
    public Boolean insertDocument(String dbName, String collectionName, String jsonFile) {
//    	JSONArray a = (JSONArray) parser.parse(new FileReader(jsonFile));
//
//    	  for (Object o : a)
//    	  {
//    	    JSONObject person = (JSONObject) o;
//    	DBObject doc = ( DBObject ) JSON.parse( json );
//    	try {
//    		mongoClient.getDB(dbName)
//    		.getCollection(collectionName)
//    		.insert(doc);
//    		return true;
//    	} catch (Exception e) {
//    		System.out.println("Failed to insert below document:\n" + doc);
//    		return false;
//    	}
    	return true;
    }
    
    public void updateDocument(String dbName, String collectionName, BasicDBObject query, BasicDBObject set) {
    	mongoClient.getDB(dbName)
    	           .getCollection(collectionName)
    	           .update(query, set);
    }
    
    public void updateDocuments(String dbName, String collectionName, BasicDBObject query, BasicDBObject set, Boolean upsert, Boolean multi) {
    	mongoClient.getDB(dbName)
    	           .getCollection(collectionName)
    	           .update(query, set, upsert, multi);
    }
    
    public DBObject getDocument(String dbName, String collectionName, BasicDBObject query) {
        DBObject doc = null;
    	cursor = getCursor(dbName, collectionName, query);
    	try {
    		while(cursor.hasNext()) {
    			doc = cursor.next();
    		}
    	} finally {
    		cursor.close();
    	}
    	return doc;
    }
    
    public void removeDocument(String dbName, String collectionName, BasicDBObject query) {
    	mongoClient.getDB(dbName)
                   .getCollection(collectionName)
                   .remove(query);
    }
    
//    public static void main(String[] args) {
//    	MongoDBConnector connector = new MongoDBConnector();
//		connector.connectQA1();
//		connector.listAllCollections("coredb");
//		connector.listAllDocuments("coredb", "systemPath");
//		BasicDBObject query = new BasicDBObject("_id", 219910915489797L);
//		connector.listDocument("coredb", "systemPath", query);
//    }
	
	public static void main(String[] args) {
		String dbName = "test";
		String collectionName = "automation";
		BasicDBObject query;
		DBCollection collection;
		
		MongoDBConnector connector = new MongoDBConnector();
		collection = connector.getCollection(dbName, collectionName);
		
		BasicDBObject env_qa1_doc = new BasicDBObject("environment", "qa1")
                                            .append("baseUrl", "https://manager-qa1.everbridge.net")
                                            .append("apiUrl", "https://api-qa1.everbridge.net/rest")
                                            .append("ebadminUrl", "https://ebadmin-qa1.everbridge.net")
                                            .append("memberPortalUrl", "https://member-qa1.everbridge.net");
		
		BasicDBObject env_qa2_doc = new BasicDBObject("environment", "qa2")
                                            .append("baseUrl", "https://manager-qa2.everbridge.net")
                                            .append("apiUrl", "https://api-qa2.everbridge.net/rest")
                                            .append("ebadminUrl", "https://ebadmin-qa2.everbridge.net")
                                            .append("memberPortalUrl", "https://member-qa2.everbridge.net");
		
		BasicDBObject env_stage_doc = new BasicDBObject("environment", "stage")
											.append("baseUrl", "https://manager-stage.everbridge.net")
											.append("apiUrl", "https://api-stage.everbridge.net/rest")
											.append("ebadminUrl", "https://ebadmin-stage.everbridge.net")
											.append("memberPortalUrl", "https://member-stage.everbridge.net");
		
		BasicDBObject env_product_doc = new BasicDBObject("environment", "product")
											.append("baseUrl", "https://manager.everbridge.net")
											.append("apiUrl", "https://api.everbridge.net/rest")
											.append("ebadminUrl", "https://ebadmin.everbridge.net")
											.append("memberPortalUrl", "https://member.everbridge.net");
		
		collection.createIndex(new BasicDBObject("environment", "text"), null, true);
		
		connector.insertDocument(dbName, collectionName, env_qa1_doc);
		connector.insertDocument(dbName, collectionName, env_qa2_doc);
		connector.insertDocument(dbName, collectionName, env_stage_doc);
		connector.insertDocument(dbName, collectionName, env_product_doc);
		
		connector.listAllDocuments(dbName, collectionName);
	}
}
