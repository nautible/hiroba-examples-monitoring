package jp.co.ogis_ri.example.springboot3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

public class UserClient {

  private static final Logger logger = LoggerFactory.getLogger(UserClient.class);

  private RestTemplate restTemplate;
  private String url;

  public UserClient(RestTemplate restTemplate, String url) {
    this.restTemplate = restTemplate;
    this.url = url;
  }
  User getUser(@PathVariable("id") long id) {
    String url = String.format("%s/users/%d", this.url, id);
    return this.restTemplate.getForObject(url, User.class);
  }
}
