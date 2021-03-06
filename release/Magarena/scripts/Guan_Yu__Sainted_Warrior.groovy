[
    new ThisDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPermanent died) {
            return permanent.isOwner(permanent.getController()) ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(),
                    this,
                    "PN may\$ shuffle SN into his or her library."
                ) :
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCard card = event.getPermanent().getCard()
            if (event.isYes() && card.isInGraveyard()) {
                game.doAction(new ShiftCardAction(card, MagicLocationType.Graveyard, MagicLocationType.TopOfOwnersLibrary));
                game.doAction(new ShuffleLibraryAction(card.getOwner()));
            }
        }
    }
]
