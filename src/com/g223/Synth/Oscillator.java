package com.g223.Synth;

import com.g223.Synth.utils.RefWrapper;
import com.g223.Synth.utils.Utils;
import org.lwjgl.system.CallbackI;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.lang.invoke.VolatileCallSite;

public class Oscillator extends SynthControlContainer
{
    private static final int TONE_OFFSET_LIMIT = 1000; // Turn down to faster scroll, and up for prescission


    private Wavetable wavetable = Wavetable.Sine;
    private RefWrapper <Integer> toneOffset = new RefWrapper<>(0);
    private RefWrapper <Integer> volume = new RefWrapper<>(100);
    private double keyFrequency;
    private int wavetableStepSize;
    private int wavetableIndex;



    public Oscillator(SynthesizerRemastered synth)
    {
        super(synth);
        JComboBox<Wavetable> comboBox = new JComboBox<>(Wavetable.values());
        comboBox.setSelectedItem(Wavetable.Sine);
        comboBox.setBounds(10, 10, 105, 25);
        comboBox.addItemListener(l ->
        {
            if (l.getStateChange() == ItemEvent.SELECTED)
            {
                wavetable = (Wavetable) l.getItem();
            }

        });
        add(comboBox);
        JLabel toneText = new JLabel("Tone");
        toneText.setBounds(172, 40, 75,25);
        add(toneText);
        JLabel volumeParameter = new JLabel(" %100");
        volumeParameter.setBounds (222, 65, 50, 25);
        volumeParameter.setBorder(Utils.WindowDesign.LINE_BORDER);
        Utils.ParameterHandling.addParameterMouseListeners(volumeParameter, this, 0, 100, 1, volume, () -> volumeParameter.setText(" " + volume.val + "%"));
        add(volumeParameter);
        JLabel volumeText = new JLabel("Volume");
        volumeText.setBounds(225, 40, 75, 25);
        add(volumeText);
        setSize(279, 100);
        setBorder(Utils.WindowDesign.LINE_BORDER);
        setLayout(null);
    }


    public void setKeyFrequency(double frequency)
    {
        keyFrequency = frequency;
        // apply tone offset
        applyToneOffset();
    }

    private double getToneOffset()
    {
        return toneOffset.val / 1000.0d;
    }

    private double getVolumeMultiplier()
    {
        return volume.val / 100.0;
    }

    public double nextSample()
    {
        double sample = wavetable.getSamples()[wavetableIndex] * getVolumeMultiplier();
        wavetableIndex = (wavetableIndex + wavetableStepSize) % Wavetable.SIZE;
        return sample;
    }

    private void applyToneOffset()
    {
        wavetableStepSize = (int)(Wavetable.SIZE * (keyFrequency * Math.pow(2, getToneOffset())) / SynthesizerRemastered.AudioInfo.SAMPLE_RATE);
    }
}
