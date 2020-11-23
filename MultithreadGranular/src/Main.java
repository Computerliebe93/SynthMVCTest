import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.Static;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Grain g1, g2;

        g2 = new controller();
        g1 = new Player();
        g1.sample();
        g2.sample();
    }
}

abstract class Grain {
    abstract void sample();
    abstract void controller();
    AudioContext ac = new AudioContext();
}


class Player extends Grain {
    @Override
    void sample() {
        AudioContext ac = new AudioContext();
        Sample sourceSample = null;
        boolean sampleReady = false;
        try {
            sourceSample = new Sample("bells.wav");
        } catch (Exception e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GranularSamplePlayer gsp = new GranularSamplePlayer(ac, sourceSample);
        gsp.setLoopType(SamplePlayer.LoopType.LOOP_FORWARDS);

        ac.out.addInput(gsp);
        ac.start();

    }

    @Override
    void controller() {
        AudioContext ac = new AudioContext();
        Sample sourceSample = null;
        boolean sampleReady = false;
        GranularSamplePlayer gsp = new GranularSamplePlayer(ac, sourceSample);

        while (sampleReady) {

                System.out.println("Ready!");
                Scanner scanner = new Scanner(System.in);
                float ScannerFloat = scanner.nextFloat();
                System.out.println("ScannerFloat is set to: " + ScannerFloat);
                gsp.setGrainSize(new Static(ac, 500));

                gsp.setPitch(new Static(ac, ScannerFloat));

            }
        }
    }



