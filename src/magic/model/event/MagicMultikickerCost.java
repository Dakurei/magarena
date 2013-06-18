package magic.model.event;

import magic.model.MagicManaCost;
import magic.model.MagicSource;
import magic.model.MagicGame;
import magic.model.choice.MagicKickerChoice;
import magic.model.action.MagicSetKickerAction;

public class MagicMultikickerCost extends MagicAdditionalCost implements MagicEventAction {
    
    final MagicManaCost manaCost;
    final String name;
    
    public static MagicMultikickerCost Replicate(final MagicManaCost aManaCost) {
        return new MagicMultikickerCost(aManaCost, "replicate");
    }
    
    public MagicMultikickerCost(final MagicManaCost aManaCost, final String aName) {
        manaCost = aManaCost;
        name = aName;
    }

    public MagicMultikickerCost(final MagicManaCost aManaCost) {
        this(aManaCost, "kicker");
    }
    
    public MagicEvent getEvent(final MagicSource source) {
        return new MagicEvent(
            source,
            new MagicKickerChoice(manaCost, name),
            this,
            "PN may pay " + manaCost.getText() + " any number of times$$."
        );
    }
        
    @Override
    public void executeEvent(final MagicGame game, final MagicEvent event) {
        event.payManaCost(game,event.getPlayer());
        if (event.isKicked()) {
            game.doAction(new MagicSetKickerAction(event.getCard(), event.getKickerCount()));
        }
    }
}
