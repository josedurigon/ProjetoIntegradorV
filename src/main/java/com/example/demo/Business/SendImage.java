package com.example.demo.Business;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SendImage {
    public void uploadImage(String imageAdress){
        try{
            InputStream is = this.getClass().getResourceAsStream(imageAdress);

            BufferedImage img = ImageIO.read(is);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            ImageIO.write(img, "mri", bao);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

}
