package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.player.Player;
import sun.net.www.content.text.Generic;

public interface Producible {

        void useEffect(Player p);

        public <T> T getType();
}
