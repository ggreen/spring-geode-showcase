package demo.kerberos.server;

import org.apache.kerby.kerberos.kerb.KrbException;
import org.apache.kerby.kerberos.kerb.server.KdcServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class KerberosConfig {

    @Value("${kerberos.configDir}")
    private String confDir;

    @Value("${kerberos.realm:default}")
    private String realm;

    @Value("${kerberos.tcp.port:8800}")
    private int kdcPort;

    @Value("${kerberos.udp.port:8800}")
    private int kdcUdpPort;

    @Value("${kerberos.tcp.allow:true}")
    private boolean allowTcp;

    @Bean
    KdcServer server() throws KrbException {
        var server = new KdcServer(Paths.get(confDir).toFile());
        server.start();

        server.setKdcRealm(realm);

        server.setKdcPort(kdcPort);
        server.setKdcUdpPort(kdcUdpPort);
        server.setAllowTcp(allowTcp);

        server.enableDebug();

        return server;
    }

}
