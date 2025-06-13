package com.tiktime.controller.enteties;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.*;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.common.configs.configdata.WeaponData;
import com.tiktime.model.entities.livingenteties.enemies.EnemyModel;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.model.upgrades.UpgradeModel;
import com.tiktime.screens.Screen;
import com.tiktime.screens.ScreenHandler;
import com.tiktime.view.enteties.livingenteties.PlayerView;
import com.tiktime.view.world.WorldView;

public class PlayerController implements Pausable, EventListener, Disposable {
    private final PlayerModel playerModel;
    private final PlayerView playerView;
    private final WorldView worldView;
    private final ScreenHandler screenHandler;
    private final float baseDamageTime = MagicConstants.PLAYER_BASE_DAMAGE_TIME;
    private float damageTimeLeft = MagicConstants.PLAYER_DAMAGE_TIME_LEFT;
    private boolean attacking = false;
    private boolean dead = false;

    public PlayerController(PlayerModel playerModel, WorldView worldView, ScreenHandler screenHandler) {
        this.playerModel = playerModel;
        this.screenHandler = screenHandler;

        this.worldView = worldView;
        this.playerView = worldView.setAndGetPlayer(
            playerModel.getPosition().x,
            playerModel.getPosition().y,
            GameConfig.getPlayerConfig().getWidth(),
            GameConfig.getPlayerConfig().getHeight(),
            playerModel.getCurrentHealth(),
            playerModel.getMaxHealth(),
            PlayerModel.CurrentStats.getCoins(),
            Direction.getDirection(playerModel.getDirection()),
            LivingEntityState.IDLE,
            playerModel.getWeaponModel().getWeaponType(),
            playerModel.getSpeed() * MagicConstants.MLT_SPEED_PLAYER_FRAME_DURATION,
            PlayerModel.CurrentStats.getAttackCooldown() * MagicConstants.MLT_ATTACK_FRAME_DURATION
        );

        subscribeOnEvents();
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        playerView.setIsAttacking(attacking);
    }

    public boolean isDead() {
        return dead;
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

    public void updateMousePosition(int screenX, int screenY) {
        float rotationDeg = getWeaponRotation(new Vector3(screenX, screenY, 0),
            getWeaponCenterPosition(playerModel.getPosition(), playerModel.getWeaponModel().getWeaponType()));

        playerView.updateWeaponRotationDeg(rotationDeg);
        playerModel.updateWeaponRotation(rotationDeg);
    }

    private float getWeaponRotation(Vector3 screenCoords, Vector3 weaponCoords) {
        Vector3 worldCoords = worldView.getWorldCamera().unproject(screenCoords);
        float dx = worldCoords.x - weaponCoords.x;
        float dy = worldCoords.y - weaponCoords.y;
        // FOR DEBUG
        worldView.setScreenCoords(new Vector3(worldCoords));
        worldView.setWeaponCoords(new Vector3(weaponCoords));

        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    private Vector3 getWeaponCenterPosition(Vector2 playerPosition, WeaponType weaponType) {
        WeaponData weaponConfig = GameConfig.getWeaponConfig(weaponType);

        if (weaponConfig == null) {
            throw new RuntimeException("WeaponConfig is null");
        }

        return new Vector3(playerPosition.x + weaponConfig.getOffsetX(), playerPosition.y + weaponConfig.getOffsetY(), 0f);
    }

    public void update(float delta, Vector2 direction) {
        if (dead) {
            screenHandler.setScreen(Screen.DEATH_SCREEN);
        }
        damageTimeLeft = Math.max(0, damageTimeLeft - delta);
        playerView.setIsAttacked(damageTimeLeft != 0);

        playerModel.updateAttackCooldownTimer(delta);
        if (attacking) {
            playerModel.tryAttack();
        }
        playerView.setIsAttacking(attacking);

        playerModel.setDirectionAndMove(direction, delta);

        Vector2 playerPosition = playerModel.getPosition();
        playerView.setPosition(playerPosition.x, playerPosition.y);

        playerView.setCurHealth(playerModel.getCurrentHealth());
        playerView.setMaxHealth(playerModel.getMaxHealth());

        if (direction.equals(Vector2.Zero)) {
            playerView.setState(LivingEntityState.IDLE);
        } else {
            if (direction.x != 0) {
                playerView.setDirection(Direction.getDirection(direction));
            }
            playerView.setState(LivingEntityState.RUNNING);
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

                playerView.setCoins(UpgradeModel.getInstance().getMoney());
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

    @Override
    public void setPaused(boolean paused) {
        playerView.setPaused(paused);
    }
}
