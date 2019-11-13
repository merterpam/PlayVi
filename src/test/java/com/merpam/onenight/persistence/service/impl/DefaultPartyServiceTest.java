package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.AbstractIntegrationTest;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.utils.TestUtils;
import de.flapdoodle.embed.process.collections.Collections;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DefaultPartyServiceTest extends AbstractIntegrationTest {

    private static final String PARTY_ONE_ID = "ID_ONE";
    private static final String PARTY_ONE_PIN = "1111";
    @Autowired
    private PartyService partyService;

    @Test
    public void testFindAll() {

        final PartyModel samplePartyOne = TestUtils.loadData("test/sampledata/party/partyOne.json", PartyModel.class);
        final PartyModel samplePartyTwo = TestUtils.loadData("test/sampledata/party/partyTwo.json", PartyModel.class);

        insertData(Collections.newArrayList(samplePartyOne, samplePartyTwo));
        List<PartyModel> parties = partyService.findAll();

        Optional<PartyModel> optionalSamplePartyOne = parties.stream()
                .filter(party -> {
                    assert samplePartyOne != null;
                    return StringUtils.equals(party.getId(), samplePartyOne.getId());
                })
                .findAny();

        Assert.assertTrue(optionalSamplePartyOne.isPresent());
        Assert.assertEquals(TestUtils.convertToJson(samplePartyOne), TestUtils.convertToJson(optionalSamplePartyOne.get()));

        Optional<PartyModel> optionalSamplePartyTwo = parties.stream()
                .filter(party -> {
                    assert samplePartyTwo != null;
                    return StringUtils.equals(party.getId(), samplePartyTwo.getId());
                })
                .findAny();

        Assert.assertTrue(optionalSamplePartyTwo.isPresent());
        Assert.assertEquals(TestUtils.convertToJson(samplePartyTwo), TestUtils.convertToJson(optionalSamplePartyTwo.get()));
    }

    @Test
    public void testFindPartyById() {
        final PartyModel samplePartyOne = TestUtils.loadData("test/sampledata/party/partyOne.json", PartyModel.class);

        insertData(Collections.newArrayList(samplePartyOne));

        PartyModel foundParty = partyService.findPartyById(PARTY_ONE_ID);

        Assert.assertEquals(TestUtils.convertToJson(samplePartyOne), TestUtils.convertToJson(foundParty));
    }

    @Test
    public void testFindPartyByPin() {
        final PartyModel samplePartyOne = TestUtils.loadData("test/sampledata/party/partyOne.json", PartyModel.class);

        insertData(Collections.newArrayList(samplePartyOne));

        PartyModel foundParty = partyService.findPartyByPin(PARTY_ONE_PIN);

        Assert.assertEquals(TestUtils.convertToJson(samplePartyOne), TestUtils.convertToJson(foundParty));
    }

    @Test
    public void delete() {
        final PartyModel samplePartyOne = TestUtils.loadData("test/sampledata/party/partyOne.json", PartyModel.class);
        insertData(Collections.newArrayList(samplePartyOne));

        Assert.assertNotNull(partyService.findPartyById(PARTY_ONE_ID));

        partyService.deleteById(PARTY_ONE_ID);

        Assert.assertNull(partyService.findPartyById(PARTY_ONE_ID));
    }
}