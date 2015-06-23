package com.proxichat.examples.guice;

import com.google.inject.AbstractModule;
import com.proxichat.examples.guice.service.Registrar;
import com.proxichat.examples.guice.service.ValidatingRegistrar;
import com.proxichat.examples.guice.store.StorageProviderType;
import com.proxichat.examples.guice.store.StorageProvider;
import com.proxichat.examples.guice.store.StorageProviderFactory;

public class RegistrationModule extends AbstractModule {
    private Class<? extends StorageProvider> storageProvider;
    private Class<? extends Registrar> registrar;

    private Class<? extends StorageProvider> getStorageProvider() {
        return storageProvider;
    }

    private Class<? extends Registrar> getRegistrar() {
        return registrar;
    }

    public static class Builder {
        private Class<? extends StorageProvider> storageProvider;
        private Class<? extends Registrar> registrar;

        public Builder() {
            // Set the builder's default values.
            this.storageProvider = StorageProviderFactory.create(StorageProviderType.SIMPLE);
            this.registrar = ValidatingRegistrar.class;
        }

        public Builder storageProvider(StorageProviderType storageProviderType) {
            this.storageProvider = StorageProviderFactory.create(storageProviderType);
            return this;
        }

        public Builder registrar(Class<? extends Registrar> registrarClass) {
            this.registrar = registrarClass;
            return this;
        }

        public RegistrationModule build() {
            return new RegistrationModule(this);
        }
    }

    private RegistrationModule(Builder builder) {
        this.storageProvider = builder.storageProvider;
    }

    @Override
    protected void configure() {
        bind(StorageProvider.class).to(this.getStorageProvider());
        bind(Registrar.class).to(ValidatingRegistrar.class);
    }

}
