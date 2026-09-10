package com.example.avalon1amod;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.elytrafly.ElytraFly;
import meteordevelopment.orbit.EventHandler;

public class HighwayYESOBFix extends Module {
    private static final double TRIGGER_Y = 120.50;

    private static final int R_TO_SPACE_DELAY = 20;
    private static final int SPACE_TO_ELYTRA_DELAY = 4;

    private int sequence = 0;
    private int delayTicks = 0;

    private ElytraFly elytraFly;

    public HighwayYESOBFix() {
        super(
            Categories.Misc,
            "Highway-fix-(with-ob-floor)",
            "Disables ElytraFly, waits 1 second, presses Space, then re-enables ElytraFly when Y is at or below 120.50."
        );
    }

    @Override
    public void onActivate() {
        elytraFly = Modules.get().get(ElytraFly.class);

        sequence = 0;
        delayTicks = 0;

        releaseSpace();
    }

    @Override
    public void onDeactivate() {
        releaseSpace();

        if (sequence != 0 && elytraFly != null && !elytraFly.isActive()) {
            elytraFly.toggle();
        }

        sequence = 0;
        delayTicks = 0;

        super.onDeactivate();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null) {
            releaseSpace();
            sequence = 0;
            delayTicks = 0;
            return;
        }

        double y = mc.player.getY();

        if (y <= TRIGGER_Y && sequence == 0) {
            if (elytraFly != null && elytraFly.isActive()) {
                elytraFly.toggle();
            }

            delayTicks = R_TO_SPACE_DELAY;
            sequence = 1;

            return;
        }
       
        if (sequence == 1) {
            delayTicks--;

            if (delayTicks <= 0) {
                mc.options.jumpKey.setPressed(true);

                delayTicks = SPACE_TO_ELYTRA_DELAY;
                sequence = 2;
            }

            return;
        }

        if (sequence == 2) {
            delayTicks--;

            if (delayTicks <= 0) {
                releaseSpace();

                if (elytraFly != null && !elytraFly.isActive()) {
                    elytraFly.toggle();
                }

                sequence = 3;
            }

            return;
        }

        if (sequence == 3) {
            sequence = 0;
        }
    }

    private void releaseSpace() {
        mc.options.jumpKey.setPressed(false);
    }
}

