package com.example.logiclib;

import com.example.aninterface.Engine;
import com.example.aninterface.Font;
import com.example.aninterface.Graphics;
import com.example.aninterface.Image;
import com.example.aninterface.Input;
import com.example.aninterface.Scene;

import java.util.ArrayList;
import java.util.List;

public class GameScene implements Scene {
    private final Engine _engine;
    private final Graphics _graphics;
    private final GameAttributes _gameAttributes;
    private final Text _objectiveText, _attemptsText;
    private final Button _quitButton, _colorblindButton;
    private final List<CombinationLayout> _combinationLayouts;
    private final List<ColorButton> _colorButtons;
    private final Image _backgroundImage;

    private final int _visibleLayouts = 10;

    public GameScene(Engine engine, int tryNumber, int combinationLength, int numberOfColors,
                     boolean repeatedColors, final Scene returnScene,Combination cResult) {
        _engine = engine;
        _graphics = _engine.getGraphics();

        // Init GameAttributes
        _gameAttributes = new GameAttributes();

        _gameAttributes.attemptsNumber = tryNumber;
        _gameAttributes.attemptsLeft = tryNumber;
        _gameAttributes.combinationLength = combinationLength;
        _gameAttributes.colorNumber = numberOfColors;
        _gameAttributes.repeatedColors = repeatedColors;
        _gameAttributes.activeLayout = 0;
        _gameAttributes.isEyeOpen = false;
        if(cResult==null)_gameAttributes.resultCombination = new Combination(combinationLength, numberOfColors, repeatedColors);
        else _gameAttributes.resultCombination=cResult;
        _gameAttributes.returnScene = returnScene;


        // Title
        int verticalMargin = 5;
        Font objetiveFont = _graphics.newFont("Comfortaa-Regular.ttf", 24f);
        String objectiveString = "Averigua el código";
        _objectiveText = new Text(objectiveString, objetiveFont, _engine,
                (_graphics.getLogicWidth() / 2) - (_graphics.getStringWidth(objectiveString, objetiveFont) / 2),
                verticalMargin + _graphics.getStringHeight(objectiveString, objetiveFont), 0);

        // Attempts
        String attemptsString = "Te quedan " + _gameAttributes.attemptsLeft + " intentos.";
        Font attemptsFont = _graphics.newFont("Comfortaa-Regular.ttf", 16f);
        _attemptsText = new Text(attemptsString, attemptsFont, _engine,
                (_graphics.getLogicWidth() / 2) - (_graphics.getStringWidth(attemptsString, attemptsFont) / 2),
                verticalMargin + _graphics.getStringHeight(objectiveString, objetiveFont)
                        + _graphics.getStringHeight(attemptsString, attemptsFont) + 5,
                0);

        // Quit button
        int buttonDimension = 50;
        int horizontalMargin = 5;
        _quitButton = new Button("UI/close.png", _engine,
                horizontalMargin, verticalMargin, buttonDimension, buttonDimension) {
            @Override
            public void callback() {
                _engine.setCurrentScene(returnScene);
            }
        };

        // ColorBlind button
        _colorblindButton = new Button("UI/eyeClosed.png", _engine,
                _graphics.getLogicWidth() - buttonDimension - horizontalMargin, verticalMargin,
                buttonDimension, buttonDimension) {
            @Override
            public void callback() {
                _gameAttributes.isEyeOpen = !_gameAttributes.isEyeOpen;
                _colorblindButton.setImage(_gameAttributes.isEyeOpen ? "UI/eyeOpened.png" : "UI/eyeClosed.png");
            }
        };

        // Combination
        int initialHeight = 100;
        int verticalPadding = 15, scale = 40;
        _combinationLayouts = new ArrayList<>();
        for (int i = 0; i < tryNumber; i++) {
            _combinationLayouts.add(new CombinationLayout(_engine, i, combinationLength,
                    _graphics.getLogicWidth() / 2,
                    initialHeight + (verticalPadding + scale) * i, scale, _gameAttributes));
        }

        // Color buttons
        int horizontalPadding = 6;
        _colorButtons = new ArrayList<>();
        for (int i = 0; i < numberOfColors; i++) {
            _colorButtons.add(new ColorButton(_engine,
                    (int) (_graphics.getLogicWidth() / 2 + (i - numberOfColors / 2f) * (scale + horizontalPadding)),
                    _graphics.getLogicHeight() - 60, scale, scale, i + 1, _gameAttributes));
        }

        _backgroundImage = null;
    }

    public GameScene(Engine engine, int tryNumber, int combinationLength, int numberOfColors,
                     boolean repeatedColors, final Scene returnScene, int backgroundSkinId, int iconSkin,Combination cResult) {
        _engine = engine;
        _graphics = _engine.getGraphics();

        // Init GameAttributes
        _gameAttributes = new GameAttributes();

        _gameAttributes.attemptsNumber = tryNumber;
        _gameAttributes.attemptsLeft = tryNumber;
        _gameAttributes.combinationLength = combinationLength;
        _gameAttributes.colorNumber = numberOfColors;
        _gameAttributes.repeatedColors = repeatedColors;
        _gameAttributes.activeLayout = 0;
        _gameAttributes.isEyeOpen = false;
        //Guardamos la combinacion por si decide seguir jugando cuando pulsa el anuncio
        if(cResult==null)_gameAttributes.resultCombination = new Combination(combinationLength, numberOfColors, repeatedColors);
        else _gameAttributes.resultCombination=cResult;

        _gameAttributes.returnScene = returnScene;
        _gameAttributes.skin = iconSkin;
        _gameAttributes.backGroundSkinId=backgroundSkinId;

        // Title
        int verticalMargin = 5;
        Font objetiveFont = _graphics.newFont("Comfortaa-Regular.ttf", 24f);
        String objectiveString = "Averigua el código";
        _objectiveText = new Text(objectiveString, objetiveFont, _engine,
                (_graphics.getLogicWidth() / 2) - (_graphics.getStringWidth(objectiveString, objetiveFont) / 2),
                verticalMargin + _graphics.getStringHeight(objectiveString, objetiveFont), 0);

        // Attempts
        String attemptsString = "Te quedan " + _gameAttributes.attemptsLeft + " intentos.";
        Font attemptsFont = _graphics.newFont("Comfortaa-Regular.ttf", 16f);
        _attemptsText = new Text(attemptsString, attemptsFont, _engine,
                (_graphics.getLogicWidth() / 2) - (_graphics.getStringWidth(attemptsString, attemptsFont) / 2),
                verticalMargin + _graphics.getStringHeight(objectiveString, objetiveFont)
                        + _graphics.getStringHeight(attemptsString, attemptsFont) + 5,
                0);

        // Quit button
        int buttonDimension = 50;
        int horizontalMargin = 5;
        _quitButton = new Button("UI/close.png", _engine,
                horizontalMargin, verticalMargin, buttonDimension, buttonDimension) {
            @Override
            public void callback() {
                _engine.setCurrentScene(returnScene);
            }
        };

        // ColorBlind button
        _colorblindButton = new Button("UI/eyeClosed.png", _engine,
                _graphics.getLogicWidth() - buttonDimension - horizontalMargin, verticalMargin,
                buttonDimension, buttonDimension) {
            @Override
            public void callback() {
                _gameAttributes.isEyeOpen = !_gameAttributes.isEyeOpen;
                _colorblindButton.setImage(_gameAttributes.isEyeOpen ? "UI/eyeOpened.png" : "UI/eyeClosed.png");
            }
        };

        // Combination
        int initialHeight = 100;
        int verticalPadding = 15, scale = 40;
        _combinationLayouts = new ArrayList<>();
        for (int i = 0; i < tryNumber; i++) {
            _combinationLayouts.add(new CombinationLayout(_engine, i, combinationLength,
                    _graphics.getLogicWidth() / 2, initialHeight + (verticalPadding + scale) * i,
                    scale, _gameAttributes));
        }

        // Color buttons
        int horizontalPadding = 6;
        _colorButtons = new ArrayList<>();
        for (int i = 0; i < numberOfColors; i++) {
            _colorButtons.add(new ColorButton(_engine,
                    (int) (_graphics.getLogicWidth() / 2 + (i - numberOfColors / 2f) * (scale + horizontalPadding)),
                    _graphics.getLogicHeight() - 60, scale, scale, i + 1, _gameAttributes));
        }

        _backgroundImage = _graphics.loadImage("world"
                + Integer.toString(backgroundSkinId) + "/background.png");
    }

    @Override
    public void update(double deltaTime) {
        updateTriesText();

        CombinationLayout activeLayout = _combinationLayouts.get(_gameAttributes.activeLayout);
        if (activeLayout.isFull()) {
            if (activeLayout.getCurrentCombination().equals(_gameAttributes.resultCombination)) {
                // USER WON
                Scene gameOverScene = new GameOverScene(_engine, _gameAttributes);

                _engine.setCurrentScene(gameOverScene);
                return;
            }

            // Hints
            Combination.HintEnum[] hints = activeLayout.getCurrentCombination().getHint(_gameAttributes.resultCombination);
            activeLayout.setHints(hints);

            _gameAttributes.attemptsLeft--;
            _gameAttributes.activeLayout++;

            if (_gameAttributes.attemptsLeft == 0) {
                // User LOST
                Scene gameOverScene = new GameOverScene(_engine, _gameAttributes);
                _engine.setCurrentScene(gameOverScene);
            }
        }

        for (CombinationLayout combination : _combinationLayouts) {
            combination.update();
        }

        for (ColorButton colorButton : _colorButtons) {
            colorButton.update();
        }
    }

    @Override
    public void render(Graphics graphics) {
        if(_backgroundImage != null)
            _graphics.drawImage(_backgroundImage, 0, 60,
                    _graphics.getLogicWidth(), _graphics.getLogicHeight() - 60);

        _objectiveText.render();
        _attemptsText.render();
        _quitButton.render();
        _colorblindButton.render();

        int firstCombination = _gameAttributes.activeLayout - _visibleLayouts + 1;
        if (firstCombination < 0)
            firstCombination = 0;

        for (int i = firstCombination; i < _combinationLayouts.size() && i < firstCombination + _visibleLayouts; i++){
            _combinationLayouts.get(i).setPositionY(100 + (15 + 40) * (i - firstCombination));
            _combinationLayouts.get(i).render();
        }

        final int colorButtonBackgroundHeight = 80;
        graphics.drawRect(0, graphics.getLogicHeight() - colorButtonBackgroundHeight,
                graphics.getLogicWidth(), colorButtonBackgroundHeight, Colors.colorValues.get(Colors.ColorName.TRASPARENTBACKGROUND));

        for (ColorButton colorButton : _colorButtons)
            colorButton.render();
    }

    @Override
    public void handleEvents(Input input) {
        if (input.getTouchEvent().size() > 0) {
            Input.TouchEvent touchEvent = input.getTouchEvent().get(0);
            _colorblindButton.handleEvents(touchEvent);
            _quitButton.handleEvents(touchEvent);

            // Detectar click en colores ya colocados
            // Sirve para borrarlos
            _combinationLayouts.get(_gameAttributes.activeLayout).handleEvents(touchEvent);

            // Cuando detecta un click en un color, se coloca en el primer hueco posible.
            for (ColorButton colorButton : _colorButtons) {
                if (colorButton.handleEvents(touchEvent)) {
                    _combinationLayouts.get(_gameAttributes.activeLayout).setNextColor(colorButton._colorID,
                            _gameAttributes.isEyeOpen);
                    break;
                }
            }
        }

    }

    private void updateTriesText() {
        String attemptsString = "Te quedan " + _gameAttributes.attemptsLeft + " intentos.";
        if (_gameAttributes.attemptsLeft == 1) {
            attemptsString = "Este es tu último intento!";
        }

        _attemptsText.setText(attemptsString);
    }
}
