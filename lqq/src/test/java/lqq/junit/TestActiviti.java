package lqq.junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActiviti {

    @Test
    public void testOneCreateTable() {
        ProcessEngineConfiguration config = ProcessEngineConfiguration
            .createStandaloneInMemProcessEngineConfiguration();

        config.setJdbcDriver("com.mysql.jdbc.Driver");
        config.setJdbcUrl(
            "jdbc:mysql://localhost:3306/test?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");
        config.setJdbcUsername("root");
        config.setJdbcPassword("123456");
        
        config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine engine = config.buildProcessEngine();
        System.out.println(engine);
    }
    
    @Test
    public void testTwoCreateTable() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine engine = configuration.buildProcessEngine();
        System.out.println(engine);
    }

}
