package com.example.logiclib;

import com.example.aninterface.Engine;
import com.example.aninterface.Font;
import com.example.aninterface.Graphics;
import com.example.aninterface.Image;
import com.example.aninterface.Input;

public class ColorButton extends GameObject {
    public int _colorID;
    private final Text _numberText;
    private final GameAttributes _gameAttributes;

    ColorButton(Engine engine, int positionX, int positionY, int width, int height, int colorID, GameAttributes gameAttributes) {
        super(engine, positionX, positionY, width, height, 1f);
        _gameAttributes = gameAttributes;

        _positionX = positionX;
        _positionY = positionY;
        _width = width;
        _height = height;
        _colorID = colorID;
        String num = String.valueOf(_colorID);
        Font _colorNum = _graphics.newFont("Comfortaa-Regular.ttf", 24);

        _numberText = new Text(num, _colorNum, engine, (int) (_positionX + _width / 2),
                (int) (_positionY + _height), 0, true);
    }

    @Override
    public boolean handleEvents(Input.TouchEvent e) {
        return e.type == Input.InputType.PRESSED && inBounds(e.x, e.y);
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        // Cuando el botón del OJO se pulsa, este condicional se encarga de cambiar todos los colores
        // a sus respectivas imagenes para daltónicos. Además, si el modo daltónico está activado,
        // también se encarga de quitar los números para volver al modo normal.
        _graphics.drawCircleWithBorder((int) (_positionX + _width / 2), (int) (_positionY + _height / 2),
                (int) (_width * _scale / 2), 1, Colors.getColor(_colorID - 1), Colors.colorValues.get(Colors.ColorName.BLACK));

        if (_gameAttributes.isEyeOpen) {
            _numberText.render();
        }
    }

    public boolean inBounds(int mouseX, int mouseY) {
        return _graphics.inBounds((int) _positionX, (int) _positionY, mouseX, mouseY, (int) (_width * _scale), (int) (_height * _scale));
    }
}
