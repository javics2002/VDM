package com.example.logiclib;
import com.example.aninterface.Engine;
import com.example.aninterface.Input;
import com.example.aninterface.Scene;
import com.example.aninterface.Graphics;
import com.example.aninterface.Font;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameScene implements Scene {
    private Text _objectiveText;
    private Text _attemptsText;
    private QuitButton _quitButton;
    private ColorblindButton _colorblindButton;
    private List<CombinationLayout> _combinationLayouts;
    private List<ColorButton> _colorButtons;

    public GameScene(Engine engine, int tryNumber, int combinationLength, int numberOfColors) {
        Graphics graphics = engine.get_graphics();

        int topMargin = 60;
        int lineSpacing = 20;

        String objectiveString = "Averigua el código";
        String attemptsString = "Te quedan " + GameAttributes.Instance().attemptsLeft + " intentos!!!!";

        Font objetiveFont = graphics.newFont("Comfortaa-Regular.ttf", 24f);
        _objectiveText = new Text(objectiveString, objetiveFont, engine,
                graphics.getWidthLogic() / 2, topMargin, 0);

        Font attemptsFont = graphics.newFont("Comfortaa-Regular.ttf", 16f);
        _attemptsText = new Text(attemptsString, attemptsFont, engine,
                graphics.getWidthLogic() / 2, topMargin + lineSpacing, 0);

        _quitButton = new QuitButton("UI/close.png", engine, 50, 40, 50, 50);
        _colorblindButton = new ColorblindButton("UI/eyeClosed.png", engine, graphics.getWidthLogic() - 50, 40, 50, 50,this);

        int initialHeight = 100;
        int padding = 40;
        _combinationLayouts = new ArrayList<>();
        for(int i = 0; i < tryNumber; i++){
            _combinationLayouts.add(new CombinationLayout(engine, i, combinationLength,
                    graphics.getWidthLogic() / 2, initialHeight + i * padding));
        }

        _colorButtons = new ArrayList<>();
        int scale = 32;
        padding = 15;
        for(int i = 0; i < numberOfColors; i++) {
            _colorButtons.add(new ColorButton("color" + (i + 1) + ".png", engine,
                    (int) (graphics.getWidthLogic() / 2 + (i - numberOfColors / 2f) * scale - padding / 2 + i * padding / 2),
                    graphics.getHeightLogic() - 70 , scale, scale));
        }
    
    }


    @Override
    public void update(double deltaTime) {

    }



    @Override
    public void render(Graphics gr) {
        _objectiveText.render();
        _attemptsText.render();

        _quitButton.render();
        _colorblindButton.render();

        for(CombinationLayout combination : _combinationLayouts) {
            combination.render();
        }

        gr.setColor(Color.lightGray.getRGB());
        //gr.fillRect(gr.logicToRealX(0), gr.logicToRealY(gr.getHeightLogic() - 100), gr.scaleToReal(gr.getWidthLogic()), gr.scaleToReal(100));

        for(ColorButton colorButton : _colorButtons) {
            colorButton.render();
        }
    }

    @Override
    public void handleEvents(Input input) {
        if(input.getTouchEvent().size()>0)
        {
            Input.TouchEvent elemento = input.getTouchEvent().get(0);
            this._colorblindButton.handleEvents(input.getTouchEvent().get(0));
            this._quitButton.handleEvents(input.getTouchEvent().get(0));

        }
    }

    public List<CombinationLayout> get_combinationLayouts() {
        return _combinationLayouts;
    }
}
