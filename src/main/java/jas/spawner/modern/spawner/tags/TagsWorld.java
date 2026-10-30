package jas.spawner.modern.spawner.tags;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.biome.BiomeGenBase;

public interface TagsWorld {
	public int lightAt(int coordX, int coordY, int coordZ);

	public int torchlightAt(int coordX, int coordY, int coordZ);

	public String blockNameAt(Integer offsetX, Integer offsetY, Integer offsetZ);

	public Block blockAt(int coordX, int coordY, int coordZ);

	public BiomeGenBase biomeAt(int coordX, int coordZ);

	/** The biome at the block: with Biomic, its 3D biome. */
	public BiomeGenBase biomeAt(int coordX, int coordY, int coordZ);

	/** Biomic's biome key at the block, i.e. biomic:hosted/minecraft/forest; null without Biomic. */
	public String biomicKeyAt(int coordX, int coordY, int coordZ);

	/** Biomic's tags at the block, i.e. FLOATING_ISLAND; empty without Biomic. */
	public Set<String> biomicTagsAt(int coordX, int coordY, int coordZ);

	public Block biomeTop(int coordX, int coordZ);

	public Block biomeTop(int coordX, int coordY, int coordZ);

	public Block biomeFiller(int coordX, int coordZ);

	public Block biomeFiller(int coordX, int coordY, int coordZ);

	public Material materialAt(int coordX, int coordY, int coordZ);

	public ChunkCoordinates originPos();

	public boolean skyVisibleAt(int coordX, int coordY, int coordZ);

	public int originDis(int coordX, int coordY, int coordZ);

	public int dimension();

	public long totalTime();

	public long timeOfDay();
	
	public boolean isClearWeather();
}
