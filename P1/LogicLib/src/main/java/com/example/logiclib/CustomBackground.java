package com.example.logiclib;

import com.example.aninterface.Engine;
import com.example.aninterface.Font;
import com.example.aninterface.Graphics;
import com.example.aninterface.Input;
import com.example.aninterface.Sound;
import com.example.aninterface.Image;

public class CustomBackground extends Button {

    private boolean _selected, _adquired;
    private final int _index, _price;
    private final int _priceGap;
    private final Sound _purchaseSound;
    private final Image _coin;
    private final Text _moneyText;

    CustomBackground(boolean selected, int index, int price, Font font, String filename, Engine engine,
                     int positionX, int positionY, int width, int height, int priceGap, Image coin, Text moneyText) {
        super(filename, selected ? Colors.ColorName.GREEN : Colors.ColorName.BLACK, Integer.toString(price), font,
                engine, positionX, positionY, width, height);

        _index = index;
        _price = price;
        _selected = selected;
        _adquired = GameData.Instance().hasBackground(_index);
        _priceGap = priceGap;

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
        if (GameData.Instance().hasBackground(_index)) {
            _clickSound.play();

            GameData.Instance().setBackground(_index);
        }
        else if (GameData.Instance().purchaseBackground(_index, _price)) {
            _purchaseSound.play();

            _moneyText.setText(Integer.toString(GameData.Instance().getMoney()));
            _adquired = true;

            GameData.Instance().setBackground(_index);
        }
    }

    @Override
    public void update(double deltaTime){
        _selected = GameData.Instance().getCurrentBackground() == _index;
    }

    @Override
    public void render(Graphics graphics) {
        final int borderWidth = 5;

        _graphics.drawRoundedRect(_positionX - borderWidth, _positionY - borderWidth,
                _width + 2 * borderWidth, _height + 2 * borderWidth,
                Colors.colorValues.get(_selected ? Colors.ColorName.GREEN : Colors.ColorName.BLACK), _arc, _arc);
        _graphics.drawImage(_image, _positionX, _positionY, _width, _height);

        if(!_adquired){
            final int textCoinSeparation = 10;
            final int coinSize = 20;

            int priceTagWidth = _graphics.getStringWidth(_text, _font) + textCoinSeparation + coinSize;
            _graphics.drawText(_text, _font, _positionX + _width / 2 - priceTagWidth / 2,
                    _positionY + _height + _priceGap / 2 + _graphics.getStringHeight(_text, _font) / 2, 0);
            _graphics.drawImage(_coin, _positionX + _width / 2 + priceTagWidth / 2 - coinSize,
                    _positionY + _height + _priceGap / 2 - coinSize / 2, coinSize, coinSize);
        }
    }
}
