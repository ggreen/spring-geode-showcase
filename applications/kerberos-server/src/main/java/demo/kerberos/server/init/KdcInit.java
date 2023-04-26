package demo.kerberos.server.init;

import org.apache.kerby.kerberos.kerb.KrbException;
import org.apache.kerby.kerberos.tool.kdcinit.KdcInitTool;

public class KdcInit {
    public static void main(String[] args) throws KrbException {

        var serverDir = "/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/applications/kerberos-server/runtime/config/";
        var keytab = "/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/deployments/local/kerberos/keytab";

        String[] kArgs = {serverDir,keytab};
        KdcInitTool.main(kArgs);
    }
}
