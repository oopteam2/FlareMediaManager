/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flaremediamanager;

import Iso14496.IsoFile;
import Video.VideoParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcodec.api.JCodecException;
import sun.applet.Main;

/**
 *
 * @author mac
 */
public class FlareMediaManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, JCodecException {
    
        File videoFile = new File("../sample.mp4");
        
        if (!videoFile.exists()) {
            try {
                throw new FileNotFoundException("File not found");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (!videoFile.canRead()) {
            throw new IllegalStateException("No read permissions to file not found");
        }
        
        
        try {
            //Iso iso = new Iso(videoFile);//Iso(new FileInputStream(videoFilePath).getChannel());
            IsoFile isoFile = new IsoFile(videoFile);
        } catch (IOException ex) {
            Logger.getLogger(FlareMediaManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Need id generating system, but okay for now
        String uniqueID = UUID.randomUUID().toString();
        File uniqueDir = new File(uniqueID);
        boolean successful = uniqueDir.mkdir();
        
        if (successful) {
            // creating the directory succeeded
            System.out.println("directory was created successfully");
            VideoParser videoParser = new VideoParser(videoFile);
            
            PrintWriter writer = new PrintWriter(uniqueDir + "/meta.txt", "UTF-8");
            writer.println(videoParser.getVideoFramesTotal());
            writer.println(videoParser.getVideoWidth());
            writer.println(videoParser.getVideoHeight());
            writer.println(videoParser.getVideoFPS());
            writer.println(videoParser.getVideoDuration());
            writer.close();
        }

        
    }
    
}
