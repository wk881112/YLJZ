package com.neo.spring.cat;

import com.dianping.cat.configuration.ClientConfigProvider;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Server;

import java.util.ArrayList;
import java.util.List;

public class DemoClientConfigProvider implements ClientConfigProvider {

    @Override
    public ClientConfig getClientConfig() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("127.0.0.1"));

        String domain = "demo-app";

        ClientConfig config = new ClientConfig();
        config.setServers(servers);
        config.setDomain(domain);

        return config;
    }

}