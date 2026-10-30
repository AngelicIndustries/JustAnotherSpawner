Adventura Arcana fork

* Spawn lists are chosen by the biome at the spawn position, not the column. With Biomic (API version 2) that is its
  3D biome, and each sky island identity that shares its biome with the ground (biomic:floating/..., biomic:aeropelago/...,
  biomic:hosted/...) is a biome of its own: its own mapping and group, with default spawns from the biome it hosts.
  Biomic's tags are attributes, rebuilt each load: "A|biomic:FLOATING_ISLAND" holds every island. MVEL tags get
  wrld.biomeAt(x, y, z), wrld.biomicKeyAt(x, y, z) and wrld.biomicTagsAt(x, y, z).
* Works with EndlessIDs biome IDs; converted max-height tags no longer stop at 256.
* CosmicDan's spawning events and IS_JAS_SPAWNED tag (off by default, in GlobalProperties).
* Build with ./gradlew build, or VERSION=<version> ./gradlew build until there is a git tag. Biomic comes from Maven Local.

Just Another Spawner (JAS) is a Minecraft Mod that aimed at providing an alternative entity spawning system for Minecraft.

License / Legal Stuff
Copyright 2012 Tarion
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


Contains MVEL code, Codehaus licensed under the Apache 2.0 Licenses