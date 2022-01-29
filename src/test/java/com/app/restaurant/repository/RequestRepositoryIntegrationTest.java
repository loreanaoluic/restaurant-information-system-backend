package com.app.restaurant.repository;

import com.app.restaurant.model.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class RequestRepositoryIntegrationTest {

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void FindAll_FindingAllNotDeletedRequests_ReturnsRequestsList(){
        List<Request> requests = requestRepository.findAllNotDeleted();
        assertEquals(2, requests.size());
    }
}
