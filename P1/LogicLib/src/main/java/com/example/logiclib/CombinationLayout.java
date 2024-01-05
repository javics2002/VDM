package com.example.logiclib;

import com.example.aninterface.Engine;
import com.example.aninterface.Font;
import com.example.aninterface.Graphics;
import com.example.aninterface.Input;

import java.util.ArrayList;
import java.util.List;

public class CombinationLayout implements GameObject {
    private final Text _combinationNumber;
    private final Combination _currentCombination;
    private final List<ColorSlot> _colors;
    private final List<HintSlot> _hints;
    private final Graphics _graphics;
    private final int _positionX, _positionY;
    private final int _scale;
    private final int _lateralMargin;

    private  GameAttributes _gameAttributes;

    public CombinationLayout(Engine engine, int number, int combinationLength, int positionX, int positionY, int scale, GameAttributes gameAttributes) {
        _graphics = engine.getGraphics();
        _gameAttributes = gameAttributes;

        _positionX = positionX;
        _positionY = positionY;

        _scale = scale;
        _lateralMargin = 5;
        int padding = 6;

        Font numberFont = _graphics.newFont("Comfortaa-Regular.ttf", 24f);
        _combinationNumber = new Text(Integer.toString(number + 1), numberFont, engine,
                _lateralMargin + 50, _positionY + _scale / 2, 0, true);

        _colors = new ArrayList<>();
        for (int i = 0; i < combinationLength; i++) {
            _colors.add(new ColorSlot(engine,
                    (int) (_positionX + (i - combinationLength / 2f) * (_scale + padding)),
                    _positionY - _scale / 2, _scale, _scale, _gameAttributes));
        }

        _hints = new ArrayList<>();
        for (int i = 0; i < combinationLength; i++) {
            _hints.add(new HintSlot(engine, (int) (_graphics.getLogicWidth() - 50 - _lateralMargin + (i % ((combinationLength + 1) / 2)
                            - combinationLength / 4f) * _scale / 2 + i % ((combinationLength + 1) / 2) * _lateralMargin / 2),
                    (i < combinationLength / 2f ? _positionY - _scale / 2 : _positionY) + (int) (_scale * .2f / 4),
                    (int) (_scale * .8f / 2), (int) (_scale * .8f / 2)));
        }

        _currentCombination = new Combination(combinationLength);
    }

    @Override
    public void render() {
        _graphics.drawRoundedRect(_lateralMargin, (int) (_positionY - _scale * 1.2f / 2),
                _graphics.getLogicWidth() - _lateralMargin * 2, (int) (_scale * 1.2f), 0xFFf8f4ed, 20, 20);

        _graphics.drawRect(_lateralMargin + 80, _positionY - _scale/2, 2, _scale, 0);
        _graphics.drawRect(_graphics.getLogicWidth() - _lateralMargin - 90, _positionY - _scale/2, 2, _scale, 0);

        _combinationNumber.render();

        for (ColorSlot color : _colors) {
            color.render();
        }

        for (HintSlot hint : _hints) {
            hint.render();
        }
    }

    @Override
    public void update() {
        for (ColorSlot color : _colors) {
            color.update();
        }
    }

    @Override
    public boolean handleEvents(Input.TouchEvent e) {
        for (int i = 0; i < _colors.size(); i++) {
            ColorSlot color = _colors.get(i);

            if (color.handleEvents(e)) {
                _currentCombination.deleteColor(i);
                color.deleteColor();
                return true;
            }
        }
        return false;
    }

    public void setNextColor(int colorID, boolean isEyeOpen) {
        // Coloca la imagen en el primer hueco del array
        for (int i = 0; i < _colors.size(); i++) {
            if (!_colors.get(i).hasColor()) {
                // Image
                _colors.get(i).setColor(colorID, isEyeOpen);

                // Combination
                _currentCombination.setNextColor(colorID);
                break;
            }
        }
    }

    // Devuelve true si el array de colores está lleno (Cuando el jugador completa una combinación)
    public boolean isFull() {
        for (int i = 0; i < _colors.size(); i++) {
            if (!_colors.get(i).hasColor()) {
                return false;
            }
        }
        return true;
    }

    public Combination getCurrentCombination() {
        return _currentCombination;
    }

    // Usando el array de las pistas, coloca la respectiva imagen, ya sea la pista negra o blanca
    public void setHints(Combination.HintEnum[] predictionHints) {
        for (int i = 0; i < predictionHints.length; i++) {
            if (predictionHints[i] == Combination.HintEnum.BLACK) {
                _hints.get(i).setColor(Colors.ColorName.BLACK);
            } else if (predictionHints[i] == Combination.HintEnum.WHITE) {
                _hints.get(i).setColor(Colors.ColorName.WHITE);
            }
        }
    }
}
