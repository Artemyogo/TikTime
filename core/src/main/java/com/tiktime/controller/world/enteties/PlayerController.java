package com.tiktime.controller.world.enteties;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.WorldModel;
import com.tiktime.model.entities.livingenteties.EnemyModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.common.Direction;
import com.tiktime.common.LivingEntityState;
import com.tiktime.common.WeaponType;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.screens.DeathScreen;
import com.tiktime.screens.Screen;
import com.tiktime.screens.ScreenHandler;
import com.tiktime.view.world.WorldView;

public class PlayerController implements EventListener, Disposable {
    private final PlayerModel playerModel;
    private final WorldView worldView;
    private final ScreenHandler screenHandler;
    private final float baseDamageTime = GameConfig.getPlayerBaseDamageTime();
    private float damageTimeLeft = GameConfig.getPlayerDamageTimeLeft();
    private boolean attacking = false;
    private boolean dead = false;

    public PlayerController(PlayerModel playerModel, WorldView worldView, ScreenHandler screenHandler) {
        this.playerModel = playerModel;
        this.worldView = worldView;
        this.screenHandler = screenHandler;

        worldView.setPlayer(
            playerModel.getPosition().x,
            playerModel.getPosition().y,
            GameConfig.getPlayerConfig().getWidth(),
            GameConfig.getPlayerConfig().getHeight(),
            playerModel.getCurrentHealth(),
            playerModel.getMaxHealth(),
            PlayerModel.CurrentStats.getCoins(),
            Direction.EAST,
            LivingEntityState.IDLE,
            WeaponType.AK47
        );

        subscribeOnEvents();
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
//        Gdx.app.log("PlayerController", "setAttacking: " + attacking);
    }

    private void subscribeOnEvents() {
        EventManager.subscribe(GameEventType.PLAYER_ATTACKED, this);
        EventManager.subscribe(GameEventType.PLAYER_DEATH, this);
        EventManager.subscribe(GameEventType.ENEMY_DEATH, this);
    }

    private void unsubscribeOnEvents() {
        EventManager.unsubscribe(GameEventType.PLAYER_ATTACKED, this);
        EventManager.unsubscribe(GameEventType.PLAYER_DEATH, this);
        EventManager.unsubscribe(GameEventType.ENEMY_DEATH, this);
    }

    public void updateWeaponRotation(float rotationDeg) {
        playerModel.updateWeaponRotation(rotationDeg);
    }

    public void update(float delta, Vector2 direction) {
        if (dead) {
            screenHandler.setScreen(Screen.DEATH_SCREEN);
        }
        damageTimeLeft = Math.max(0, damageTimeLeft - delta);
        worldView.setPlayerIsAttacked(damageTimeLeft != 0);

        playerModel.updateAttackCooldownTimer(delta);
        if (attacking) {
            playerModel.tryAttack();
        }
        worldView.setPlayerIsAttacking(attacking);

        playerModel.setDirectionAndMove(direction, delta);

        Vector2 playerPosition = playerModel.getPosition();
        worldView.setPlayerCoordinates(playerPosition.x, playerPosition.y);

        worldView.setPlayerCurHealth(playerModel.getCurrentHealth());
        worldView.setPlayerMaxHealth(playerModel.getMaxHealth());

        if (direction.equals(Vector2.Zero)) {
            worldView.setPlayerState(LivingEntityState.IDLE);
        } else {
            if (direction.x != 0) {
                worldView.setPlayerDirection(Direction.getDirection(direction));
            }
            worldView.setPlayerState(LivingEntityState.RUNNING);
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.type) {
            case PLAYER_ATTACKED: {
                if (!(event.data instanceof PlayerModel)) {
                    throw new RuntimeException("Invalid event data");
                }

                PlayerModel playerModel = (PlayerModel) event.data;
                if (this.playerModel != playerModel) {
                    throw new RuntimeException("Invalid event data");
                }

                damageTimeLeft = baseDamageTime;
                break;
            }

            case ENEMY_DEATH: {
                if (!(event.data instanceof EnemyModel)) {
                    throw new RuntimeException("Invalid event data");
                }

                worldView.getPlayerView().setCoins(UpgradeModel.getInstance().getMoney());
                break;
            }

            case PLAYER_DEATH: {
                dead = true;
                break;
            }

            default: throw new RuntimeException("Invalid event type");
        }
    }

    @Override
    public void dispose() {
        unsubscribeOnEvents();
    }
}
