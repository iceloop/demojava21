package com.example.demojava21.test;

/**
 * ElasticsearchDataInserter
 *
 * <p>创建人：hrniu 创建日期：2024/5/28
 */
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchDataInserter {

    public static void main(String[] args) throws IOException {
        String hostname = "9.23.27.139";
        int port = 9200;
        String scheme = "http";
        String username = "elastic";
        String password = "eG!2tlS*0aC@sXt";

        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(hostname, port, scheme))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        RestHighLevelClient client = new RestHighLevelClient(builder);

        // Create index with mappings
        //  createIndex(client);

        // Insert data
        insertBulkData(client);

        client.close();
    }

    private static void createIndex(RestHighLevelClient client) throws IOException {
        String indexName = "insurance_iceloop111";
        String mapping = "{\n" +
                "  \"mappings\": {\n" +
                "    \"_doc\": {\n" +  // Ensure type name is included
                "      \"properties\": {\n" +
                "        \"productCode\": {\"type\": \"keyword\"},\n" +
                "        \"processingType\": {\"type\": \"keyword\"},\n" +
                "        \"clientName\": {\"type\": \"text\"},\n" +
                "        \"businessNature\": {\"type\": \"text\"},\n" +
                "        \"underwriterComments\": {\"type\": \"text\"}\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.source(mapping, XContentType.JSON);

        CreateIndexResponse createIndexResponse = client.indices().create(request);

        if (createIndexResponse.isAcknowledged()) {
            System.out.println("Index created successfully.");
        } else {
            System.err.println("Index creation failed.");
        }
    }

    private static void insertBulkData(RestHighLevelClient client) throws IOException {
        BulkRequest request = new BulkRequest();

        for (int i = 1; i <= 10000; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("productCode", "1521000" + i % 5);
            jsonMap.put("processingType", "2");
            jsonMap.put("clientName", i % 2 == 0 ? "电梯" : "空调");
            jsonMap.put("businessNature", i % 2 == 0 ? "电梯安装" : "空调安装");
            jsonMap.put("underwriterComments", i % 2 == 0 ? "总部OA签批同意业务，签报号" : "无评论");

            request.add(new IndexRequest("insurance_iceloop111", "_doc")  // Specify type name
                    .id(String.valueOf(i))
                    .source(jsonMap, XContentType.JSON));
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("productCode", "1521000");
        jsonMap.put("processingType", "2");
        jsonMap.put("clientName",  "电梯haha" );
        jsonMap.put("businessNature",  "空调安装");
        jsonMap.put("underwriterComments", "无评论");

        request.add(new IndexRequest("insurance_iceloop111", "_doc")  // Specify type name
                .id(String.valueOf(200000))
                .source(jsonMap, XContentType.JSON));

        BulkResponse bulkResponse = client.bulk(request);

        if (bulkResponse.hasFailures()) {
            System.err.println("Bulk insert had failures: " + bulkResponse.buildFailureMessage());
        } else {
            System.out.println("Bulk insert completed successfully.");
        }
    }
}
