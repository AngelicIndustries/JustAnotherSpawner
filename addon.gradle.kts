// Project-specific Gradle customizations belong here.

// modGroup has to be upstream's jas package; publish under the Angelic group instead.
group = "industries.angelic.justanotherspawner"

// Ship the README (license notice) in the jar, as the old build did.
tasks.named<ProcessResources>("processResources") {
    from("README.md")
}
