package com.proxichat.examples.guice.store;

public class StorageProviderFactory {

    public static Class<? extends StorageProvider> create(StorageProviderType type) {
        Class<? extends StorageProvider> providerClass = null;
        if (type == StorageProviderType.SIMPLE) {
            providerClass = SimpleStorageProvider.class;
        }
        return providerClass;
    }

}
