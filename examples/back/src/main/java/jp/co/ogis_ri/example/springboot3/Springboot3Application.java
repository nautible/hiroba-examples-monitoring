package jp.co.ogis_ri.example.springboot3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class Springboot3Application {

	private Logger logger = LoggerFactory.getLogger(Springboot3Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Springboot3Application.class, args);
	}

	@RequestMapping("/")
    String home() {
        return "front application sample";
    } 

	@RequestMapping("/healthz")
    String healthz() {
        return "ok";
    }

	@RequestMapping("/users/{id}")
    public ResponseEntity<User> getUsers(@PathVariable("id") long userId) {
		logger.info("getUsers ID : {}", userId);
		User user = new User();

		try {
			// UserIDが10の時だけ遅延する
			if (userId == 10L) {
				Thread.sleep(5000);
				logger.error("DataBase Response Delay ID : {}", userId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (userId == 99L) {
			// UserIDが99の時はエラーを返す
			logger.error("System Error ID : {}", userId);
			HttpHeaders headers = new HttpHeaders();
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<User>(user, headers, status);
		}
		user.setId(userId);
		user.setName("hoge");
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<User>(user, headers, status);
    } 
}
