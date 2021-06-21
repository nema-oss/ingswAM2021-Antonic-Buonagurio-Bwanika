package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.player.Board;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class UpdatePlayerBoardMessage implements ActionMessage, Serializable {

    private final Board board;

    public UpdatePlayerBoardMessage(Board board){
        this.board = board;
    }

    @Override
    public void execute(VirtualView virtualView) {
    }

    @Override
    public void execute(View view) {
    }
}
