package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicCounterType;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicSubType;
import magic.model.action.MagicChangeCountersAction;
import magic.model.action.MagicPlayTokenAction;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicSimpleMayChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicDestroyTargetPicker;
import magic.model.trigger.MagicWhenOtherComesIntoPlayTrigger;

public class Turntimber_Ranger {
    public static final MagicWhenOtherComesIntoPlayTrigger T = new MagicWhenOtherComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (otherPermanent.getController() == permanent.getController() &&
                    otherPermanent.hasSubType(MagicSubType.Ally)) ?
                new MagicEvent(
                    permanent,
                    new MagicSimpleMayChoice(
                        MagicSimpleMayChoice.PLAY_TOKEN,
                        1,
                        MagicSimpleMayChoice.DEFAULT_YES
                    ),
                    new MagicDestroyTargetPicker(false),
                    this,
                    "PN may$ put a 2/2 green Wolf creature " +
                    "token onto the battlefield. If you do, put a " +
                    "+1/+1 counter on SN."
                ) :
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            if (event.isYes()) {
                game.doAction(new MagicPlayTokenAction(
                        event.getPlayer(),
                        TokenCardDefinitions.get("Wolf")));
                game.doAction(new MagicChangeCountersAction(
                        event.getPermanent(),
                        MagicCounterType.PlusOne,
                        1,
                        true));
            }        
        }        
    };
}
