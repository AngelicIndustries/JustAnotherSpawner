package jas.spawner.modern.spawner.biome.group;

import jas.common.JASLog;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import industries.angelic.biomic.api.BiomeCatalogue;
import industries.angelic.biomic.api.BiomicApi;
import industries.angelic.biomic.api.BiomicBiomeApi;

/** The only class that calls Biomic; {@link BiomeLookup} loads it when Biomic is installed. */
final class BiomicBiomes {

	private static Map<String, BiomeLookup.Island> islands;
	private static Map<String, Set<String>> tagsByPackageName;

	private BiomicBiomes() {
	}

	/** Biomic's biome API version 2 or later. Read at runtime: the constant would be compiled in. */
	static boolean available() {
		try {
			int version = BiomicApi.class.getField("VERSION").getInt(null);
			if (version >= 2) {
				return true;
			}
			JASLog.log().warning("Biomic's API is version %s; 3D biomes need version 2. Using column biomes.", version);
		} catch (ReflectiveOperationException e) {
			JASLog.log().warning("Biomic has no API version; 3D biomes need version 2. Using column biomes.");
		} catch (LinkageError e) {
			JASLog.log().warning("Biomic's API can't be loaded (%s). Using column biomes.", e);
		}
		return false;
	}

	static BiomeLookup.Result at(World world, int x, int y, int z) {
		BiomicBiomeApi.Lookup lookup = BiomicBiomeApi.lookup(world, x, y, z);
		BiomeGenBase biome = lookup.biome();
		String packageName = islands().containsKey(lookup.key()) ? lookup.key() : BiomeHelper.getPackageName(biome);
		return BiomeLookup.result(biome, packageName, lookup.key(), lookup.tags());
	}

	/**
	 * Every sky island identity whose key isn't its biome's own: composite keys (a path under the namespace), which
	 * Biomic stores so an island can share a biome with the ground without a biome ID of its own.
	 */
	static Map<String, BiomeLookup.Island> islands() {
		if (islands != null) {
			return islands;
		}
		Map<String, BiomeLookup.Island> found = new LinkedHashMap<String, BiomeLookup.Island>();
		for (BiomeCatalogue.Entry entry : BiomeCatalogue.entries()) {
			String key = entry.key();
			if (entry.kind() == BiomeCatalogue.Kind.FLOATING && key.indexOf('/', key.indexOf(':') + 1) >= 0) {
				found.put(key, BiomeLookup.island(key, entry.biome(), entry.tags()));
			}
		}
		found = Collections.unmodifiableMap(found);
		if (BiomeCatalogue.isComplete()) {
			islands = found;
		}
		return found;
	}

	static Map<String, Set<String>> tagsByPackageName() {
		if (tagsByPackageName != null) {
			return tagsByPackageName;
		}
		Map<String, Set<String>> found = new LinkedHashMap<String, Set<String>>();
		for (BiomeCatalogue.Entry entry : BiomeCatalogue.entries()) {
			String packageName;
			if (islands().containsKey(entry.key())) {
				packageName = entry.key();
			} else if (entry.biome() != null) {
				packageName = BiomeHelper.getPackageName(entry.biome());
			} else {
				continue;
			}
			Set<String> tags = found.get(packageName);
			if (tags == null) {
				tags = new LinkedHashSet<String>();
				found.put(packageName, tags);
			}
			tags.addAll(entry.tags());
		}
		found = Collections.unmodifiableMap(found);
		if (BiomeCatalogue.isComplete()) {
			tagsByPackageName = found;
		}
		return found;
	}
}
