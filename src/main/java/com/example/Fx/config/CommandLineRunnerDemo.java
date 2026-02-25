package com.example.Fx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
@Slf4j
public class CommandLineRunnerDemo implements CommandLineRunner {

    @Autowired
    Environment environment;

    //@ConfigurationProperties(prefix = "fx")
    @Value("${fx.message}")
    String fxMessage;

    @Value("${fx.default:test}")
    String fxDefault;

    @Override
    public void run(String... args) throws Exception {


        log.info("CommandLineRunnerDemo hit...");
        log.debug("CommandLineRunnerDemo debug level log...");

        String[] activeProfiles = environment.getActiveProfiles();
        log.info("activeProfiles = {}", Arrays.toString(activeProfiles));

        for (String arg : args) {
            System.out.println("Argument: " + arg);
        }

        log.info("fx.message : {}", fxMessage);//local-hello
        log.info("fx.default : {}", fxDefault);//test i.e the fallback value since fx.default is absent in yml file

        //java -jar target/Fx-0.0.1-SNAPSHOT.jar --debug --env=prod foo bar --spring.profiles.active=prod
        //java -jar target/Fx-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev foo bar
        //java -jar target/Fx-0.0.1-SNAPSHOT.jar foo bar
    }
}
