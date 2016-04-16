package com.catway;

import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String file_in_path = "D:\\Image.jpg";
        String file_out_path = "D:\\ImageContour.jpg";
        BufferedImage img = null;
        BufferedImage gradient;
        try {
            img = ImageIO.read(new File(file_in_path));
        } catch (IOException e) {
            System.out.println("Error while reading file");
        }
        assert img != null;
        int height = img.getHeight();
        int width = img.getWidth();
        gradient = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x< width-1; x++) {
                int invG = (int) getColoredG(img,x,y)/12;
                int G = 255-invG;
                try{
                    gradient.setRGB(x, y, new Color(G,G,G).getRGB());}
                catch(Exception e){
                    System.out.println("Write colour exception");
                }
            }
        }

        try {
            boolean a = ImageIO.write(gradient, "png", new File(file_out_path));
            System.out.println(a);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing");
        }

    }

    private static double getColoredG(BufferedImage img, int x, int y)
    {
        Color b00 = new Color(img.getRGB(x-1, y-1));
        Color b01 = new Color(img.getRGB(x, y-1));
        Color b02 = new Color(img.getRGB(x+1, y-1));
        Color b10 = new Color(img.getRGB(x-1, y));

        Color b12 = new Color(img.getRGB(x+1, y));
        Color b20 = new Color(img.getRGB(x-1, y+1));
        Color b21 = new Color(img.getRGB(x, y+1));
        Color b22 = new Color(img.getRGB(x+1, y+1));


        float RedGx = GetG(b02.getRed(), b12.getRed(), b22.getRed(), b00.getRed(), b10.getRed(), b20.getRed());
        float GreenGx = GetG(b02.getGreen(), b12.getGreen(), b22.getGreen(), b00.getGreen(), b10.getGreen(), b20.getGreen());
        float BlueGx = GetG(b02.getBlue(), b12.getBlue(), b22.getBlue(), b00.getBlue(), b10.getBlue(), b20.getBlue());

        float RedGy = GetG(b20.getRed(), b21.getRed(), b22.getRed(), b00.getRed(), b01.getRed(), b02.getRed());
        float GreenGy = GetG(b20.getGreen(), b21.getGreen(), b22.getGreen(), b00.getGreen(), b01.getGreen(), b02.getGreen());
        float BlueGy = GetG(b20.getBlue(), b21.getBlue(), b22.getBlue(), b00.getBlue(), b01.getBlue(), b02.getBlue());

        float RedG = (int) Math.sqrt(RedGx*RedGx + RedGy*RedGy);
        float GreenG = (int) Math.sqrt(GreenGx*GreenGx + GreenGy*GreenGy);
        float BlueG = (int) Math.sqrt(BlueGx*BlueGx + BlueGy*BlueGy);

        return (float) Math.sqrt(RedG*RedG + GreenG*GreenG + BlueG*BlueG);
    }

    private static int GetG(int a, int b, int c, int d, int e, int f)
    {
        return (a + 2*b + c) - (d + 2*e + f);
    }

}
