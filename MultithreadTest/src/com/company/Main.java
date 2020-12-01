package com.company;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.Static;
import java.util.Scanner;

public class Main implements Runnable{
    boolean running = true;

    public static void main(String[] args) {
        // instantiate the AudioContext
        AudioContext ac = new AudioContext();
        // load the source sample from a file
        Sample sourceSample = null;
        boolean sampleReady = false;
        try{
            sourceSample = new Sample("bells.wav");
        }
        catch(Exception e){

            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        // instantiate a GranularSamplePlayer
        GranularSamplePlayer gsp = new GranularSamplePlayer(ac, sourceSample);
        // tell gsp to loop the file
        gsp.setLoopType(SamplePlayer.LoopType.LOOP_FORWARDS);
        // set the grain size to a fixed 10ms
        // tell gsp to behave somewhat randomly
        // gsp.setRandomness(new Static(ac, 1000.0f));
        // connect gsp to ac
        ac.out.addInput(gsp);
        // begin audio processing
        ac.start();

        //THREAD 1//
        Main obj = new Main();
        Thread thread = new Thread(obj);
        thread.start();
        while(true){
            System.out.println("Thread 1");
            Scanner scanner = new Scanner(System.in);
            float scannerFloat = scanner.nextFloat();
            System.out.println("ScannerFloat is set to: " + scannerFloat);
            gsp.setPitch(new Static(ac, 500));
            gsp.setPitch(new Static(ac, scannerFloat));
            gsp.setGrainSize(new Static(20));

            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException e){
                System.out.println("got interrupted 1");
            }
        }
    }

    //THREAD 1///
    public void run() {

        while(running){
            System.out.println("Thread 2");
            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException e ){
                System.out.println("Got interrupted 2");
            }
        }
    }
}

