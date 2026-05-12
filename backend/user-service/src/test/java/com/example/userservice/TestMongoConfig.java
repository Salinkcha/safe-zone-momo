package com.example.userservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Configuration
@Profile("test")
public class TestMongoConfig {

    private MongodExecutable mongodExecutable;

    @PostConstruct
    public void startMongo() throws Exception {
        String ip = "localhost";
        int port = 27017;

        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.V4_4_16)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @PreDestroy
    public void stopMongo() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }
}
