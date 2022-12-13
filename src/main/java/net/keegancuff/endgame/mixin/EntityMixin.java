package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class EntityMixin {

    private boolean onPhaseShifter;
    @Shadow
    protected boolean inNetherPortal;
    @Shadow
    public World world;
    @Shadow
    private BlockPos blockPos;
    @Shadow
    private Vec3d velocity = Vec3d.ZERO;
    @Shadow
    private float yaw;
    @Shadow
    private float pitch;
    @Shadow
    public void sendMessage(Text message) {
    }
    @Shadow
    protected DataTracker dataTracker;
    @Shadow
    private static final TrackedData<Boolean> NO_GRAVITY = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Shadow
    protected boolean onGround;

    @Shadow
    public void setNoGravity(boolean noGravity) {
        this.dataTracker.set(NO_GRAVITY, noGravity);
    }

    @Shadow
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }


    @Shadow
    public DataTracker getDataTracker() {
        return this.dataTracker;
    }

    public boolean isOnPhaseShifter(){
        return onPhaseShifter;
    }

    public void setOnPhaseShifter(boolean bool){
        onPhaseShifter = bool;
    }

    @Inject(at = @At("HEAD"), method = "getTeleportTarget", cancellable = true)
    private void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> info) {
        EndGame.LOGGER.info("Injecting from EntityMixin in method getTeleportTarget");
        if (destination.getRegistryKey() == ModDimensions.MIRROR_DIMENSION) {
            TeleportTarget out = new TeleportTarget(new Vec3d((double)blockPos.getX() + 0.5, blockPos.getY(),
                    (double)blockPos.getZ() + 0.5), velocity, yaw, pitch);
            EndGame.LOGGER.info("TeleportTarget:" + out.position.toString());
            info.setReturnValue(out);
        }
        if (destination.getRegistryKey() == World.OVERWORLD && world.getRegistryKey() == ModDimensions.MIRROR_DIMENSION){
            TeleportTarget out = new TeleportTarget(new Vec3d((double)blockPos.getX() + 0.5, blockPos.getY(),
                    (double)blockPos.getZ() + 0.5), velocity, yaw, pitch);
            EndGame.LOGGER.info("TeleportTarget:" + out.position.toString());
            info.setReturnValue(out);
        }
        info.cancel();
    }


}
