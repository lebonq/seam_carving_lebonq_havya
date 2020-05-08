public class Cocinelle{
    
    static int[][]calculerM(final int[][] pM){ 
        int vCMax = pM[0].length;//TO-DO
        int vLMax = pM.length;//TO-DO
        int[][] M = new int[vLMax][vCMax];

        for(int i = 0; i < vLMax;i++){ //
            for(int j = 0; j < vCMax;j++){
                M[i][j] = pM[i][j];
            }
        }

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

                }
                catch(Exception pE){ }

                try{
                    vVne = M[l][c] + pM[l+1][c+1];
                    vVOldne = M[l+1][c+1];
                }
                catch(Exception pE){ }

                try{
                    vVnw = M[l][c] + pM[l+1][c-1];
                    vVOldnw = M[l+1][c-1];
                }
                catch(Exception pE){ }

                //if(Math.max(vVn, vVne) < vVnw){
                    if(vVnw > vVOldnw){
                        M[l+1][c-1] = vVnw;
                    }
                //}
                //if( vVn < vVne){
                    if(vVne > vVOldne){
                        M[l+1][c+1] = vVne;
                    }
                //}
                //else{
                    if(vVn > vVOldn){
                        M[l+1][c] = vVn;
                    }
                //}
            }
        }
        return M;

    }// int[][]calculerM()

    static void afficherCheminCoutMax(final int[][]M,final int L) { accm(M,L,0);}

    static void accm(final int[][] pM,final int pL,final int pC){
        if(pL == 0 && pC == 0)
            return;
        int Mnw = 0, Mn = 0, Mne = 0; // Si on sort du tableau on en rajoute rien
        if ((pC - 1 >= 0))
            Mnw = pM[pL + 1][pC - 1];
        if ((pL - 1 >= 0))
            Mn = pM[pL + 1][pC];
        if ((pL - 1 >= 0 && pC - 1 >= 0))
            Mne = pM[pL + 1][pC + 1];
        if (pM[pL][pC] == Mnw) {
            accm(pM, pL + 1, pC - 1);
            System.out.printf("(%d,%d)-->", pL + 1, pC - 1);
        } // endif
        else if (pM[pL][pC] == Mn) {
            accm(pM, pL + 1, pC);
            System.out.printf("(%d,%d) -->", pL + 1, pC);
        } // endif
        else {
            accm(pM, pL + 1, pC + 1);
            System.out.printf("(&d,%d)", pL + 1, pC + 1);
            }//endelse
    }//Methode accm(M,l,c) 

    static public void main(String[] args){
        int[][] grillePucerons = new int[][]{
            {2 ,4 ,3 ,9 ,6 },
            {1 ,10,15,1 ,2 },
            {2 ,4 ,11,26,66},
            {36,34,1 ,13,30},
            {46,2 ,8 ,7 ,15},
            {89,27,10,12,3 },
            {1 ,72,3 ,6 ,6 },
            {3 ,1 ,2 ,4 ,5 }};
            int[][] m = calculerM(grillePucerons);

            for(int i = 7; i >= 0;i--){
                for(int j = 0; j < 5;j++){
                    System.out.print(""  + m[i][j] + " ");
                }
                System.out.println(" ");
            }

    }
}//Class cocinelle
//Je vais me prendre un cafe