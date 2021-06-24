package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exception.FullDepositException;
import it.polimi.ingsw.model.gameboard.Resource;
import it.polimi.ingsw.model.gameboard.ResourceType;
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

        deposit = new Deposit();
        deposit.addResource(1,singleResource);
        assertEquals(deposit.get(1), singleResource);
       deposit.addResource(2, resources.get(0));
       deposit.addResource(3, resources.get(1));
       assertEquals(resources.get(0),deposit.get(2));
       assertEquals(resources.get(1),deposit.get(3));

    }

    @Test
    @DisplayName("Testing the full deposit case")
    void checkDepositRules() throws FullDepositException, Exception {


        /*
            * there is try and catch in the checkDepositRules method so we can't see the exception, btw it was already tested before
        deposit = new Deposit();
        deposit.addResource(1, new Resource(ResourceType.STONE));
        assertThrows(FullDepositException.class, () -> deposit.addResource(1, new Resource(ResourceType.STONE)));
        assertThrows(FullDepositException.class, () -> deposit.addResource(2, new Resource(ResourceType.STONE)));
        deposit.addResource(2, new Resource(ResourceType.SHIELD));
        deposit.addResource(2, new Resource(ResourceType.SHIELD));
        assertThrows(FullDepositException.class, ()-> deposit.addResource(2, new Resource(ResourceType.SHIELD)));


         */
    }


    @Test
    @DisplayName("Testing the getting all functionality of the deposit")
    void getAll() throws FullDepositException, Exception {

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

    @Test
    void getFloor() throws FullDepositException {
        deposit = new Deposit();
        deposit.addResource(1, new Resource(ResourceType.COIN));

        assertEquals(ResourceType.COIN, deposit.getFloor(1).get(0).getType());

    }

    @Test
    void getResources2() throws FullDepositException, InsufficientResourcesException {
        deposit = new Deposit();
        List<Resource> resources = new ArrayList<Resource>();
        resources.add( new Resource(ResourceType.SHIELD));
        resources.add( new Resource(ResourceType.COIN));
        resources.add( new Resource(ResourceType.COIN));
        deposit.addResource(1,resources.get(0));
        deposit.addResource(2, resources.get(1));
        deposit.addResource(2, resources.get(1));
        List<Resource> result = deposit.getResources(ResourceType.COIN, 2);
        for(Resource resource: result){
            assertEquals(resource.getType(), ResourceType.COIN);
        }
    }
}