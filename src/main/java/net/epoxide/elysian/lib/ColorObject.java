package net.epoxide.elysian.lib;

import net.minecraft.nbt.NBTTagCompound;

public class ColorObject {
    
    public float red = 1.0f;
    public float green = 1.0f;
    public float blue = 1.0f;
    public float alpha = 1.0f;
    
    /**
     * Constructs a new ColorObject with completely random values for RGBA.
     * 
     * @param doAlpha : If true, the alpha value will also be randomized, by default it is 1.
     */
    public ColorObject(boolean doAlpha) {
    
        this(getRandomColor(), getRandomColor(), getRandomColor(), (doAlpha) ? getRandomColor() : 1.0f);
    }
    
    /**
     * Constructs a new ColorObject from a NBTTagCompound.
     * 
     * @param tag: An nbt tag which should have color data written to it.
     */
    public ColorObject(NBTTagCompound tag) {
    
        this(tag.getFloat("red"), tag.getFloat("green"), tag.getFloat("blue"), (tag.hasKey("alpha")) ? tag.getFloat("alpha") : 1.0f);
    }
    
    /**
     * Converts a decimal color integer like the one produced in getIntFromColor back into a
     * ColorObject.
     * 
     * @param rgb : The decimal value which represents all of the color data.
     */
    public ColorObject(int rgb) {
    
        this((float) (rgb >> 16 & 255) / 255.0F, (float) (rgb >> 8 & 255) / 255.0F, (float) (rgb & 255) / 255.0F);
    }
    
    /**
     * Converts a String based hexadecimal representation of a color into a ColorObject.
     * 
     * @param hexColor: The hexadecimal color as a string. It is expected that a # is included.
     */
    public ColorObject(String hexColor) {
    
        this(Integer.valueOf(hexColor.substring(1, 3), 16), Integer.valueOf(hexColor.substring(3, 5), 16), Integer.valueOf(hexColor.substring(5, 7), 16));
    }
    
    /**
     * Creates a new ColorObject using integers which will be converted back into floats. The
     * alpha value is automatically set to 1. (0-255)
     * 
     * @param red : The amount of red that makes up this color.
     * @param green : The amount of green that makes up this color.
     * @param blue : The amount of blue that makes up this color.
     * */
    public ColorObject(int red, int green, int blue) {
    
        this((float) red / 255, (float) green / 255, (float) blue / 255, 1.0f);
    }
    
    /**
     * Creates a new ColorObject using integers which will be converted back into floats.
     * (0-255)
     * 
     * @param red : The amount of red that makes up this color.
     * @param green : The amount of green that makes up this color.
     * @param blue : The amount of blue that makes up this color.
     * @param alpha : The transparency for this color. 0 is fully transparent, while 100 is
     *            completely solid.
     * */
    public ColorObject(int red, int green, int blue, int alpha) {
    
        this((float) red / 255, (float) green / 255, (float) blue / 255, (float) alpha / 100);
    }
    
    /**
     * Creates a new ColorObject using the standard RGB color values. Alpha is set to 1.
     * 
     * @param red : The amount of red that makes up this color.
     * @param green : The amount of green that makes up this color.
     * @param blue : The amount of blue that makes up this color.
     */
    public ColorObject(float red, float green, float blue) {
    
        this(red, green, blue, 1.0f);
    }
    
    /**
     * Creates a new ColorObject using the standard RGBA color values.
     * 
     * @param red : The amount of that makes up this color.
     * @param green : The amount of that makes up this color.
     * @param blue : The amount of that makes up this color.
     * @param alpha : The transparency of this color object. 0 is completely see through, 1 is
     *            completely solid.
     */
    public ColorObject(float red, float green, float blue, float alpha) {
    
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    /**
     * Creates a NBTTagCompound from a ColorObject.
     * 
     * @return NBTTagCompound: A NBTTagCompound containing the RGBA of the ColorObject.
     */
    public NBTTagCompound getTagFromColor () {
    
        return this.writeToTag(new NBTTagCompound());
    }
    
    /**
     * Provides an Integer based decimal representation of this ColorObject. This is used by
     * Mojang for several things such as Biome colors and ItemStack colors. This method returns
     * the same value as getHexIntFromColor.
     * 
     * @return int: An Integer which represents the decimal value of this ColorObject.
     */
    public int getDecimalFromColor () {
    
        int rgb = (int) (this.red * 255);
        rgb = (rgb << 8) + (int) (this.green * 255);
        rgb = (rgb << 8) + (int) (this.blue * 255);
        return rgb;
    }
    
    /**
     * Provides a String based hexadecimal representation of this ColorObject. Alpha values are
     * ignored.
     * 
     * @return String: A String based hexadecimal representation of this ColorObject. Example:
     *         #99FF99
     */
    public String getHexStringFromColor () {
    
        return String.format("#%02X%02X%02X", (int) (this.red * 255), (int) (this.green * 255), (int) (this.blue * 255));
    }
    
    /**
     * Provides an integer based hexadecimal representation of this ColorObject. The output of
     * this method is the same as getDecimalFromColor. This is used by Mojang for several
     * things such as Biome colors and ItemStack colors.
     * 
     * @return int: An integer based hexadecimal representation of the color represented by
     *         this ColorObject. Example: 10092441
     */
    public int getHexIntFromColor () {
    
        return Integer.parseInt(getHexStringFromColor().substring(1), 16);
    }
    
    /**
     * Creates a random float value which represents a color.
     * 
     * @return float: A random float between 0 and 1.
     */
    public static float getRandomColor () {
    
        return Constants.RANDOM.nextFloat();
    }
    
    /**
     * A simple method used to check if a ColorObject is generic. A generic ColorObject is
     * considered a ColorObject that represents White.
     * 
     * @return boolean: If the ColorObject represents pure white, this method will return true.
     */
    public boolean isGeneric () {
    
        return isGeneric(1.0f);
    }
    
    /**
     * A method which can be used to check if a ColorObject is generic. A Generic ColorObject,
     * as defined in this method, is a ColorObject which has the same value for R, G and B.
     * 
     * @param color : The value of the color being checked for the RGB.
     * @return boolean: If true, the ColorObject will be considered generic.
     */
    public boolean isGeneric (float color) {
    
        return (this.red >= color && this.blue >= color && this.green >= color);
    }
    
    /**
     * Creates a copy of the provided ColorObject, useful when you don't want to mess up
     * existing instances.
     * 
     * @return ColorObject: A clone of the provided ColorObject;
     */
    public ColorObject clone () {
    
        ColorObject clone = new ColorObject(false);
        clone.red = this.red;
        clone.green = this.green;
        clone.blue = this.blue;
        clone.alpha = this.alpha;
        
        return clone;
    }
    
    /**
     * Writes the ColorObject to an NBTTagCompound which can be used for storing and syncing
     * colors.
     * 
     * @param tag: The NBTTagCompound to write the data to.
     * @return NBTTagCompound: An NBTTagCompound which has the color data of this ColorObject
     *         written to it.
     */
    public NBTTagCompound writeToTag (NBTTagCompound tag) {
    
        if (tag == null)
            tag = new NBTTagCompound();
        
        tag.setFloat("red", this.red);
        tag.setFloat("green", this.green);
        tag.setFloat("blue", this.blue);
        tag.setFloat("alpha", this.alpha);
        return tag;
    }
    
    /**
     * Merges two ColorObject together. The first object represents the primary colors, while
     * the second represents the alpha.
     * 
     * @param primary : The primary colors, only the RGB of this will be kept.
     * @param alpha : The alpha layer, only the alpha value of this color will be kept.
     * @return ColorObject: A new ColorObject which has the RGB of the primary color, and the A
     *         of the alpha color.
     */
    public static ColorObject mergeColors (ColorObject primary, ColorObject alpha) {
    
        ColorObject colorObj = primary;
        colorObj.alpha = alpha.alpha;
        return colorObj.clone();
    }
    
    public static enum VanillaColor {
        
        BLACK("Black", new ColorObject(25, 25, 25)), RED("Red", new ColorObject(153, 51, 51)), GREEN("Green", new ColorObject(102, 127, 51)), BROWN("Brown", new ColorObject(102, 76, 51)), BLUE("Blue", new ColorObject(51, 76, 178)), PURPLE("Purple", new ColorObject(127, 63, 178)), CYAN("Cyan", new ColorObject(76, 127, 153)), LIGHT_GRAY("LightGray", new ColorObject(153, 153, 153)), GRAY("Gray", new ColorObject(76, 76, 76)), PINK("Pink", new ColorObject(242, 127, 165)), LIME("Lime", new ColorObject(127, 204, 25)), YELLOW("Yellow", new ColorObject(229, 229, 51)), LIGHT_BLUE("LightBlue", new ColorObject(102, 153, 216)), MAGENTAG("Magenta", new ColorObject(178, 76, 216)), ORANGE("Orange", new ColorObject(216, 127, 5)), WHITE("White", new ColorObject(255, 255, 255));
        
        public String colorName;
        public ColorObject colorObj;
        
        /**
         * A simple enumeration of all the vanilla Minecraft colors. This contains a string
         * representation of the color, which is mostly used for Ore Dictionary stuff, and a
         * ColorObject which holds all the color data for the color.
         * 
         * @param name: A name which represents the color within Minecraft. Example: Gray
         * @param color: A ColorObject which holds the RGB value for this color.
         */
        VanillaColor(String name, ColorObject color) {
        
            colorName = name;
            colorObj = color;
        }
        
        /**
         * Provides the OreDictionary name for the dye item associated with this color.
         * 
         * @return String: A string which represents the associated dye within the
         *         OreDictionary.
         */
        public String getDyeName () {
        
            return "dye" + colorName;
        }
        
        /**
         * Provides the OreDictionary name for the Dyed Glass Pane associated with this color.
         * 
         * @return String: A string which represents the associated Dyed Glass Pane within the
         *         OreDictionary.
         */
        public String getGlassPaneName () {
        
            return "paneGlass" + colorName;
        }
        
        /**
         * Provides the OreDictionary name for the Dyed Glass Block associated with this color.
         * 
         * @return String: A String which represents the associated Dyed Glass Block within the
         *         OreDictionary.
         */
        public String getGlassBlockName () {
        
            return "blockGlass" + colorName;
        }
    }
}