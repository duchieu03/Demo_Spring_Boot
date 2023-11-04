package demobackend.demobackend;

import demobackend.demobackend.model.Authorize;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.Role;
import demobackend.demobackend.repository.LocalUserRepository;
import demobackend.demobackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoBackendApplication.class, args);
	}

	@Autowired
	RoleRepository repository;
	@Autowired
	LocalUserRepository localUserRepository;
	@Override
	public void run(String... args) throws Exception {

	}
}
