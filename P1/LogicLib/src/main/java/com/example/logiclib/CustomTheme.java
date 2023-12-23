package com.example.logiclib;

import com.example.aninterface.Engine;
import com.example.aninterface.Font;
import com.example.aninterface.Graphics;
import com.example.aninterface.Image;
import com.example.aninterface.Input;
import com.example.aninterface.Sound;

public class CustomTheme extends Button{
    private final int borderWidth = 5, _textCoinSeparation = 10, _coinSize = 20;

    private boolean _selected, _adquired;
    private int _index, _price;
    private int _priceGap;
    private final Sound _purchaseSound;
    private final Image _coin;
    private final Text _moneyText;
    private final int _backgroundColor, _buttonColor;

    CustomTheme(boolean selected, int index, int price, String backgroundColor, String buttonColor, Font font, Engine engine,
                     int positionX, int positionY, int width, int height, int priceGap, Image coin, Text moneyText) {
        super(Colors.colorValues.get(selected ? Colors.ColorName.GREEN : Colors.ColorName.BLACK), Integer.toString(price), font,
                engine, positionX, positionY, width, height);

        _index = index;
        _price = price;
        _selected = selected;
        _adquired = GameData.Instance().hasBackground(_index);
        _priceGap = priceGap;

        _backgroundColor = Colors.parseARGB(backgroundColor);
        _buttonColor = Colors.parseARGB(buttonColor);

        _purchaseSound = _engine.getAudio().loadSound("buy.mp3", false);
        _purchaseSound.setVolume(.5f);

        _coin = coin;
        _moneyText = moneyText;
    }

    @Override
    public boolean handleEvents(Input.TouchEvent event) {
        if (event.type == Input.InputType.PRESSED && inBounds(event.x, event.y)) {
            callback();

            return true;
        }

        return false;
    }

    @Override
    public void callback() {
        if (GameData.Instance().hasTheme(_index)) {
            _clickSound.play();

            GameData.Instance().setTheme(_index);
        }
        else if (GameData.Instance().purchaseTheme(_index, _price)) {
            _purchaseSound.play();

            _moneyText.setText(Integer.toString(GameData.Instance().getMoney()));
            _adquired = true;

            GameData.Instance().setTheme(_index);
        }
    }

    @Override
    public void update(double deltaTime){
        _selected = GameData.Instance().getCurrentTheme() == _index;
    }

    @Override
    public void render(Graphics graphics) {
        _graphics.drawRoundedRect(_positionX - borderWidth, _positionY - borderWidth,
                _width + 2 * borderWidth, _height + 2 * borderWidth,
                Colors.colorValues.get(_selected ? Colors.ColorName.GREEN : Colors.ColorName.BLACK), _arc, _arc);

        _graphics.drawRect(_positionX, _positionY, _width, _height / 2, _backgroundColor);
        _graphics.drawRect(_positionX, _positionY + _height / 2, _width, _height / 2, _buttonColor);

        if(!_adquired){
            int priceTagWidth = _graphics.getStringWidth(_text, _font) + _textCoinSeparation + _coinSize;
            _graphics.drawText(_text, _font, _positionX + _width / 2 - priceTagWidth / 2,
                    _positionY + _height + _priceGap / 2 + _graphics.getStringHeight(_text, _font) / 2, 0);
            _graphics.drawImage(_coin, _positionX + _width / 2 + priceTagWidth / 2 - _coinSize,
                    _positionY + _height + _priceGap / 2 - _coinSize / 2, _coinSize, _coinSize);
        }
    }
}
