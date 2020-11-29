package com.kirbydee.splooshkaboom.utils.sound;

public class StereoVolume implements Volume {

    private final MonoVolume volumeLeft;

    private final MonoVolume volumeRight;

    public static StereoVolume of(MonoVolume volumeLeft, MonoVolume volumeRight) {
        return new StereoVolume(volumeLeft, volumeRight);
    }

    public StereoVolume(MonoVolume volumeLeft, MonoVolume volumeRight) {
        this.volumeLeft = volumeLeft;
        this.volumeRight = volumeRight;
    }

    @Override
    public float getLeft() {
        return this.volumeLeft.getValue();
    }

    @Override
    public float getRight() {
        return this.volumeRight.getValue();
    }
}
