package jp.co.ogis_ri.example.springboot3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ExampleController {

    @Autowired
    private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(Springboot3Application.class);

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
		UserClient userClient = new UserClient(restTemplate, "http://springboot3-back:8080");
		User user = userClient.getUser(userId);

        if (userId == 99L) {
            HttpHeaders headers = new HttpHeaders();
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<User>(user, headers, status);
        }
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<User>(user, headers, status);
    } 
}
