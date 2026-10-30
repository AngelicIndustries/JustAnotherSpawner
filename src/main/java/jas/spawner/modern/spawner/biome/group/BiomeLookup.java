package jas.spawner.modern.spawner.biome.group;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.Loader;

/**
 * The biome of a block, not just of its column. With Biomic (API version 2) that is the 3D biome at the block, and
 * each of Biomic's sky island identities that shares its biome with the ground (biomic:floating/..., biomic:aeropelago/...,
 * biomic:hosted/...) is a biome of its own to JAS, named by its key, so islands get their own groups and spawn lists.
 * Without Biomic it is the column's biome, as before.
 */
public final class BiomeLookup {

	/** What a block is to JAS. */
	public static final class Result {
		public final BiomeGenBase biome;
		/** The package name biome groups match: an island identity's key, else the biome's package name. */
		public final String packageName;
		/** Biomic's key at the block, null without Biomic. */
		public final String biomicKey;
		/** Biomic's tags at the block, empty without Biomic. */
		public final Set<String> biomicTags;

		Result(BiomeGenBase biome, String packageName, String biomicKey, Set<String> biomicTags) {
			this.biome = biome;
			this.packageName = packageName;
			this.biomicKey = biomicKey;
			this.biomicTags = biomicTags;
		}
	}

	/** An island identity: the biome it resolves to (its default spawns come from that biome) and its tags. */
	public static final class Island {
		public final String key;
		public final BiomeGenBase biome;
		public final Set<String> tags;

		Island(String key, BiomeGenBase biome, Set<String> tags) {
			this.key = key;
			this.biome = biome;
			this.tags = tags;
		}
	}

	private static Boolean biomic;

	private BiomeLookup() {
	}

	private static boolean biomic() {
		if (biomic == null) {
			biomic = Loader.isModLoaded("biomic") && BiomicBiomes.available();
		}
		return biomic;
	}

	public static Result at(World world, int x, int y, int z) {
		if (biomic()) {
			return BiomicBiomes.at(world, x, y, z);
		}
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		return new Result(biome, BiomeHelper.getPackageName(biome), null, Collections.<String> emptySet());
	}

	public static BiomeGenBase biome(World world, int x, int y, int z) {
		return at(world, x, y, z).biome;
	}

	public static String packageName(World world, int x, int y, int z) {
		return at(world, x, y, z).packageName;
	}

	/** Biomic's island identities by key; empty without Biomic. */
	public static Map<String, Island> islands() {
		return biomic() ? BiomicBiomes.islands() : Collections.<String, Island> emptyMap();
	}

	/**
	 * Biomic's tags for each package name it knows: the island identities and the biomes its catalogue lists. Empty
	 * without Biomic.
	 */
	public static Map<String, Set<String>> tagsByPackageName() {
		return biomic() ? BiomicBiomes.tagsByPackageName() : Collections.<String, Set<String>> emptyMap();
	}

	static Result result(BiomeGenBase biome, String packageName, String biomicKey, Set<String> biomicTags) {
		return new Result(biome, packageName, biomicKey, biomicTags);
	}

	static Island island(String key, BiomeGenBase biome, Set<String> tags) {
		return new Island(key, biome, tags);
	}
}
