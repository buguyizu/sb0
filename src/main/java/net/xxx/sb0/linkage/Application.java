package net.xxx.sb0.linkage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("net.xxx.sb0.linkage")
public class Application
{

    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    }

}
