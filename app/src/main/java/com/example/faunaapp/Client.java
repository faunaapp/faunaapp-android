package com.example.faunaapp;

import com.apollographql.apollo.ApolloClient;

import javax.inject.Inject;

public class Client {
    private static final String path = "https://fauna-android-feature-t-cillum.herokuapp.com/";

    @Inject
    public Client() {

    }
    public ApolloClient getClient(){
       return ApolloClient.builder().serverUrl(path).build();
    }
}
