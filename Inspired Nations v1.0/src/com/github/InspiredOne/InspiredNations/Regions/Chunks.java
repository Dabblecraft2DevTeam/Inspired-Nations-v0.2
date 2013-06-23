/*******************************************************************************
 * Copyright (c) 2013 InspiredOne.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     InspiredOne - initial API and implementation
 ******************************************************************************/
package com.github.InspiredOne.InspiredNations.Regions;

import java.awt.Point;
import java.util.Vector;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class Chunks {

	public Vector<ChunkData> Chunks = new Vector<ChunkData>();
	public Chunks() {
	}

	public Chunks(Vector<Chunk> tiles) {
		Vector<ChunkData> temp = new Vector<ChunkData>();
		for (int i = 0; i < tiles.size(); i++) {
			temp.add(new ChunkData(new Point(tiles.get(i).getX(), tiles.get(i).getZ()), tiles.get(i).getWorld().getName()));
		}
		Chunks.addAll(temp);
	}
	
	public void addChunks(Vector<Chunk> tiles) {
		Vector<ChunkData> temp = new Vector<ChunkData>();
		for (int i = 0; i < tiles.size(); i++) {
			temp.add(new ChunkData(new Point(tiles.get(i).getX(), tiles.get(i).getZ()), tiles.get(i).getWorld().getName()));
		}
		Chunks.addAll(temp);
	}
	
	public void addChunk(Point tile, String worldname) {
		Chunks.add(new ChunkData(tile, worldname));
	}
	
	public void addChunk(ChunkData data) {
		Chunks.add(data);
	}
	
	public void addChunk(Chunk tile) {
		Chunks.add(new ChunkData(new Point(tile.getX(), tile.getZ()), tile.getWorld().getName()));
	}
	
	public void removeChunk(Chunk tile) {
		Chunks.remove(new ChunkData(new Point(tile.getX(), tile.getZ()), tile.getWorld().getName()));
	}
	
	public void removeChunk(ChunkData tile) {
		Chunks.remove(tile);
	}
	
	public void removeChunk(Point tile, String worldname) {
		Chunks.remove(new ChunkData(tile, worldname));
	}
	
	public void removeLast() {
		Chunks.remove(Chunks.size() - 1);
	}

	public boolean isIn(Location spot) {
		Chunk temp = spot.getChunk();
		if (Chunks.contains(new ChunkData(new Point(temp.getX(), temp.getZ()), temp.getWorld().getName()))) return true;
		else return false;
	}
	
	public boolean isIn(ChunkData spot) {
		if (Chunks.contains(spot)) {
			return true;
		}
		else return false;
	}
	
	public boolean isIn(Point spot, String worldname) {
		if (Chunks.contains(new ChunkData(spot, worldname))) return true;
		else return false;
	}
	
	public int Area() {
		return Chunks.size()*256;
	}
	
	public int Volume() {
		return Area()*256;
	}
	
}
