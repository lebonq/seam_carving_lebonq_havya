import java.awt.image.BufferedImage;
import java.awt.Color;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;


public class SeamCarving {

    static public int[][][] createImageTab(final String pImageName) throws Exception {

        BufferedImage vImage = ImageIO.read(new File(pImageName));

        int vImageWidth = vImage.getWidth();
        int vImageHeight = vImage.getHeight();
        int[][][] vImageTab = new int[vImageWidth][vImageHeight][3];

        for (int i = 0; i < vImageWidth; i++) {
            for (int j = 0; j < vImageHeight; j++) {
                vImageTab[i][j][0] = new Color(vImage.getRGB(i, j)).getRed();
                vImageTab[i][j][1] = new Color(vImage.getRGB(i, j)).getGreen();
                vImageTab[i][j][2] = new Color(vImage.getRGB(i, j)).getBlue();
            }
        }
        return vImageTab;
    }

    static public void detectEdge(final int[][][] pImage) throws Exception{
        int vImageWidth = pImage.length;
        int vImageHeight = pImage[0].length;
        BufferedImage vImage = new BufferedImage(vImageWidth,vImageHeight,TYPE_INT_RGB);
        Color vBlack = new Color(0,0,0);
        Color vGrey = new Color(127,127,127);
        Color vWhite = new Color(255,255,255);


        for (int i = 0; i < vImageWidth; i++) {
            for (int j = 0; j < vImageHeight; j++) {
                vImage.setRGB(i, j, vBlack.getRGB());
            }
        }

        int vNextPixel = 1;
        int vThresholdMin = 2;
        int vThresholdMax = 3;
        double vCurrent = 0;
        double vTop = 0;
        double vBot = 0;
        double vLeft = 0;
        double vRight = 0;

        for (int i = 0; i < vImageWidth; i++) {
            for (int j = 0; j < vImageHeight; j++) {
                vCurrent = (pImage[i][j][0] + pImage[i][j][1] + pImage[i][j][2])/3;
                try{
                vTop = (pImage[i-vNextPixel][j][0] + pImage[i-vNextPixel][j][1] + pImage[i-vNextPixel][j][2])/3;}
                catch(Exception pE){}
                try{
                vBot = (pImage[i+vNextPixel][j][0] + pImage[i+vNextPixel][j][1] + pImage[i+vNextPixel][j][2])/3;}
                catch(Exception pE){}
                try{
                vLeft = (pImage[i][j-vNextPixel][0] + pImage[i][j-vNextPixel][1] + pImage[i][j-vNextPixel][2])/3;}
                catch(Exception pE){}
                try{
                vRight = (pImage[i][j+vNextPixel][0] + pImage[i][j+vNextPixel][1] + pImage[i][j+vNextPixel][2])/3;}
                catch(Exception pE){}

                if(Math.abs(vCurrent - vLeft) <= vThresholdMin || Math.abs(vCurrent- vRight) <= vThresholdMin){
                    vImage.setRGB(i, j, vWhite.getRGB());
                }
                else if ((Math.abs(vCurrent - vLeft) <= vThresholdMax && Math.abs(vCurrent - vLeft) > vThresholdMin) 
                || (Math.abs(vCurrent - vRight) <= vThresholdMax && Math.abs(vCurrent - vRight) > vThresholdMin)){
                    vImage.setRGB(i, j, vGrey.getRGB());
                }
                if(Math.abs(vCurrent - vTop) <= vThresholdMin || Math.abs(vCurrent- vBot) <= vThresholdMin){
                    vImage.setRGB(i, j, vWhite.getRGB());
                }
                else if ((Math.abs(vCurrent - vTop) <= vThresholdMax && Math.abs(vCurrent - vTop) > vThresholdMin) 
                || (Math.abs(vCurrent - vBot) <= vThresholdMax && Math.abs(vCurrent - vBot) > vThresholdMin)){
                    vImage.setRGB(i, j, vGrey.getRGB());
                }
            }
        }


        File vOutputfile = new File("edge.jpg");
        ImageIO.write(vImage, "jpg", vOutputfile);
    }

    static public void createFile(final int[][][] pImageTab) throws Exception{
        File vOutputfile = new File("image.jpg");
        BufferedImage vImage = new BufferedImage(256,256,TYPE_INT_RGB);
        int vFactorWidth = (pImageTab.length/256)+1;
        int vFactorHeight = (pImageTab[0].length/256)+1;
        int vWidth = pImageTab.length;
        int vHeight = pImageTab[0].length;

        for(int i = 0; i < vWidth;i++){
            for(int j = 0; j < vHeight;j++){
                Color vColor = new Color(pImageTab[i][j][0],pImageTab[i][j][1],pImageTab[i][j][2]);
                vImage.setRGB(i/vFactorWidth, j/vFactorHeight, vColor.getRGB());
            }
        }

        ImageIO.write(vImage, "jpg", vOutputfile);
    }

    static public void main(final String[] args){
        try{
            long vStart = new Date().getTime();
            createFile(createImageTab("src/8k.jpg"));
            detectEdge(createImageTab("src/8k.jpg"));
            long vEnd = new Date().getTime();
            long vTime = (vEnd - vStart);
            System.out.println("Temps d'excecution : " + vTime + " millisecondes.");
        }
        catch(Exception pE){
            System.err.println(pE);
        }
    }
}