import java.awt.image.BufferedImage;
import java.awt.Color;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.util.Date;
import java.util.Stack;

import javax.imageio.ImageIO;

public class SeamCarving {

    static public int[][][] createImageTab(final String pImageName) throws Exception {

        BufferedImage vImage = ImageIO.read(new File(pImageName));

        int vImageWidth = vImage.getWidth();
        int vImageHeight = vImage.getHeight();
        int[][][] vImageTab = new int[vImageWidth][vImageHeight][3];

        for (int x = 0; x < vImageWidth; x++) {
            for (int y = 0; y < vImageHeight; y++) {
                vImageTab[x][y][0] = new Color(vImage.getRGB(x, y)).getRed();
                vImageTab[x][y][1] = new Color(vImage.getRGB(x, y)).getGreen();
                vImageTab[x][y][2] = new Color(vImage.getRGB(x, y)).getBlue();
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
        
        for (int y = 0; y < vImageHeight; y++) {
            for (int x = 0; x < vImageWidth; x++) {
                vImageTab[x][y] = vBlack.getRGB();
                vImage.setRGB(x, y, vBlack.getRGB());
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

        for (int x = 0; x < vImageWidth; x++) {
            for (int y = 0; y < vImageHeight; y++) {
                vCurrent = (pImage[x][y][0] + pImage[x][y][1] + pImage[x][y][2]) / 3;
                try {
                    vTop = (pImage[x - vNextPixel][y][0] + pImage[x - vNextPixel][y][1] + pImage[x - vNextPixel][y][2])
                            / 3;
                } catch (Exception pE) {
                }
                try {
                    vBot = (pImage[x + vNextPixel][y][0] + pImage[x + vNextPixel][y][1] + pImage[x + vNextPixel][y][2])
                            / 3;
                } catch (Exception pE) {
                }
                try {
                    vLeft = (pImage[x][y - vNextPixel][0] + pImage[x][y - vNextPixel][1] + pImage[x][y - vNextPixel][2])
                            / 3;
                } catch (Exception pE) {
                }
                try {
                    vRight = (pImage[x][y + vNextPixel][0] + pImage[x][y + vNextPixel][1]
                            + pImage[x][y + vNextPixel][2]) / 3;
                } catch (Exception pE) {
                }
                try {
                    vRightTop = (pImage[x - vNextPixel][y + vNextPixel][0] + pImage[x - vNextPixel][y + vNextPixel][1]
                            + pImage[x - vNextPixel][y + vNextPixel][2]) / 3;
                } catch (Exception pE) {
                }
                try {
                    vRightBot = (pImage[x + vNextPixel][y + vNextPixel][0] + pImage[x + vNextPixel][y + vNextPixel][1]
                            + pImage[x + vNextPixel][y + vNextPixel][2]) / 3;
                } catch (Exception pE) {
                }
                try {
                    vLeftTop = (pImage[x - vNextPixel][y - vNextPixel][0] + pImage[x - vNextPixel][y - vNextPixel][1]
                            + pImage[x - vNextPixel][y - vNextPixel][2]) / 3;
                } catch (Exception pE) {
                }
                try {
                    vLeftBot = (pImage[x + vNextPixel][y - vNextPixel][0] + pImage[x + vNextPixel][y - vNextPixel][1]
                            + pImage[x + vNextPixel][y - vNextPixel][2]) / 3;
                } catch (Exception pE) {
                }

                if (Math.abs(vCurrent - vLeft) <= vThresholdMin || Math.abs(vCurrent - vRight) <= vThresholdMin) {
                    vImage.setRGB(x, y, vWhite.getRGB());
                    vImageTab[x][y] = vWhite.getRGB();
                } else if ((Math.abs(vCurrent - vLeft) <= vThresholdMax && Math.abs(vCurrent - vLeft) > vThresholdMin)
                        || (Math.abs(vCurrent - vRight) <= vThresholdMax
                                && Math.abs(vCurrent - vRight) > vThresholdMin)) {
                    vImage.setRGB(x, y, vGrey.getRGB());
                    vImageTab[x][y] = vGrey.getRGB();
                }
                if (Math.abs(vCurrent - vTop) <= vThresholdMin || Math.abs(vCurrent - vBot) <= vThresholdMin) {
                    vImage.setRGB(x, y, vWhite.getRGB());
                    vImageTab[x][y] = vWhite.getRGB();
                } else if ((Math.abs(vCurrent - vTop) <= vThresholdMax && Math.abs(vCurrent - vTop) > vThresholdMin)
                        || (Math.abs(vCurrent - vBot) <= vThresholdMax && Math.abs(vCurrent - vBot) > vThresholdMin)) {
                    vImage.setRGB(x, y, vGrey.getRGB());
                    vImageTab[x][y] = vGrey.getRGB();
                }
                if (Math.abs(vCurrent - vRightTop) <= vThresholdMin || Math.abs(vCurrent - vLeftTop) <= vThresholdMin) {
                    vImage.setRGB(x, y, vWhite.getRGB());
                    vImageTab[x][y] = vWhite.getRGB();
                } else if ((Math.abs(vCurrent - vRightTop) <= vThresholdMax
                        && Math.abs(vCurrent - vRightTop) > vThresholdMin)
                        || (Math.abs(vCurrent - vLeftTop) <= vThresholdMax
                                && Math.abs(vCurrent - vLeftTop) > vThresholdMin)) {
                    vImage.setRGB(x, y, vGrey.getRGB());
                    vImageTab[x][y] = vGrey.getRGB();
                }
                if (Math.abs(vCurrent - vRightBot) <= vThresholdMin || Math.abs(vCurrent - vLeftBot) <= vThresholdMin) {
                    vImage.setRGB(x, y, vWhite.getRGB());
                    vImageTab[x][y] = vWhite.getRGB();
                } else if ((Math.abs(vCurrent - vRightBot) <= vThresholdMax
                        && Math.abs(vCurrent - vRightBot) > vThresholdMin)
                        || (Math.abs(vCurrent - vLeftBot) <= vThresholdMax
                                && Math.abs(vCurrent - vLeftBot) > vThresholdMin)) {
                    vImage.setRGB(x, y, vGrey.getRGB());
                    vImageTab[x][y] = vGrey.getRGB();
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
        Color vBlack = new Color(0,0,0); //vaut 3
        Color vGrey = new Color(127,127,127); //vaut 2
        Color vWhite = new Color(255,255,255); //vaut 1
        int[][] vEdgeValue = new int[vImageWidth][vImageHeight]; // pour stocker les valeurs en int de chaque couleur
        int[][] vM = new int[vImageWidth][vImageHeight];

        for(int y = 0; y < vImageHeight; y++) { // On ajoute les valeurs pour avoir les couts de chaque pixel
            for (int x = 0; x < vImageWidth; x++) {
                if (new Color(pEdge[x][y]).getRGB() == vWhite.getRGB())
                    vEdgeValue[x][y] = 1;
                if (new Color(pEdge[x][y]).getRGB() == vGrey.getRGB())
                    vEdgeValue[x][y] = 2;
                if (new Color(pEdge[x][y]).getRGB() == vBlack.getRGB())
                    vEdgeValue[x][y] = 3;
            }//endfor
        }//endfor

        for(int y = 0; y < 1; y++) { // On ajoute les valeurs dans la premiere ligne
            for (int x = 0; x < vImageWidth;x++) {
                vM[x][y] = vEdgeValue[x][y];
            }//endfor
        }//endfor

        for(int y = 0; y < vImageHeight - 1; y++) {
            for (int x = 0; x < vImageWidth; x++) {
                int vBas = vM[x][y] + vEdgeValue[x][y + 1];

                if (vBas < vM[x][y + 1] || vM[x][y + 1] == 0) { //Si vM == 0 on considere que cette case n'a jamais ete touche
                    vM[x][y + 1] = vBas;
                }
                try{//on met un try pour eviter la sorti de tableau sur les bords
                    int vDiagonaleDroite = vM[x][y] + vEdgeValue[x+1][y+1];
                    if (vDiagonaleDroite < vM[x+1][y + 1] || vM[x+1][y + 1] == 0) {
                        vM[x+1][y + 1] = vBas;
                    }
                }catch(Exception pE){}
                try{
                    int vDiagonaleGauche = vM[x][y] + vEdgeValue[x-1][y+1];
                    if (vDiagonaleGauche < vM[x-1][y + 1] || vM[x-1][y + 1] == 0) {
                        vM[x-1][y + 1] = vBas;
                    }
                }catch(Exception pE){}
            }//endfor
        }//endfor
        System.gc();//force le garbage collector a fonctionner
        return vM;
    }//calculerMColonne()

    /**
     * Permet de calculer la valeur minimal ainsi que ses coordonnées
     * @param pM Matrice de coup minimun
     * @param pY Valeur de depart de Y
     * @param pX Valeur de depart de X
     * @return Un tableau de int, en 0 la valeur minimum, et 1 sa postion en x et en 2 sa postion en y
     */
    static public int[] minValue(final int[][] pM, final int pY,final int pX){
        int vXVal = pX;
        int vYVal = pY;
        int vValMin = pM[pX][pY];

        for(int x = 0; x < pM.length;x++){
            if(vValMin > pM[x][vYVal]) {
                vValMin = pM[x][vYVal];
                vXVal = x;
            } // endif
        } // endfor
        return new int[] { vValMin, vXVal, vYVal };
    }//minValue()

    /**
     * retourne un stack avec en haut la derniere composante
     * @param pM Matrice de coup minimun
     * @param pValMin tableau retourné par minValue()
     * @return Stack<int[]> avec en 0 la coordonée x et en 1 y
     */
    static public Stack<int[]> ccm(final int[][] pM,final int[] pValMin){
        int vXDepart = pValMin[1];
        int vYDepart = pValMin[2];
        int Infini = Integer.MAX_VALUE;
        if(vYDepart == 0 ){ //fin de notre reccurence
            //System.out.print("("+vXDepart+","+vYDepart+")");
            Stack<int[]> vReturn = new Stack<int[]>();
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//end if
        int vDiagonaleDroite = Infini;
        int vDiagonaleGauche = Infini;

        try{
            vDiagonaleDroite = pM[vXDepart+1][vYDepart-1];
        }catch(Exception pE){}
        try{
            vDiagonaleGauche = pM[vXDepart-1][vYDepart-1];
        }catch(Exception pE){}

        if(pM[vXDepart][vYDepart-1] < Math.min(vDiagonaleGauche, vDiagonaleDroite)) {
            Stack<int[]> vReturn = ccm(pM, new int[]{pValMin[0],vXDepart,vYDepart-1});
            //System.out.print("("+vXDepart+","+vYDepart+")");
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
            
        }//endif
        else if(vDiagonaleGauche < vDiagonaleDroite){
            Stack<int[]> vReturn = ccm(pM, new int[]{pValMin[0],vXDepart-1,vYDepart-1});
            //System.out.print("("+vXDepart+","+vYDepart+")");
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//ende lse if
        else{
            Stack<int[]> vReturn = ccm(pM, new int[]{pValMin[0],vXDepart+1,vYDepart-1});
            //System.out.print("("+vXDepart+","+vYDepart+")");
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//end else
    }//ccm()
    
    static public int conversionPourcentageLigne (final int[][][] pImage, final int pPourcentage){
        return (pImage.length*pPourcentage)/100;
    }//conversionPourcentageLigne
    
    static public int conversionPourcentageColonne (final int[][][] pImage, final int pPourcentage){
        return (pImage[0].length*pPourcentage)/100;
    }//conversionPourcentageColonne

    /**
     * Permet de retirer le nombre de colonne necessaire
     * @param pColonne nombre de colonnes de pixel a retirer
     * @param pEdge Image avec les bords affichés 
     * @param pImageTab Image source
     * @return Tableau de l'image avec p colonne en moins mais matrice non redimensionné
     * @throws Exception
     */
    static public int[][][] retirerColonne(final int pColonne, final int[][] pEdge, final int [][][] pImageTab) throws Exception{
        int[][] vEdgeModifie = pEdge;
        int[][][] vImageTabModifie = pImageTab;
        int[][] vMColonne = calculerMColone(pEdge);
        int[] vColonneValue = minValue(vMColonne,vMColonne[0].length-1,0);
        Stack<int[]> vStack = ccm(vMColonne, vColonneValue);
        int vHeight = pImageTab[0].length;
        if(pColonne == -1){
            return pImageTab;
        }//endif

        for(int y = vHeight-1; y > -1;  y--){
            int[] vPixel = vStack.pop();
            vImageTabModifie = decalerLigne3D(vPixel,vImageTabModifie);
            vEdgeModifie = decalerLigne2D(vPixel,vEdgeModifie);
        }//endfor
        System.gc();//force le garbage collector a fonctionner
        return retirerColonne(pColonne-1, vEdgeModifie, vImageTabModifie);
    }//retirerColonne()

    static public int[][][] decalerLigne3D(final int[] pPixel, final int[][][] pImageTab){
        int[][][] vImageTabModifie = pImageTab;
        int vWidth = pImageTab.length;
        for(int x = pPixel[0];x < vWidth-1;x++){
            vImageTabModifie[x][pPixel[1]][0] = pImageTab[x+1][pPixel[1]][0];
            vImageTabModifie[x][pPixel[1]][1] = pImageTab[x+1][pPixel[1]][1];
            vImageTabModifie[x][pPixel[1]][2] = pImageTab[x+1][pPixel[1]][2];
        }
        vImageTabModifie[vWidth-1][pPixel[1]][0] = 0;
        vImageTabModifie[vWidth-1][pPixel[1]][1] = 0;
        vImageTabModifie[vWidth-1][pPixel[1]][2] = 0;
        return vImageTabModifie;
        
    }//decalerLigne3D()

    static public int[][] decalerLigne2D(final int[] pPixel, final int[][] pEdge){
        int[][] vEdgeModifie = pEdge;
        int vWidth = pEdge.length;
        Color vBlack = new Color(0,0,0);

        for (int x = pPixel[0]; x < vWidth - 1; x++) {
            vEdgeModifie[x][pPixel[1]] = pEdge[x + 1][pPixel[1]];
            vEdgeModifie[x][pPixel[1]] = pEdge[x + 1][pPixel[1]];
            vEdgeModifie[x][pPixel[1]] = pEdge[x + 1][pPixel[1]];
        }
        vEdgeModifie[vWidth - 1][pPixel[1]] =  vBlack.getRGB();
        return vEdgeModifie;
        
    }//decalerLigne3D()
    
    static public void createFile(final int[][][] pImageTab,final int pColonne) throws Exception{
        File vOutputfile = new File("image_redimensionnee.jpg");
        int vWidth = pImageTab.length-pColonne;
        int vHeight = pImageTab[0].length;
        BufferedImage vImage = new BufferedImage(vWidth,vHeight,TYPE_INT_RGB);
        
        for(int x = 0; x < vWidth;x++){
            for(int y = 0; y < vHeight; y++) {
                Color vColor = new Color(pImageTab[x][y][0], pImageTab[x][y][1], pImageTab[x][y][2]);
                vImage.setRGB(x, y, vColor.getRGB());
            }
        }
        ImageIO.write(vImage, "jpg", vOutputfile);
    }//createFile()

    static public void main(final String[] args){
        try{
            String vNameImage = args[0];
            long vStart = new Date().getTime();
            System.out.print("Processing : ");
            

            int[][][] vImageTab = createImageTab(vNameImage);
            int[][] vEdge = detectEdge(vImageTab);

            int vPourcentageColonne = Integer.parseInt(args[1]);
            int vColonneToDelete = conversionPourcentageColonne(vImageTab, vPourcentageColonne);
            int[][][] vImageRedimensionnee = retirerColonne(vColonneToDelete, vEdge, vImageTab);
            createFile(vImageRedimensionnee,vColonneToDelete);

            long vEnd = new Date().getTime();
            long vTime = (vEnd - vStart);
            System.out.println("Fait en " + vTime/1000 + " secondes.");
        }//end try
        catch(Exception pE){

            System.err.println(pE);

        }//endcatch
    }//end main()
}//endclass Seamcarving