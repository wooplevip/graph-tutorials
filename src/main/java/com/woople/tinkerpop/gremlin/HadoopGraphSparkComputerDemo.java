package com.woople.tinkerpop.gremlin;

import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.tinkerpop.gremlin.hadoop.structure.HadoopGraph;
import org.apache.tinkerpop.gremlin.process.computer.clone.CloneVertexProgram;
import org.apache.tinkerpop.gremlin.spark.process.computer.SparkGraphComputer;

import java.io.File;

public class HadoopGraphSparkComputerDemo {
    public static void main(String[] args) throws Exception {
        FileConfiguration configuration = new PropertiesConfiguration();
        configuration.load(new File(args[0]));

        final Configuration hadoopConfig = new Configuration(false);

        if ("kerberos".equalsIgnoreCase(hadoopConfig.get("hadoop.security.authentication"))) {

            UserGroupInformation.setConfiguration(hadoopConfig);
            try {
                UserGroupInformation userGroupInformation =
                        UserGroupInformation.loginUserFromKeytabAndReturnUGI(configuration.getString("user.principal"), configuration.getString("user.keytab"));
                UserGroupInformation.setLoginUser(userGroupInformation);

                System.out.println("Login successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HadoopGraph graph = HadoopGraph.open(configuration);
        graph.compute(SparkGraphComputer.class).program(CloneVertexProgram.build().create()).submit().get();
    }
}
