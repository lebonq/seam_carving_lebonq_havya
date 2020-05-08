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
        }//endfor
    return M;
    }// int[][]calculerM()

    static void afficherCheminCoutMin( int[][]M) { accm(M,L,C);}
        
    static void accm(int[][] M, int l, int c){
        if(l==0 && c==0) return;
        int Mnw = 0, Mn = 0, Mne = 0; //Si on sort du tableau on en rajoute rien
        if ((c-1>=0)) Mnw=M[l+1][c-1];
        if ((l-1>=0)) Mn=M[l+1][c];
        if ((l-1>=0 && c-1>=0)) Mne=M[l+1][c+1];
        if(M[l][c] == Mnw){ 
            accm(M,l+1,c-1);
            System.out.printf("(%d,%d)-->",l+1,c-1);
        }//endif
        else
            if (M[l][c] == Mn){ 
                accm(M, l-1, c);
                System.out.printf("(%d,%d) -->", l-1,c);
            }//endif
            else {
                accm(M, l-1, c-1);
                System.out.printf("(&d,%d)", l-1,c-1);
            }//endelse
    }//Methode accm(M,l,c)
}//Class cocinelle
//Je vais me prendre un cafe