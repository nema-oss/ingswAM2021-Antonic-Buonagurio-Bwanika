package it.polimi.ingsw.gameboard;

/*
 * this class represent the Resources
 */

public class Resource implements Producible{

    private final ResourceType type;
    private String className;

    public Resource(ResourceType type){
        this.className = getClass().getName();
        this.type = type;
    }

    /*
     * this method returns the type of the Resource
     * @return resource's type (type:ResourceType)
    */

    public ResourceType getType() {
        return type;
    }

    @Override
    public String getClassName() {
        return className;
    }

    //public void useEffect(Player p){}  //?????

}
