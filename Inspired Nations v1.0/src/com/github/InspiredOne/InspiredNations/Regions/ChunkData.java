package com.github.InspiredOne.InspiredNations.Regions;

import java.awt.Point;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class ChunkData {

	public Point point;
	public String world;
	public ChunkData(Point spot, String worldname) {
		point = spot;
		worldname = world;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(point).
            append(world).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof ChunkData))
            return false;

        ChunkData rhs = (ChunkData) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(point, rhs.point).
            append(world, rhs.world).
            isEquals();
    }
	
}
