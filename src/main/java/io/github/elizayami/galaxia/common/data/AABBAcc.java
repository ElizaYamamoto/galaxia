package io.github.elizayami.galaxia.common.data;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;



public class AABBAcc {



    public static AxisAlignedBB ofSize(Vector3d vec3, double d, double e, double f) {
        return new AxisAlignedBB(vec3.x - d / 2.0D, vec3.y - e / 2.0D, vec3.z - f / 2.0D, vec3.x + d / 2.0D, vec3.y + e / 2.0D, vec3.z + f / 2.0D);
    }
}
