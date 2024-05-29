package com.example.demojava21.test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class RuleEngineTest {

    public static class RuleEngine {

        private RestHighLevelClient client;

        public RuleEngine(RestHighLevelClient client) {
            this.client = client;
        }

        public void evaluateRules(String index) throws IOException {
            // Example for rule 251: Check if the policy involves prohibited activities
            // 输出开始时间
            System.out.println(System.currentTimeMillis());
            BoolQueryBuilder query = QueryBuilders.boolQuery()
                    .must(QueryBuilders.boolQuery()
                    .should(QueryBuilders.wildcardQuery("clientName", "haha")));

            SearchRequest searchRequest = new SearchRequest(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            searchRequest.source(searchSourceBuilder);

            // Execute the search request
            SearchResponse searchResponse = client.search(searchRequest);

            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println("Rule 251 violated: 涉及空调或电梯安装维修人员，属禁止承保业务");
                // 输出结束时间
                System.out.println(System.currentTimeMillis());
                System.out.println(hit.getSourceAsString());
            }
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
        engine.evaluateRules("insurance_iceloop");
        client.close();
    }
}
