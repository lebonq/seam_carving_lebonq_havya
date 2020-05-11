import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;

import javax.imageio.ImageIO;

public class SeamCarving{

    static public int[][] createImageTab(final String pImageName) throws Exception{

        BufferedImage vImage = ImageIO.read(new File(pImageName));

        int vImageWidth = vImage.getWidth();
        int vImageHeight = vImage.getHeight();
        int[][] vImageTab = new int[vImageHeight][vImageWidth];

        for(int i = 0; i < vImageHeight;i++){
            for(int j = 0; j < vImageWidth;j++){
                vImageTab[i][j]= vImage.getRGB(i, j);
            }
        }
        System.out.print(vImageTab);
        return vImageTab;
    }

    static public void createFile(final int[][] pImageTab) throws Exception{
        File vOutputfile = new File("image.jpg");
        BufferedImage vImage = new BufferedImage(256,256,TYPE_INT_RGB);

        for(int i = 0; i < 512;i++){
            for(int j = 0; j < 512;j++){
                vImage.setRGB(i/2, j/2, pImageTab[i][j]);
            }
        }

        ImageIO.write(vImage, "jpg", vOutputfile);
    }

    static public void main(final String[] args){
        try{
            createFile(createImageTab("src/chaton.jpg"));
        }
        catch(Exception pE){
            System.err.println(pE);
        }
    }
}