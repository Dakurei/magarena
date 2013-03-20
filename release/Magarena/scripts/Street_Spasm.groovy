[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            final int x = payedCost.getX();
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_CREATURE_WITHOUT_FLYING_YOU_DONT_CONTROL,
                new MagicDamageTargetPicker(x),
                x,
                this,
                "SN deals " + x + " damage to target creature\$ without flying you don't control."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            event.processTarget(game,choiceResults,new MagicTargetAction() {
                public void doAction(final MagicTarget target) {
                    final MagicDamage damage=new MagicDamage(event.getSource(),target,event.getRefInt());
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
        }
    },
    new MagicCardActivation(
        [
            MagicConditionFactory.ManaCost("{X}{X}{R}{R}")
        ],
        new MagicActivationHints(MagicTiming.Tapping,true),
        "Overload"
    ) {
        public MagicEvent[] getCostEvent(final MagicCard source) {
            return [
                new MagicPayManaCostEvent(source, source.getController(), MagicManaCost.create("{X}{X}{R}{R}"))
            ];
        }
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            final int x = payedCost.getX();
            return new MagicEvent(
                cardOnStack,
                x,
                this,
                "SN deals " + x + " damage to each creature\$ without flying you don't control."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final Collection<MagicPermanent> targets=
                game.filterPermanents(event.getPlayer(),MagicTargetFilter.TARGET_CREATURE_WITHOUT_FLYING_YOUR_OPPONENT_CONTROLS);
            for (final MagicPermanent target : targets) {
                final MagicDamage damage=new MagicDamage(event.getSource(),target,event.getRefInt());
                game.doAction(new MagicDealDamageAction(damage));
            }
        }
    }
]
