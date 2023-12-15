package com.example.logiclib;

import com.example.aninterface.Engine;
import com.example.aninterface.Font;
import com.example.aninterface.Graphics;
import com.example.aninterface.Input;
import com.example.aninterface.Scene;


public class InitialScene implements Scene {
    private final Button _quickGameButton, _worldsButton;

    private final Button _shareButton;
    private final Text _titleText;
    Engine _engine;

    public InitialScene(Engine engine) {
        _engine = engine;

        final Graphics graphics = _engine.getGraphics();

        Font _titleFont = graphics.newFont("Comfortaa-Regular.ttf", 48f);
        String title = "Master Mind";

        int titleWidth = graphics.getStringWidth(title, _titleFont);
        _titleText = new Text(title, _titleFont, _engine,
                graphics.getLogicWidth() / 2 - titleWidth / 2, 200, 0);

        final int buttonWidth = 330;
        final int buttonHeight = 90;
        final int quickGameButtonPositionY = graphics.getLogicHeight() / 2;
        final int paddingY = 30;

        final Scene returnScene = this;

        Font buttonFont = graphics.newFont("Comfortaa-Regular.ttf", 35f);
        _quickGameButton = new Button(Colors.ColorName.BACKGROUNDBLUE, "Partida rápida", buttonFont, _engine,
                graphics.getLogicWidth() / 2 - buttonWidth / 2, quickGameButtonPositionY, buttonWidth, buttonHeight) {
            @Override
            public void callback() {
                Scene scene = new GameScene(_engine,  8, 4, 6,
                        false, returnScene,null);
                _engine.setCurrentScene(scene);
            }
        };

        _worldsButton = new Button(Colors.ColorName.BACKGROUNDBLUE, "Explorar mundos", buttonFont, _engine,
                graphics.getLogicWidth() / 2 - buttonWidth / 2, quickGameButtonPositionY + buttonHeight + paddingY,
                buttonWidth, buttonHeight) {
            @Override
            public void callback() {
                Scene scene = new WorldScene(_engine, 1);
                _engine.setCurrentScene(scene);
            }
        };



        _shareButton = new Button(Colors.ColorName.BACKGROUNDBLUE, "Compartir :d", buttonFont, _engine,
                graphics.getLogicWidth() / 2 - buttonWidth / 2, (int)(graphics.getLogicHeight() - 100), buttonWidth, buttonHeight) {
            @Override
            public void callback() {
                _engine.shareScreenshot(graphics.getWidth(), graphics.getHeight());
            }
        };
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render(Graphics gr) {
        _titleText.render();
        _quickGameButton.render();
        _worldsButton.render();
        _shareButton.render();

    }

    @Override
    public void handleEvents(Input a) {
        if (a.getTouchEvent().size() > 0) {
            _quickGameButton.handleEvents(a.getTouchEvent().get(0));
            _worldsButton.handleEvents(a.getTouchEvent().get(0));

            _shareButton.handleEvents(a.getTouchEvent().get(0));
        }
    }
}




