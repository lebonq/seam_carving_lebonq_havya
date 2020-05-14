import java.awt.image.BufferedImage;
import java.awt.Color;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.io.PrintWriter;
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

    static public int[][] detectEdge(final int[][][] pImage) throws Exception{
        int vImageWidth = pImage.length;
        int vImageHeight = pImage[0].length;
        BufferedImage vImage = new BufferedImage(vImageWidth,vImageHeight,TYPE_INT_RGB);
        Color vBlack = new Color(0,0,0);
        Color vGrey = new Color(127,127,127);
        Color vWhite = new Color(255,255,255);
        int[][] vImageTab = new int[vImageWidth][vImageHeight];
        for(int w = 14; w <15; w++){
        
        for (int i = 0; i < vImageWidth; i++) {
            for (int j = 0; j < vImageHeight; j++) {
                vImageTab[i][j] = vBlack.getRGB();
                vImage.setRGB(i, j, vBlack.getRGB());
            }
        }

        int vNextPixel = w;
        double vThresholdMin = 1;
        double vThresholdMax = 2.5;
        double vCurrent = 0;
        double vTop = 0;
        double vBot = 0;
        double vLeft = 0;
        double vRight = 0;
        double vLeftTop = 0;
        double vRightTop = 0;
        double vRightBot = 0;
        double vLeftBot = 0;

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
                try{
                vRightTop = (pImage[i-vNextPixel][j+vNextPixel][0] + pImage[i-vNextPixel][j+vNextPixel][1] + pImage[i-vNextPixel][j+vNextPixel][2])/3;}
                catch(Exception pE){}
                try{
                vRightBot = (pImage[i+vNextPixel][j+vNextPixel][0] + pImage[i+vNextPixel][j+vNextPixel][1] + pImage[i+vNextPixel][j+vNextPixel][2])/3;}
                catch(Exception pE){}
                try{
                vLeftTop = (pImage[i-vNextPixel][j-vNextPixel][0] + pImage[i-vNextPixel][j-vNextPixel][1] + pImage[i-vNextPixel][j-vNextPixel][2])/3;}
                catch(Exception pE){}
                try{
                vLeftBot = (pImage[i+vNextPixel][j-vNextPixel][0] + pImage[i+vNextPixel][j-vNextPixel][1] + pImage[i+vNextPixel][j-vNextPixel][2])/3;}
                catch(Exception pE){}

                if(Math.abs(vCurrent - vLeft) <= vThresholdMin || Math.abs(vCurrent- vRight) <= vThresholdMin){
                    vImage.setRGB(i, j, vWhite.getRGB());
                    vImageTab[i][j] = vWhite.getRGB();
                }
                else if ((Math.abs(vCurrent - vLeft) <= vThresholdMax && Math.abs(vCurrent - vLeft) > vThresholdMin) 
                || (Math.abs(vCurrent - vRight) <= vThresholdMax && Math.abs(vCurrent - vRight) > vThresholdMin)){
                    vImage.setRGB(i, j, vGrey.getRGB());
                    vImageTab[i][j] = vGrey.getRGB();
                }
                if(Math.abs(vCurrent - vTop) <= vThresholdMin || Math.abs(vCurrent- vBot) <= vThresholdMin){
                    vImage.setRGB(i, j, vWhite.getRGB());
                    vImageTab[i][j] = vWhite.getRGB();
                }
                else if ((Math.abs(vCurrent - vTop) <= vThresholdMax && Math.abs(vCurrent - vTop) > vThresholdMin) 
                || (Math.abs(vCurrent - vBot) <= vThresholdMax && Math.abs(vCurrent - vBot) > vThresholdMin)){
                    vImage.setRGB(i, j, vGrey.getRGB());
                    vImageTab[i][j] = vGrey.getRGB();
                }
                if(Math.abs(vCurrent - vRightTop) <= vThresholdMin || Math.abs(vCurrent- vLeftTop) <= vThresholdMin){
                    vImage.setRGB(i, j, vWhite.getRGB());
                    vImageTab[i][j] = vWhite.getRGB();
                }
                else if ((Math.abs(vCurrent - vRightTop) <= vThresholdMax && Math.abs(vCurrent - vRightTop) > vThresholdMin) 
                || (Math.abs(vCurrent - vLeftTop) <= vThresholdMax && Math.abs(vCurrent - vLeftTop) > vThresholdMin)){
                    vImage.setRGB(i, j, vGrey.getRGB());
                    vImageTab[i][j] = vGrey.getRGB();
                }
                if(Math.abs(vCurrent - vRightBot) <= vThresholdMin || Math.abs(vCurrent- vLeftBot) <= vThresholdMin){
                    vImage.setRGB(i, j, vWhite.getRGB());
                    vImageTab[i][j] = vWhite.getRGB();
                }
                else if ((Math.abs(vCurrent - vRightBot) <= vThresholdMax && Math.abs(vCurrent - vRightBot) > vThresholdMin) 
                || (Math.abs(vCurrent - vLeftBot) <= vThresholdMax && Math.abs(vCurrent - vLeftBot) > vThresholdMin)){
                    vImage.setRGB(i, j, vGrey.getRGB());
                    vImageTab[i][j] = vGrey.getRGB();
                }
            }
        }


        File vOutputfile = new File("edge" + w + ".jpg");
        ImageIO.write(vImage, "jpg", vOutputfile);
        }
        return vImageTab;
    }

    static int[][]calculerMColone(final int[][] pEdge) throws Exception{ 
        int vImageWidth = pEdge.length;
        int vImageHeight = pEdge[0].length;
        Color vBlack = new Color(0,0,0); //vaut 2
        Color vGrey = new Color(127,127,127); //vaut 1
        Color vWhite = new Color(255,255,255); //vaut 0

    }//calculerMColonne()

    static int afficherValeurMin(final int[][] pM){
        int vRes = pM[pM.length-1][0];
        int vIndice = 0;
        for(int i=0; i<pM[0].length-1;i++){    
            if(vRes > pM[pM.length-1][i + 1]){
                vIndice = i+1;
                vRes = pM[pM.length-1][i + 1];
            }//endif
        }//endfor
        System.out.println("La coccinelle a mange " + vRes + " pucerons.");
        return vIndice;
    }//afficherValeurMax()

    static public void createFile(final int[][][] pImageTab) throws Exception{
        File vOutputfile = new File("image.jpg");
        int vWidth = pImageTab.length;
        int vHeight = pImageTab[0].length;
        BufferedImage vImage = new BufferedImage(vWidth,vHeight,TYPE_INT_RGB);
        
        for(int i = 0; i < vWidth;i++){
            for(int j = 0; j < vHeight;j++){
                Color vColor = new Color(pImageTab[i][j][0],pImageTab[i][j][1],pImageTab[i][j][2]);
                vImage.setRGB(i, j, vColor.getRGB());
            }
        }
        ImageIO.write(vImage, "jpg", vOutputfile);
    }

    static public void main(final String[] args){
        try{
            String vNameImage = "src/20.png";
            long vStart = new Date().getTime();

            createFile(createImageTab(vNameImage));

            int[][] vEdge = detectEdge(createImageTab(vNameImage));
            int[][] vColonne = calculerMColone(vEdge);
            afficherValeurMin(vColonne);

            long vEnd = new Date().getTime();
            long vTime = (vEnd - vStart);
            System.out.println("Temps d'excecution : " + vTime + " millisecondes.");
            
            PrintWriter vWriterColonne = new PrintWriter("mcolonne.txt");
            for(int i = 0; i < vColonne.length;i++){
                for(int j = 0; j < vColonne[0].length;j++){
                    vWriterColonne.print("" + vColonne[i][j] + " ");
                }//endfor
                vWriterColonne.println(" ");
            }//endfor
            vWriterColonne.close();
        }//end try
        catch(Exception pE){

            System.err.println(pE);

        }//endcatch
    }//end main()
}//endclass Seamcarving