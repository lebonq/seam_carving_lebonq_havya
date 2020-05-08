import java.math.*;

public class Cocinelle{
    
    static int[][]calculerM(){ 
    int[][] M = new int[L+1][C+1];
    M[0][0] = 0;
    for (int c=1; c < C+1; c++) M[0][c] = M[0][c-1] + e(0,c-1);
    for (int l=1; l < L+1; l++) M[l][0] = M[l-1][0] + m(l-1,0);
    
    for (int l = 1; l < L+1; l++)
        for (int c = 1; c < C+l; c++){ // calcul du coût min pour arriver en (l, c)
            int Me = M[l][c-1] + e(l,c-1);
            // coût min si le dernier déplacement est Est
            int Mn = M[l-1][c] + n(l-1,c);
            // coût min si le dernier déplacement est Nord
            int Mne = M[l-1][c-1] + ne(l-1,c-1); // coût min si le dernier déplacement est Nord—Est
            M[l][c] = (int)Math.min(Me, (int)Math.min(Mn,Mne));
        }
    return M;
}

static void afficherCheminCoutMin( int[][]M) { accm(M,L,C);}
    
static void accm(int[][] M, int l, int c){
    if(l==0 && c==0) return;
    int Me = I, Mn = I, Mne = I;
    if ((c-1>=0)) Me=M[l][c-1] + e(l,c-1);
    if ((l-1>=0)) Me=M[l-1][c] + n(l-1,c);
    if ((l-1>=0 && c-1>=0)) Mne=M[l-1][c-1] + ne(l-1,c-1);
    if(M[l][c] == Me){ 
        accm(M,l,c-1);
        System.out.printf("(%d,%d)-->",l,c-1);
    }
    else
        if (M[l][c] == Mn){ 
            accm(M, l-1, c);
            System.out.printf("(%d,%d) -->", l-1,c);
        }
        else {
            accm(M, l-1, c-1);
            System.out.printf("(&d,%d)", l-1,c-1);
        }//endif
    }//Methode accm(M,l,c)
}//Class cocinelle