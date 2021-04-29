package com.example.faunaapp.client;

import dagger.Component;

@Component
public interface ClientComponent {
    Client getClient();
}
