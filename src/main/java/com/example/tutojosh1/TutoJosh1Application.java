package com.example.tutojosh1;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

@SpringBootApplication
public class TutoJosh1Application {

    public static void main(String[] args) {
        SpringApplication.run(TutoJosh1Application.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                ConcurrentSkipListSet<String> observed = new ConcurrentSkipListSet<>();
                List<Thread> threads = new ArrayList<>();
                for (int i=0; i<1000;i++){
                    boolean fist = i == 0;
                    threads.add(Thread.ofVirtual().unstarted(new Runnable() {
                        @Override
                        public void run() {
                            if (fist) observed.add(Thread.currentThread().toString());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (fist) observed.add(Thread.currentThread().toString());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            if (fist) observed.add(Thread.currentThread().toString());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            if (fist) observed.add(Thread.currentThread().toString());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }));
                }

                for (Thread t:threads)
                    t.start();

                for (Thread t:threads)
                    t.join();


                System.out.println(observed);
            }
        };
    }

}

@Controller
@ResponseBody
class CustomerController{
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }
    @GetMapping("customers")
    Collection<Customer> customers(){
        return repository.findAll();
    }
}


interface CustomerRepository extends ListCrudRepository<Customer, Integer>{
}

record Customer(@Id Integer id, String name){}
