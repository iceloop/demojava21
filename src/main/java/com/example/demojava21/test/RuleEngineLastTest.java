package com.example.demojava21.test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class RuleEngineLastTest {

    public static class RuleEngine {

        private RestHighLevelClient client;

        public RuleEngine(RestHighLevelClient client) {
            this.client = client;
        }

        public boolean evaluateRules(String index) throws IOException {
            // 输出开始时间
            long startTime = System.currentTimeMillis();
            System.out.println("Start time: " + startTime);

            BoolQueryBuilder query = QueryBuilders.boolQuery()
                    .must(QueryBuilders.wildcardQuery("clientName", "*dodo*"));

            SearchRequest searchRequest = new SearchRequest(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            searchSourceBuilder.size(0); // 设置size为0只返回匹配数量
            searchSourceBuilder.fetchSource(false); // 不需要返回_source

            searchRequest.source(searchSourceBuilder);

            // Execute the search request
            SearchResponse searchResponse = client.search(searchRequest);

            long endTime = System.currentTimeMillis();
            System.out.println("End time: " + endTime);
            System.out.println("Total time: " + (endTime - startTime) + "ms");

            // 返回匹配数量是否大于0
            return searchResponse.getHits().getTotalHits() > 0;
        }
    }

    public static void main(String[] args) throws IOException {
        // Elasticsearch credentials and connection settings
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

        RuleEngine engine = new RuleEngine(client);
        boolean exists = engine.evaluateRules("insurance_iceloop");
        System.out.println("Data exists: " + exists);

        client.close();
    }
}
