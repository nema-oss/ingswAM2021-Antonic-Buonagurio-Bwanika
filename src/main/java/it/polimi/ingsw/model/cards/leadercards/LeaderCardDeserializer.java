package it.polimi.ingsw.model.cards.leadercards;

import com.google.gson.*;

import java.lang.reflect.Type;

public class LeaderCardDeserializer implements JsonDeserializer<LeaderCard> {

    @Override
    public LeaderCard deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement type = jsonObject.get("leaderCardType");
        if (type != null) {
            switch (type.getAsString()) {
                case "DISCOUNT":
                    return jsonDeserializationContext.deserialize(jsonObject, Discount.class);

                case "EXTRA_DEPOSIT":
                    return jsonDeserializationContext.deserialize(jsonObject, ExtraDeposit.class);

                case "EXTRA_PRODUCTION":
                    return jsonDeserializationContext.deserialize(jsonObject, ExtraProduction.class);

                case "WHITE_TO_RESOURCE":
                    return jsonDeserializationContext.deserialize(jsonObject, WhiteToResource.class);
            }
        }
        return null;
    }
}
