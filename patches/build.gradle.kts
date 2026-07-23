group = "app.revanced"

patches {
    about {
        name = "Moovit Patches"
        description = "ReVanced patches for Moovit: remove ads, unlock Moovit+, and add GmsCore support"
        author = "ReVanced"
        license = "GNU General Public License v3.0"
        source = "https://github.com/loan-mgt/revanced-patches-moovit"
        contact = "qypol342@gmail.com"
        website = "https://github.com/loan-mgt"
    }
}

dependencies {
    implementation(files("../revanced-patches/patches/build/libs/patches-6.1.1-dev.4.jar"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
        )
    }
}
