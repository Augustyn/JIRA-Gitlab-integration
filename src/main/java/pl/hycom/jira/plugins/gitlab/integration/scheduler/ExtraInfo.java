package pl.hycom.jira.plugins.gitlab.integration.scheduler;

import java.io.Serializable;
import java.util.Random;

/**
 * Extra stuff that be added to the parameter map.
 *
 * @since v1.0
 */
public class ExtraInfo implements Serializable
{
    private static final long serialVersionUID = 8798328793941741984L;

    private static final Random RANDOM = new Random();
    private static final FavoriteColor[] COLORS = FavoriteColor.values();

    public static ExtraInfo random()
    {
        return new ExtraInfo(COLORS[RANDOM.nextInt(COLORS.length)], RANDOM.nextInt());
    }



    private final FavoriteColor color;
    private final int number;

    public ExtraInfo(FavoriteColor color, int number)
    {
        this.color = color;
        this.number = number;
    }



    public FavoriteColor getFavoriteColor()
    {
        return color;
    }

    public int getFavoriteNumber()
    {
        return number;
    }

    @Override
    public String toString()
    {
        return "ExtraInfo[color=" + color + ", number=" + number + ']';
    }



    // Note: enums are always implicitly Serializable
    public static enum FavoriteColor
    {
        PINK,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        CYAN,
        BLUE,
        INDIGO,
        VIOLET,
        MAGENTA,
        BLACK,
        GREY,
        WHITE,
        SILVER,
        COPPER,
        GOLD,
        PLATINUM,
        CHARTREUSE,
        MAHOGANY,
        AQUA,
        APRICOT,
        BEIGE,
        BURGUNDY,
        CHICKADEE,
        CHOCOLATE,
        CORNFLOWER,
        DAFFODIL,
        DANDELION,
        EGGNOG,
        EGGPLANT,
        FIG,
        FUSCHIA,
        IVORY,
        KHAKI,
        LEMON,
        MADARIN,
        MAROON,
        MELON,
        MINT,
        MUSTARD,
        OLIVE,
        PEACH,
        PLUM,
        SALMON,
        THISTLE,
        TOMATO,
        WATERMELON,
        WILDFLOWER,
        ZINNIA
    }
}
