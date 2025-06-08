package com.tiktime.controller.world.enteties;

import com.badlogic.gdx.utils.Disposable;
import com.tiktime.common.configs.GameConfig;
import com.tiktime.model.entities.livingenteties.PlayerModel;
import com.tiktime.model.entities.weapons.BulletModel;
import com.tiktime.model.events.EventListener;
import com.tiktime.model.events.EventManager;
import com.tiktime.model.events.GameEvent;
import com.tiktime.model.events.GameEventType;
import com.tiktime.view.world.WorldView;

import java.util.HashSet;
import java.util.Set;

public class BulletController implements EventListener, Disposable {
    private final Set<BulletModel> bulletModels = new HashSet<BulletModel>();
    private final WorldView worldView;

    public BulletController(WorldView worldView) {
        this.worldView = worldView;
        subscribeOnEvents();
    }

    private void subscribeOnEvents() {
        EventManager.subscribe(GameEventType.BULLET_CREATED, this);
        EventManager.subscribe(GameEventType.BULLET_DESTROYED, this);
    }

    private void unsubscribeOnEvents() {
        EventManager.unsubscribe(GameEventType.BULLET_CREATED, this);
        EventManager.unsubscribe(GameEventType.BULLET_DESTROYED, this);
    }

    public void update(float delta) {
        bulletModels.forEach(bulletModel -> {
           worldView.setBulletPosition(bulletModel.getBody().getPosition().x,
               bulletModel.getBody().getPosition().y,
               bulletModel.getId());
        });
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.type) {
            // TODO: btw at now we delete body in interaction and that is bad but idc
            case BULLET_CREATED: {
                BulletModel bulletModel = (BulletModel) event.data;
                bulletModels.add(bulletModel);
                worldView.addBullet(bulletModel.getBody().getPosition().x,
                    bulletModel.getBody().getPosition().y,
                    GameConfig.getBulletConfig().getWidth(),
                    GameConfig.getBulletConfig().getHeight(),
                    bulletModel.getRotationDeg(),
                    bulletModel.getId()
                    );
                break;
            }
            case BULLET_DESTROYED: {
                BulletModel bulletModel = (BulletModel) event.data;
                bulletModels.remove(bulletModel);
                worldView.deleteBullet(bulletModel.getId());
                break;
            }
            default:
                throw new RuntimeException("Unknown event type: " + event.type);
        }
    }

    @Override
    public void dispose() {
        unsubscribeOnEvents();
    }
}
