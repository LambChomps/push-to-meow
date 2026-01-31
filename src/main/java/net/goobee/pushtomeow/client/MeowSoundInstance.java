package net.goobee.pushtomeow.client;

import net.goobee.pushtomeow.PushToMeow;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class MeowSoundInstance extends MovingSoundInstance {
    private final PlayerEntity player;

    public MeowSoundInstance(PlayerEntity sentPlayer, SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
        this.player = sentPlayer;
        this.volume = volume;
        this.pitch = pitch;
        this.attenuationType = AttenuationType.LINEAR;

        Vec3d pos = this.player.getEyePos();
        HitResult hitResult = this.player.raycast(1, 1f, true);
        if (hitResult != null) {
            pos = pos.add(pos.relativize(hitResult.getPos()));
            PushToMeow.LOGGER.info("CLIENT INFO: {}, {}", pos, hitResult.getPos());
        }
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void tick() {
        if (this.player.isRemoved()) this.setDone();
        else {
            Vec3d pos = this.player.getEyePos();
            HitResult hitResult = this.player.raycast(1, 1f, true);
            if (hitResult != null) {
                pos = pos.add(pos.relativize(hitResult.getPos()));
            }
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        }
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return !this.player.isSilent();
    }
}
