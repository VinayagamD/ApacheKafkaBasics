package com.vinaylogics.kafkatraining.tutorial3;

import com.vinaylogics.twitterkeyprtoection.ElasticKeyProvider;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ElasticSearchConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class);

    public static RestHighLevelClient createClient() {

        // replace with own credentials
        ElasticKeyProvider keyProvider = ElasticKeyProvider.getInstance();
        // don't do if you run local ES
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(keyProvider.getUsername(), keyProvider.getPassword()));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(keyProvider.getHostname(), 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = createClient();
        String jsonString ="{ \"foo\": \"bar\"}";
        IndexRequest indexRequest = new IndexRequest(
                "twitter",
                "tweets"
        ).source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        String id = indexResponse.getId();
        logger.info(id);

        // close the client gracefully
        client.close();
    }
}
