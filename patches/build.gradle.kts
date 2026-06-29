group = "app.revanced"

patches {
    about {
        name = "Moovit Patches"
        description = "ReVanced patches for Moovit: remove ads and unlock Moovit+"
        author = "ReVanced"
        license = "GNU General Public License v3.0"
        source = "https://github.com/loan-mgt/revanced-patches-moovit"
        contact = "qypol342@gmail.com"
        website = "https://github.com/loan-mgt"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
        )
    }
}
