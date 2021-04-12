package com.example.faunaapp;

import dagger.Component;

@Component
public interface ClientComponent {
    Client getClient();
}
