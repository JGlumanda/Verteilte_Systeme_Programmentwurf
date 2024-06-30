package de.jglumanda.verteiltesysteme_programmentwurf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VerteilteSystemeProgrammentwurfApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerteilteSystemeProgrammentwurfApplication.class, args);
    }

}
