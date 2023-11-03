package com.example.aninterface;

public interface Graphics {
    Font newFont(String fileName, float size);

    Image loadImage(String path);

    void drawImage(Image image, int x, int y, int w, int h);

    void setColor(int color);

    void clear(int color);

    void drawRect(int x, int y, int width, int height, int color);
    void drawRoundedRect(int x, int y, int width, int height, int color, int arcWidth, int arcHeight);

    void drawText(String text, Font font, int x, int y, int color);
    void drawCircle(int logicX, int logicY, int radius, int color);

    int logicToRealX(int x);

    int logicToRealY(int y);

    int scaleToReal(int realScale);

    int getWidth();

    int getLogicWidth();

    int getHeight();

    int getLogicHeight();

    int getStringWidth(String text, Font font);

    int getStringHeight(String text, Font font);

    void setNewResolution(int w, int h);

    void prepareFrame();
}


