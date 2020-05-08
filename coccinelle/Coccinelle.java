//Bonjour et bienvenue dans ce programme d'algorthmique sur les coccinelles.
public class Coccinelle{
    
    static int[][]calculerM(final int[][] pM){ 
        int vCMax = pM[0].length;
        int vLMax = pM.length;
        int[][] M = new int[vLMax][vCMax];//Terme genrale du tableau m(L,C)

        for(int i = 0; i < vLMax;i++){
            for(int j = 0; j < vCMax;j++){
                M[i][j] = pM[i][j]; //On copie notre grille des pucerons dans notre 
            }//endfor
        }//endfor

        for(int l = 0; l < vLMax;l++){
            for(int c = 0; c < vCMax;c++){
                int vVn = 0;
                int vVOldn = 0;
                int vVne = 0;
                int vVOldne = 0;
                int vVnw = 0;
                int vVOldnw = 0;

                try{
                    vVn = M[l][c] + pM[l+1][c];
                    vVOldn = M[l+1][c];
                }//endtry
                catch(Exception pE){ }

                try{
                    vVne = M[l][c] + pM[l+1][c+1];
                    vVOldne = M[l+1][c+1];
                }//endtry
                catch(Exception pE){ }

                try{
                    vVnw = M[l][c] + pM[l+1][c-1];
                    vVOldnw = M[l+1][c-1];
                }//endtry
                catch(Exception pE){}
                if(vVnw > vVOldnw){
                    M[l+1][c-1] = vVnw;
                    }//endif
                if(vVne > vVOldne){
                    M[l+1][c+1] = vVne;
                    }//endif
                if(vVn > vVOldn){
                    M[l+1][c] = vVn;
                }//endif
            }//endfor
        }//endfor
        return M;
    }//calculerM()

    static void afficherM(final int[][] pM){
        System.out.println("Tableau M[L][C] de terme general M[l][c] = m(l,c) :");
        for(int i = 7; i >= 0;i--){
            for(int j = 0; j < 5;j++){
                if(pM[i][j] < 100) { //Ces deux conditions permetttent l'alignement des valeurs
                    if (pM[i][j] < 10) { 
                        System.out.print("  " + pM[i][j] + " ");
                    } else
                        System.out.print(" " + pM[i][j] + " ");
                } else
                    System.out.print("" + pM[i][j] + " ");
            }//endfor
            System.out.println("");
        }//endfor
        System.out.println("");
    }//afficherM()

    static void afficherResultat(final int[][] pM){
        int vCMax = pM[0].length;
        int vLMax = pM.length;
        int vIndiceMax = afficherValeurMax(pM,vCMax);
        System.out.print("Elle a suivi le chemin : ");
        int[][] vDebutFin = accm(pM,vLMax-1,vIndiceMax,0);
        System.out.printf("\nCase d'atterrissage = (%d,%d) \n",vDebutFin[0][0],vDebutFin[0][1]);
        System.out.printf("Case de l'interview = (%d,%d)",vDebutFin[01][0],vDebutFin[1][1]);

    }//afficherResultat()

    static int afficherValeurMax(final int[][] pM,final int pCMax){
        int vRes = pM[7][0];
        int vIndice = 0;
        for(int i=0; i<pCMax-1;i++){    
            if(vRes < pM[7][i + 1]){
                vIndice = i+1;
                vRes = pM[7][i + 1];
            }//endif
        }//endfor
        System.out.println("La coccinelle a mange " + vRes + " pucerons.");
        return vIndice;
    }//afficherValeurMax()

    /**
     * Retourne un tableau 2d ou a l'indice 0 : tableau contenant les coordonees de la case d'interview
     *                             l'indice 1 : tableau contenant les coordonees de la case d'atterrissage
     */
    static int[][] accm(final int[][] pM,final int pI,final int pJ,final int pJFinal){ 
        int vMoinsUn = 0;
        int[][] vReturn = new int[2][2];
        if(pI == -1)return new int[][]{{pI+1,pJ},{pM.length-1,pJFinal}};
        try{
            vMoinsUn = pM[pI][pJ-1];
        }//endtry
        catch(Exception pE){};
        if(pM[pI][pJ+1] < vMoinsUn && vMoinsUn > pM[pI][pJ]){
            if(pI == pM.length) accm(pM,pI-1, pJ+1,pJ);
            vReturn = accm(pM,pI-1,pJ-1,pJFinal);
            System.out.printf("(%d,%d)",pI,pJ-1);
        }//endif
        else if (pM[pI][pJ+1] < pM[pI][pJ]){
            if(pI == pM.length) accm(pM,pI-1, pJ+1,pJ);
            vReturn = accm(pM,pI-1,pJ,pJFinal);
            System.out.printf("(%d,%d)",pI,pJ);
        }//end elseif
        else {
            if(pI == pM.length) accm(pM,pI-1, pJ+1,pJ);
            vReturn = accm(pM,pI-1, pJ+1,pJFinal);
            System.out.printf("(%d,%d)",pI,pJ+1);
        }//end else
        return vReturn;
    }//accm()

    static public void main(String[] args){
        int[][] grillePucerons = new int[][]{ //Déclaration du tableau
            {2 ,4 ,3 ,9 ,6 },
            {1 ,10,15,1 ,2 },
            {2 ,4 ,11,26,66},
            {36,34,1 ,13,30},
            {46,2 ,8 ,7 ,15},
            {89,27,10,12,3 },
            {1 ,72,3 ,6 ,6 },
            {3 ,1 ,2 ,4 ,5 }};
            int[][] vM = calculerM(grillePucerons);
            afficherM(vM);
            afficherResultat(vM);
    }//main()
}//Class cocinelle

//Je vais me prendre un cafe