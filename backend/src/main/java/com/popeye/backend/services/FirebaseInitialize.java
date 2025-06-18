package com.popeye.backend.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initFirebase() {
        // Only initialize Firebase if it hasn't already been initialized
        if (FirebaseApp.getApps().isEmpty()) {
            try (InputStream serviceAccount = getClass().getClassLoader()
                    .getResourceAsStream("firebase-configuration-key-popeye.json")) {

                if (serviceAccount == null) {
                    throw new IllegalStateException("firebase-configuration-key-popeye.json not found in classpath");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully.");

            } catch (Exception e) {
                System.err.println("Error initializing Firebase: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("ℹ️ Firebase already initialized — skipping re-initialization.");
        }
    }
}
