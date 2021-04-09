package it.polimi.ingsw.player;

import it.polimi.ingsw.exception.FullDepositException;
import it.polimi.ingsw.gameboard.Resource;
import it.polimi.ingsw.gameboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
class DepositTest {

    private Deposit deposit;
    private ArrayList<Resource> resources;
    private Resource singleResource;

    @BeforeEach
    void setUp() {

        resources = new ArrayList<Resource>();
        Resource gold = new Resource(ResourceType.COIN);
        Resource servant1 = new Resource(ResourceType.SERVANT);
        Resource servant2 = new Resource(ResourceType.STONE);
        resources.add(gold);
        resources.add(servant1);
        resources.add(servant2);
        singleResource = new Resource(ResourceType.SHIELD);
    }

    @Test
    @DisplayName("Testing adding and getting resources from the deposit")
    void getResources() throws FullDepositException, Exception {

        /*
        deposit = new Deposit();
        Map<ResourceType, Integer> freq = resources.stream().collect(Collectors.groupingBy(Resource::getType, Collectors.summingInt(e->1)));
        deposit.addResources(resources);
        for (ResourceType type : freq.keySet()) {
            ArrayList<Resource> getResource = deposit.getResources(type, freq.get(type));
            assertEquals(freq.get(type).intValue(), getResource.size());
            for (Resource res : getResource)
                assertEquals(type, res.getType());

        }
        for(ResourceType type: freq.keySet()){
            assertThrows(InsufficientResourcesException.class, () -> deposit.getResources(type,freq.get(type)));
        }

         */

        deposit = new Deposit();
        deposit.addResource(0,singleResource);
        assertEquals(deposit.get(0), singleResource);
        for(int i = 0; i < resources.size(); i++){
            deposit.addResource(i+1,resources.get(i));

        }
        for(int i = 0; i < resources.size(); i++){
            assertEquals(resources.get(i),deposit.get(i+1));
        }
    }

    @Test
    @DisplayName("Testing the full deposit case")
    void checkDepositRules() throws FullDepositException, Exception {

        /*
        ArrayList<Resource> testResources = new ArrayList<Resource>();
        deposit = new Deposit();
        testResources.add(new Resource(ResourceType.STONE));
        testResources.add(new Resource(ResourceType.STONE));
        testResources.add(new Resource(ResourceType.SHIELD));
        testResources.add(new Resource(ResourceType.SHIELD));
        assertThrows(FullDepositException.class, ()->deposit.addResources(testResources));

         */

        deposit = new Deposit();
        deposit.addResource(1, new Resource(ResourceType.STONE));
        assertThrows(FullDepositException.class, () -> deposit.addResource(1, new Resource(ResourceType.STONE)));
        assertThrows(FullDepositException.class, () -> deposit.addResource(2, new Resource(ResourceType.STONE)));
        deposit.addResource(2, new Resource(ResourceType.SHIELD));
        deposit.addResource(2, new Resource(ResourceType.SHIELD));
        assertThrows(FullDepositException.class, ()-> deposit.addResource(2, new Resource(ResourceType.SHIELD)));

    }


    @Test
    @DisplayName("Testing the getting all functionality of the deposit")
    void getAll() throws FullDepositException, Exception {

        /*
        deposit = new Deposit();
        Map<ResourceType, Integer> freq = resources.stream().collect(Collectors.groupingBy(Resource::getType, Collectors.summingInt(e -> 1)));
        deposit.addResources(resources);
        Map<ResourceType,ArrayList<Resource>> resourcesFromDeposit = deposit.getAll();
        for(ResourceType type: freq.keySet()){
            assertEquals(freq.get(type), resourcesFromDeposit.get(type).size());
        }

         */

        deposit = new Deposit();
        List<Resource> resources = new ArrayList<Resource>();
        resources.add( new Resource(ResourceType.SHIELD));
        resources.add( new Resource(ResourceType.COIN));
        resources.add( new Resource(ResourceType.COIN));
        deposit.addResource(1,resources.get(0));
        deposit.addResource(2, resources.get(1));
        deposit.addResource(2, resources.get(1));
        Map<ResourceType, Integer> freq = resources.stream().collect(Collectors.groupingBy(Resource::getType, Collectors.summingInt(e -> 1)));
        Map<ResourceType,List<Resource>> resourcesFromDeposit = deposit.getAll();
        for(ResourceType type: freq.keySet()){
            assertEquals(freq.get(type), resourcesFromDeposit.get(type).size());
        }

    }


}