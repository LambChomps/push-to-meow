package net.goobee.pushtomeow.client;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class MeowSoundInstance extends MovingSoundInstance {
    private final PlayerEntity player;

    public MeowSoundInstance(PlayerEntity player, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
        this.player = player;
        this.x = this.player.getX();
        this.y = this.player.getEyePos().getY() - 0.01;
        this.z = this.player.getZ();
        this.volume = volume;
        this.pitch = pitch;
        this.attenuationType = AttenuationType.LINEAR;
    }

    @Override
    public void tick() {
        if (this.player.isRemoved()) this.setDone();
        else {
            this.x = this.player.getX();
            this.y = this.player.getEyePos().getY() - 0.01;
            this.z = this.player.getZ();
        }
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return !this.player.isSilent();
    }
}
