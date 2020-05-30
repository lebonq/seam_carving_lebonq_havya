import java.awt.image.BufferedImage;
import java.awt.Color;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.util.Date;
import java.util.Stack;
import java.util.Random;

import javax.imageio.ImageIO;

public class SeamCarving {
    static public long vTimeColonneStart = new Date().getTime();
    static public long vTimeLigneStart = new Date().getTime();
    static public int[][] vImageSeams;

    /**
     * Permet de copier l'image pixel par pixel dans un tableau 2D
     * @param pImageName String chemin d'acces de l'image
     * @return int[]( )
     * @throws Exception
     */
    static public int[][] createImageTab(final String pImageName) throws Exception {

        BufferedImage vImage = ImageIO.read(new File(pImageName));

        int vImageWidth = vImage.getWidth();
        int vImageHeight = vImage.getHeight();
        int[][] vImageTab = new int[vImageWidth][vImageHeight];

        for (int x = 0; x < vImageWidth; x++) {
            for (int y = 0; y < vImageHeight; y++) {
                vImageTab[x][y] = vImage.getRGB(x, y);
            }
        }
        return vImageTab;
    }

    /**
     * Permet de generer notre matrice avec les energies
     * @param pImage int[][] Un tableau 2D avec la valeur RGB  de chaque pixel 
     * @return pEdge int[][] Un tableau 2D avec la valeur de nos energies
     * @throws Exception
     */
    static public int[][] detectEdge(final int[][] pImage,final boolean pWriteFile) throws Exception{
        int vImageWidth = pImage.length;
        int vImageHeight = pImage[0].length;
        BufferedImage vImage = new BufferedImage(vImageWidth,vImageHeight,TYPE_INT_RGB);
        int[][] vEdgeTab = new int[vImageWidth][vImageHeight];

        for(int y = 0; y < vImageHeight; y++) { // On ajoute les valeurs pour avoir les couts de chaque pixel
            for (int x = 0; x < vImageWidth; x++) {
                int vCurrent = pImage[x][y];
                int vTop = pImage[x][y];
                int vBot = pImage[x][y];
                int vLeft = pImage[x][y];
                int vRight = pImage[x][y];
                int vTopLeft = pImage[x][y];
                int vTopRight = pImage[x][y];
                int vBotLeft = pImage[x][y];
                int vBotRight = pImage[x][y];
                int vTopLeftP = pImage[x][y];
                int vTopRightP = pImage[x][y];
                int vBotLeftP = pImage[x][y];
                int vBotRightP = pImage[x][y];
                int vTopP = pImage[x][y];
                int vBotP = pImage[x][y];
                int vLeftP = pImage[x][y];
                int vRightP = pImage[x][y];
                int vTopMidLeft = pImage[x][y];
                int vTopMidRight = pImage[x][y];
                int vBotMidRight = pImage[x][y];
                int vBotMidLeft = pImage[x][y];
                int vLeftMidTop = pImage[x][y];
                int vLeftMidBot = pImage[x][y];
                int vRightMidTop = pImage[x][y];
                int vRightMidBot = pImage[x][y];

                try{
                vTop = pImage[x][y-1];
                vTopP = pImage[x][y-2];
                }catch(Exception pE){}
                try{
                vBot = pImage[x][y+1];
                vBotP = pImage[x][y+2];
                }catch(Exception pE){}
                try{
                vRight = pImage[x+1][y];
                vRightP = pImage[x+2][y];
                }catch(Exception pE){}
                try{
                vLeft = pImage[x-1][y];
                vLeftP = pImage[x-2][y];
                }catch(Exception pE){}
                try{
                vTopRight = pImage[x+1][y-1];
                vTopRightP = pImage[x+2][y-2];
                vTopMidRight = pImage[x+1][y-2];
                vRightMidTop = pImage[x+2][y-1];
                }catch(Exception pE){}
                try{
                vTopLeft = pImage[x-1][y-1];
                vTopLeftP = pImage[x-2][y-2];
                vTopMidLeft = pImage[x-1][y-2];
                vLeftMidTop = pImage[x-2][y-1];
                }catch(Exception pE){}
                try{
                vBotRight = pImage[x+1][y+1];
                vBotRightP = pImage[x+2][y+2];
                vBotMidRight = pImage[x+1][y+2];
                vRightMidBot = pImage[x+2][y+1];
                }catch(Exception pE){}
                try{
                vBotLeft = pImage[x-1][y+1];
                vBotLeftP = pImage[x-2][y+2];
                vBotMidLeft = pImage[x-1][y+2];
                vLeftMidBot = pImage[x-2][y+1];
                }catch(Exception pE){}
                
                int vRedX = Math.abs(new Color(vRight).getRed() - new Color(vLeft).getRed());
                int vGreenX = Math.abs(new Color(vRight).getGreen() - new Color(vLeft).getGreen());
                int vBlueX = Math.abs(new Color(vRight).getBlue() - new Color(vLeft).getBlue());

                int vRedY = Math.abs(new Color(vTop).getRed() - new Color(vBot).getRed());
                int vGreenY = Math.abs(new Color(vTop).getGreen() - new Color(vBot).getGreen());
                int vBlueY = Math.abs(new Color(vTop).getBlue() - new Color(vBot).getBlue());

                int vRedX2 = Math.abs(new Color(vRightP).getRed() - new Color(vLeftP).getRed());
                int vGreenX2 = Math.abs(new Color(vRightP).getGreen() - new Color(vLeftP).getGreen());
                int vBlueX2 = Math.abs(new Color(vRightP).getBlue() - new Color(vLeftP).getBlue());

                int vRedY2 = Math.abs(new Color(vTopP).getRed() - new Color(vBotP).getRed());
                int vGreenY2 = Math.abs(new Color(vTopP).getGreen() - new Color(vBotP).getGreen());
                int vBlueY2 = Math.abs(new Color(vTopP).getBlue() - new Color(vBotP).getBlue());

                int vRedXY = Math.abs(new Color(vTopRight).getRed() - new Color(vBotLeft).getRed());
                int vGreenXY = Math.abs(new Color(vTopRight).getGreen() - new Color(vBotLeft).getGreen());
                int vBlueXY = Math.abs(new Color(vTopRight).getBlue() - new Color(vBotLeft).getBlue());

                int vRedYX = Math.abs(new Color(vBotRight).getRed() - new Color(vTopLeft).getRed());
                int vGreenYX = Math.abs(new Color(vBotRight).getGreen() - new Color(vTopLeft).getGreen());
                int vBlueYX = Math.abs(new Color(vBotRight).getBlue() - new Color(vTopLeft).getBlue());

                int vRedXY2 = Math.abs(new Color(vTopRightP).getRed() - new Color(vBotLeftP).getRed());
                int vGreenXY2 = Math.abs(new Color(vTopRightP).getGreen() - new Color(vBotLeftP).getGreen());
                int vBlueXY2 = Math.abs(new Color(vTopRightP).getBlue() - new Color(vBotLeftP).getBlue());

                int vRedYX2 = Math.abs(new Color(vBotRightP).getRed() - new Color(vTopLeftP).getRed());
                int vGreenYX2 = Math.abs(new Color(vBotRightP).getGreen() - new Color(vTopLeftP).getGreen());
                int vBlueYX2 = Math.abs(new Color(vBotRightP).getBlue() - new Color(vTopLeftP).getBlue());

                int vRedYX3 = Math.abs(new Color(vBotMidRight).getRed() - new Color(vTopMidLeft).getRed());
                int vGreenYX3 = Math.abs(new Color(vBotMidRight).getGreen() - new Color(vTopMidLeft).getGreen());
                int vBlueYX3 = Math.abs(new Color(vBotMidRight).getBlue() - new Color(vTopLeftP).getBlue());

                int vRedYX4 = Math.abs(new Color(vBotMidLeft).getRed() - new Color(vTopMidRight).getRed());
                int vGreenYX4 = Math.abs(new Color(vBotMidLeft).getGreen() - new Color(vTopMidRight).getGreen());
                int vBlueYX4 = Math.abs(new Color(vBotMidLeft).getBlue() - new Color(vTopMidRight).getBlue());

                int vRedYX5 = Math.abs(new Color(vLeftMidTop).getRed() - new Color(vRightMidBot).getRed());
                int vGreenYX5 = Math.abs(new Color(vLeftMidTop).getGreen() - new Color(vRightMidBot).getGreen());
                int vBlueYX5 = Math.abs(new Color(vLeftMidTop).getBlue() - new Color(vRightMidBot).getBlue());

                int vRedYX6 = Math.abs(new Color(vLeftMidBot).getRed() - new Color(vRightMidTop).getRed());
                int vGreenYX6 = Math.abs(new Color(vLeftMidBot).getGreen() - new Color(vRightMidTop).getGreen());
                int vBlueYX6 = Math.abs(new Color(vLeftMidBot).getBlue() - new Color(vRightMidTop).getBlue());
                
                vEdgeTab[x][y] = vRedX+vRedY+vRedX2+vRedY2+vRedYX+vRedXY2+vRedYX2+vRedYX3+vRedYX4+vRedYX5+vRedYX6+vRedXY
                               + vGreenX+vGreenY+vGreenX2+vGreenY2+vGreenXY+vGreenYX+vGreenXY2+vGreenYX2+vGreenYX3+vGreenYX4+vGreenYX5+vGreenYX6
                               + vBlueX+vBlueY+vBlueX2+vBlueY2+vBlueXY+vBlueYX+vBlueXY2+vBlueYX2+vBlueYX3+vBlueYX4+vBlueYX5+vBlueYX6;
                

                // Filtre Laplacien
                /*
                vEdgeTab[x][y] = -4*(((new Color(vCurrent).getRed())+(new Color(vCurrent).getBlue())+(new Color(vCurrent).getBlue()))/3)
                                 +1*(((new Color(vTop).getRed())+(new Color(vTop).getBlue())+(new Color(vTop).getBlue()))/3)
                                 +1*(((new Color(vBot).getRed())+(new Color(vBot).getBlue())+(new Color(vBot).getBlue()))/3)
                                 +1*(((new Color(vLeft).getRed())+(new Color(vLeft).getBlue())+(new Color(vLeft).getBlue()))/3)
                                 +1*(((new Color(vRight).getRed())+(new Color(vRight).getBlue())+(new Color(vRight).getBlue()))/3);*/
                
                vImage.setRGB(x,y,new Color((vRedX+vRedY+vRedX2+vRedY2+vRedYX+vRedXY2+vRedYX2+vRedYX3+vRedYX4+vRedYX5+vRedYX6+vRedXY)/24,
                                            (vGreenX+vGreenY+vGreenX2+vGreenY2+vGreenXY+vGreenYX+vGreenXY2+vGreenYX2+vGreenYX3+vGreenYX4+vGreenYX5+vGreenYX6)/24,
                                            (vBlueX+vBlueY+vBlueX2+vBlueY2+vBlueXY+vBlueYX+vBlueXY2+vBlueYX2+vBlueYX3+vBlueYX4+vBlueYX5+vBlueYX6)/24).getRGB());
            }
        }

        if(pWriteFile){
            File vOutputfile = new File("edge.png");
            ImageIO.write(vImage, "png", vOutputfile);
        }
        return vEdgeTab;
    }

    /** permet de calculer les chemins de valeurs minimun verticale
     * @param pEdge tableau avec les valeurs des bords
     */
    static int[][]calculerMColone(final int[][] pEdge) throws Exception{ 
        int vImageWidth = pEdge.length;
        int vImageHeight = pEdge[0].length;
        int[][] vM = new int[vImageWidth][vImageHeight];

        for(int y = 0; y < 1; y++) { // On ajoute les valeurs dans la premiere ligne
            for (int x = 0; x <vImageWidth;x++) {
                vM[x][y] = pEdge[x][y];
            }//endfor
        }//endfor

        for(int y = 1; y < vImageHeight; y++) { // On met -1 partout pour dire que la case n'a jamais ete ecrite
            for (int x = 0; x < vImageWidth;x++) {
                vM[x][y] = -1;
            }//endfor
        }//endfor

        
        for(int y = 0; y < vImageHeight-1; y++) {
            for (int x = 0; x < vImageWidth; x++) {
                int vBas = vM[x][y] + pEdge[x][y + 1];

                if (vBas < vM[x][y + 1] || vM[x][y + 1] == -1) { // Si vM == -1 on considere que cette case n'a jamais
                                                                 // ete touche
                    vM[x][y + 1] = vBas;
                }
                try {// on met un try pour eviter la sorti de tableau sur les bords
                    int vDiagonaleDroite = vM[x][y] + pEdge[x + 1][y + 1];
                    if (vDiagonaleDroite < vM[x + 1][y + 1] || vM[x + 1][y + 1] == -1) {
                        vM[x + 1][y + 1] = vDiagonaleDroite;
                    }//endif
                } catch (Exception pE) {
                }
                try {
                    int vDiagonaleGauche = vM[x][y] + pEdge[x - 1][y + 1];
                    if (vDiagonaleGauche < vM[x-1][y + 1] || vM[x-1][y + 1] == -1) {
                        vM[x-1][y + 1] = vDiagonaleGauche;
                    }
                }catch(Exception pE){}
            }//endfor
        }//endfor

        return vM;
    }//calculerMColonne()

    /** permet de calculer les chemins de valeurs minimun horizontal
     * @param pEdge tableau avec les valeurs des bords
     */
    static int[][]calculerMLigne(final int[][] pEdge) throws Exception{ 
        int vImageWidth = pEdge.length;
        int vImageHeight = pEdge[0].length;
        int[][] vM = new int[vImageWidth][vImageHeight];

        for(int y = 0; y < vImageHeight; y++) { // On ajoute les valeurs dans la premiere ligne
            for (int x = 0; x <1;x++) {
                vM[x][y] = pEdge[x][y];
            }//endfor
        }//endfor

        for(int y = 0; y < vImageHeight; y++) { // On met -1 partout pour dire que la case n'a jamais ete ecrite
            for (int x = 1; x < vImageWidth;x++) {
                vM[x][y] = -1;
            }//endfor
        }//endfor

        
        for(int y = 0; y < vImageHeight; y++) {
            for (int x = 0; x < vImageWidth-1; x++) {
                int vDroite = vM[x][y] + pEdge[x+1][y];

                if (vDroite < vM[x + 1][y] || vM[x + 1][y] == -1) { // Si vM == -1 on considere que cette case n'a jamais
                                                                 // ete touche
                    vM[x+1][y] = vDroite;
                }
                try {// on met un try pour eviter la sorti de tableau sur les bords
                    int vDiagonaleHaut = vM[x][y] + pEdge[x + 1][y - 1];
                    if (vDiagonaleHaut < vM[x + 1][y - 1] || vM[x + 1][y - 1] == -1) {
                        vM[x + 1][y - 1] = vDiagonaleHaut;
                    }
                } catch (Exception pE) {
                }
                try {
                    int vDiagonaleBas = vM[x][y] + pEdge[x + 1][y + 1];
                    if (vDiagonaleBas < vM[x+1][y + 1] || vM[x+1][y + 1] == -1) {
                        vM[x+1][y + 1] = vDiagonaleBas;
                    }
                }catch(Exception pE){}
            }//endfor
        }//endfor

        return vM;
    }//calculerMLigne()

    /**
     * Permet de calculer la valeur minimal ainsi que ses coordonnées
     * @param pM Matrice de coup minimun
     * @param pY Valeur de depart de Y
     * @return Un tableau de int, en 0 la valeur minimum, et 1 sa postion en x et en 2 sa postion en y
     */
    static public int[] minValueColonne(final int[][] pM, final int pY){
        int vValMin = pM[0][pY];
        Random vRand = new Random();
        int[] vRandom = new int[pM.length];
        int vCpt = 0;

        for(int x = 0; x < pM.length;x++){
            if(vValMin >= pM[x][pY]) {
                vValMin = pM[x][pY];
            } // endif
        } // endfor

        for(int x = 0; x < pM.length;x++){
            
            if(vValMin == pM[x][pY]) {
                vRandom[vCpt] = x;
                vCpt++;
            } // endif
        } // endfor
        
        return new int[] { vValMin,vRandom[vRand.nextInt(vCpt)] ,pY }; 
    }//minValueColonne()

    /**
     * Permet de calculer la valeur minimal ainsi que ses coordonnées
     * @param pM Matrice de coup minimun
     * @param pX Valeur de depart de X
     * @return Un tableau de int, en 0 la valeur minimum, et 1 sa postion en x et en 2 sa postion en y
     */
    static public int[] minValueLigne(final int[][] pM, final int pX){
        int vValMin = pM[pX][0];
        Random vRand = new Random();
        int[] vRandom = new int[pM[0].length];
        int vCpt = 0;

        for(int y = 0; y < pM[0].length;y++){
            if(vValMin >= pM[pX][y]) {
                vValMin = pM[pX][y];
            } // endif
        } // endfor

        for(int y = 0; y < pM[0].length;y++){
            
            if(vValMin == pM[pX][y]) {
                vRandom[vCpt] = y;
                vCpt++;
            } // endif
        } // endfor
        
        return new int[] { vValMin, pX,vRandom[vRand.nextInt(vCpt)] }; 
    }//minValueColonne()

    /**
     * retourne un stack avec en haut la derniere composante
     * @param pM Matrice de coup minimun
     * @param pValMin tableau retourné par minValue()
     * @return Stack<int[]> avec en 0 la coordonée x et en 1 y
     */
    static public Stack<int[]> ccmColonne(final int[][] pM,final int[] pValMin){
        int vXDepart = pValMin[1];
        int vYDepart = pValMin[2];
        int Infini = Integer.MAX_VALUE;
        if(vYDepart == 0 ){ //fin de notre reccurence
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
            Stack<int[]> vReturn = ccmColonne(pM, new int[]{pValMin[0],vXDepart,vYDepart-1});
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
            
        }//endif
        else if(vDiagonaleGauche > vDiagonaleDroite){
            Stack<int[]> vReturn = ccmColonne(pM, new int[]{pValMin[0],vXDepart+1,vYDepart-1});
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//ende lse if
        else{
            Stack<int[]> vReturn = ccmColonne(pM, new int[]{pValMin[0],vXDepart-1,vYDepart-1});
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//end else
    }//ccmColonne()
    
        /**
     * retourne un stack avec en haut la derniere composante
     * @param pM Matrice de coup minimun
     * @param pValMin tableau retourné par minValue()
     * @return Stack<int[]> avec en 0 la coordonée x et en 1 y
     */
    static public Stack<int[]> ccmLigne(final int[][] pM,final int[] pValMin){
        int vXDepart = pValMin[1];
        int vYDepart = pValMin[2];
        int Infini = Integer.MAX_VALUE;
        
        if(vXDepart == 0 ){ //fin de notre reccurence
            Stack<int[]> vReturn = new Stack<int[]>();
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//end if
        int vDiagonaleHaut = Infini;
        int vDiagonaleBas = Infini;

        try{
            vDiagonaleHaut = pM[vXDepart-1][vYDepart-1];
        }catch(Exception pE){}
        try{
            vDiagonaleBas = pM[vXDepart-1][vYDepart+1];
        }catch(Exception pE){}

        if(pM[vXDepart-1][vYDepart] < Math.min(vDiagonaleBas, vDiagonaleHaut)) {
            Stack<int[]> vReturn = ccmLigne(pM, new int[]{pValMin[0],vXDepart-1,vYDepart});
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
            
        }//endif
        else if(vDiagonaleBas > vDiagonaleHaut){
            Stack<int[]> vReturn = ccmLigne(pM, new int[]{pValMin[0],vXDepart-1,vYDepart-1});
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//ende lse if
        else{
            Stack<int[]> vReturn = ccmLigne(pM, new int[]{pValMin[0],vXDepart-1,vYDepart+1});
            vReturn.push(new int[]{vXDepart,vYDepart});
            return vReturn;
        }//end else
    }//ccmColonne()

    /**
     * Permet de convertir le pourcentage entree en parametre en un entier du nombre de ligne a supprimer
     * @param pImage Matrice 2D de l'image
     * @param pPourcentage Entier du pourcentage de ligne a retirer
     * @return entier du nombre de ligne a retirer
     */
    static public int conversionPourcentageLigne (final int[][] pImage, final int pPourcentage){
        return (pImage[0].length*pPourcentage)/100;
    }//conversionPourcentageLigne
    
    //Similaire a la précedente mais pour les colonnes
    static public int conversionPourcentageColonne (final int[][] pImage, final int pPourcentage){
        return (pImage.length*pPourcentage)/100;
    }//conversionPourcentageColonne

    /**
     * Permet de retirer le nombre de colonne necessaire
     * @param pColonne nombre de colonnes de pixel a retirer
     * @param pEdge Image avec les bords affichés 
     * @param pImageTab Image source
     * @return Tableau de l'image avec p colonne en moins mais matrice non redimensionné
     * @throws Exception
     */
    static public int[][] retirerColonne(final int pColonne, final int [][] pImageTab) throws Exception{
        System.gc();//Force le garbage collector
        int[][] vEdgeModifie = detectEdge(pImageTab,true);
        int[][] vImageTabModifie = pImageTab;
        int vColonne = pColonne;

        while(vColonne != 0){
            if(vColonne%50 == 0){
                long vEnd = new Date().getTime();
                System.out.print("  " + (((vEnd - vTimeColonneStart)*(vColonne/50))/1000) + " seconds remaining.\n"); //permet d'afficher le temps de calcul restant
                vTimeColonneStart = new Date().getTime();
            }//endif
            int[][] vMColonne = calculerMColone(vEdgeModifie);
            int[] vColonneValue = minValueColonne(vMColonne,vMColonne[0].length-1);
            //vEdgeModifie = decalerLigne(ccmColonne(vMColonne, vColonneValue),vEdgeModifie,vColonne,vMColonne,vColonneValue);
            vImageTabModifie = decalerLigne(ccmColonne(vMColonne, vColonneValue),vImageTabModifie,vColonne,vMColonne,vColonneValue);
            vEdgeModifie = detectEdge(vImageTabModifie,false);
            vColonne--;
        }

        return vImageTabModifie;

    }//retirerColonne()
    
    /**
     * Permet de retirer le nombre de ligne necessaire
     * @param pLigne nombre de lignes de pixel a retirer
     * @param pEdge Image avec les bords affichés 
     * @param pImageTab Image source
     * @return Tableau de l'image avec p colonne en moins mais matrice non redimensionné
     * @throws Exception
     */
    static public int[][] retirerLigne(final int pLigne, final int [][] pImageTab) throws Exception{
        System.gc();//Force le garbage collector
        int[][] vEdgeModifie = detectEdge(pImageTab,false);
        int[][] vImageTabModifie = pImageTab;
        int vLigne = pLigne;

        while(vLigne != 0){
            if(vLigne%50 == 0){
                long vEnd = new Date().getTime();
                System.out.print("  " + (((vEnd - vTimeLigneStart)*(vLigne/50))/1000) + " seconds remaining.\n"); //permet d'afficher le temps de calcul restant
                vTimeLigneStart = new Date().getTime();
            }//endif
            int[][] vMLigne = calculerMLigne(vEdgeModifie);
            int[] vLigneValue = minValueLigne(vMLigne,vMLigne.length-1);
            //vEdgeModifie = decalerColonne(ccmLigne(vMLigne, vLigneValue),vEdgeModifie,vLigne,vMLigne,vLigneValue);
            vImageTabModifie = decalerColonne(ccmLigne(vMLigne, vLigneValue),vImageTabModifie,vLigne,vMLigne,vLigneValue);
            vEdgeModifie = detectEdge(vImageTabModifie,false);
            vLigne--;
        }

        return vImageTabModifie;

    }//retirerLigne()


    static public int[][] decalerLigne(final Stack<int[]> pPixel, final int[][] pImageTab,final int pColonne,final int[][] pMColonne, final int[] pColonneValue) throws Exception{
        int[][] vImageReturn = new int[pImageTab.length-1][pImageTab[0].length];
        
        //decommenter les lignes suivantes pour creer tracer les seams
        /*
        Stack<int[]> vStackColonne = ccmColonne(pMColonne, pColonneValue);
        vImageSeams = pImageTab;
        for(int y = 0; y < vImageSeams[0].length; y++) {
            int[] test = vStackColonne.pop();
            for(int x = 0; x < vImageSeams.length;x++){
                vImageSeams[test[0]][test[1]] = new Color(255,0,0).getRGB();
            }
        }
        createFile(vImageSeams, "animation/" + Math.abs(pColonne-714));*/

        for(int y = 0; y < vImageReturn[0].length; y++) {
            for(int x = 0; x < vImageReturn.length;x++){
                vImageReturn[x][y] = pImageTab[x][y];
            }
        }
        for(int y = 0; y < pImageTab[0].length; y ++){
            int[] vPixel = pPixel.pop();
            for(int x = vPixel[0]; x < pImageTab.length-1;x++){
                vImageReturn[x][vPixel[1]] = pImageTab[x+1][vPixel[1]];
            }
        }
        //System.out.println(pImageReturn.length);
        return vImageReturn;
                
    }//decalerLigne()

    static public int[][] decalerColonne(final Stack<int[]> pPixel, final int[][] pImageTab,final int pLigne,final int[][] pMLigne, final int[] pLigneValue) throws Exception{
        int[][] vImageReturn = new int[pImageTab.length][pImageTab[0].length-1];
        
        //decommenter les lignes suivantes pour creer l'animation
        /*
        Stack<int[]> vStackLigne = ccmLigne(pMLigne, pLigneValue);
        vImageSeams = pImageTab;
        for(int x = 0; x < vImageSeams.length; x++) {
            int[] test = vStackLigne.pop();
            for(int y = 0; y < vImageSeams[0].length;y++){
                vImageSeams[test[0]][test[1]] = new Color(255,0,0).getRGB();
            }
        }
        createFile(vImageSeams, "animation/" + (Math.abs((pLigne-484))+714));*/

        for(int y = 0; y < vImageReturn[0].length; y++) {
            for(int x = 0; x < vImageReturn.length;x++){
                vImageReturn[x][y] = pImageTab[x][y];
            }
        }
        for(int x = 0; x < pImageTab.length; x ++){
            int[] vPixel = pPixel.pop();
            for(int y = vPixel[1]; y < pImageTab[0].length-1;y++){
                vImageReturn[vPixel[0]][y] = pImageTab[vPixel[0]][y+1];
            }
        }
        //System.out.println(pImageReturn.length);
        return vImageReturn;
                
    }//decalerColonne()


    /**
     * Permet de créer un fichier png a partir du tableau en parametre
     * @param pImageTab tableau 2D qui contient la valeur RGB en un int signé
     * @param pName String du nom du fichier
     * @throws Exception Erreur lors de l'écriture
     */
    static public void createFile(final int[][] pImageTab, final String pName) throws Exception{
        File vOutputfile = new File(pName + ".png");
        int vWidth = pImageTab.length;
        int vHeight = pImageTab[0].length;
        BufferedImage vImage = new BufferedImage(vWidth,vHeight,TYPE_INT_RGB);

        for(int y = 0; y < vHeight; y++) {
            for(int x = 0; x < vWidth;x++){
                vImage.setRGB(x, y, pImageTab[x][y]);
            }
        }
        ImageIO.write(vImage, "png", vOutputfile);
    }//createFile()


    static public void main(final String[] args){
        try{
            String vNameImage = args[0];
            long vStart = new Date().getTime();
            System.out.print("Processing : \n");
            
            System.out.print("Loading file.\n");
            int[][] vImageTab = createImageTab(vNameImage);

            int vPourcentageColonne =Integer.parseInt(args[1]);
            int vPourcentageLigne =Integer.parseInt(args[2]);

            int vColonneToDelete = conversionPourcentageColonne(vImageTab, vPourcentageColonne); 
            int vLigneToDelete = conversionPourcentageLigne(vImageTab, vPourcentageLigne);
            
            vImageSeams = new int[vImageTab.length][vImageTab[0].length]; //Localisation des pixels des seams

            System.out.print("Editing image vertically.\n");
            int[][] vImageRedimensionnee = retirerColonne(vColonneToDelete, vImageTab);

            System.out.print("Editing image horizontally.\n");
            vImageRedimensionnee = retirerLigne(vLigneToDelete, vImageRedimensionnee); //On recupere limage avec les colonnes en moins et on retire les lignes

            System.out.print("Saving file.\n");
            createFile(vImageRedimensionnee,"final");

            System.out.println("\nDone !\n");

            long vEnd = new Date().getTime();
            long vTime = (vEnd - vStart);
            System.out.println("Total excecution time : " + vTime/1000 + " seconds.");
        }//end try
        catch(Exception pE){

            System.err.println(pE);

        }//endcatch
    }//end main()
}//endclass Seamcarving