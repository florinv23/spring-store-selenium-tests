package com.boot.selenium.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationsUtil {
    private static Properties configProps;

    public static Properties getConfigProperties() throws IOException {
        if (configProps == null) {
            configProps = new Properties();
            String basePath = ConfigurationsUtil.class.getResource("/").getPath();
            if("dev".equals(System.getenv("environment"))){
                try(InputStream in = new FileInputStream(basePath + "configs-dev.properties")){
                    configProps.load(in);
                }
            }else{
                try(InputStream in = new FileInputStream(basePath + "configs.properties")){
                    configProps.load(in);
                }
            }
        }

        return configProps;
    }
}
