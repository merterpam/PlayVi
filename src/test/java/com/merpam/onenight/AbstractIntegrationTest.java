package com.merpam.onenight;

import com.merpam.onenight.configuration.OneNightApplicationTestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@Import(OneNightApplicationTestConfiguration.class)
@SpringBootTest
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Before
    public void setUpAbstract() {
        cleanDatabase();
        setUp();
    }

    @After
    public void tearDownAbstract() {
        cleanDatabase();
        tearDown();
    }

    protected void insertData(Collection<Object> objectsToSave) {
        mongoTemplate.insertAll(objectsToSave);
    }

    /**
     * Placeholder to add additional logic to @Before in subclasseses
     */
    protected void setUp() {

    }

    /**
     * Placeholder to add additional logic to @After in subclasseses
     */
    protected void tearDown() {

    }

    private void cleanDatabase() {
        mongoTemplate.getDb().drop();
    }
}
