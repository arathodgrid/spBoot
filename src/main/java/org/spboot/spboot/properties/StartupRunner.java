package org.spboot.spboot.properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(Integer.MAX_VALUE)   // 👈 runs LAST
public class StartupRunner implements CommandLineRunner {

    private final Environment environment;
    private final ApplicationContext context;

    public StartupRunner(Environment environment, ApplicationContext context) {
        this.environment = environment;
        this.context = context;
    }

    @Override
    public void run(String... args) {

        System.out.println("\n========== APPLICATION PROPERTIES ==========\n");

        System.out.println("app.date = " + environment.getProperty("app.date"));
        System.out.println("app.date-format = " + environment.getProperty("app.date-format"));
        System.out.println("spring.application.name = " + environment.getProperty("spring.application.name"));

        System.out.println("\n========== APPLICATION PROPERTIES END ==========\n\n");

        System.out.println("\n========== CUSTOM BEANS ONLY ==========\n");

        String[] beans = context.getBeanDefinitionNames();
        Arrays.sort(beans);

        for (String bean : beans) {

            Object beanInstance = context.getBean(bean);

            String packageName = beanInstance.getClass().getPackageName();

            if (packageName.startsWith("org.spboot")) {
                System.out.println(bean + " -> " + beanInstance.getClass().getSimpleName());
            }
        }

        System.out.println("\n========== CUSTOM BEANS END ==========\n\n");

    }
}