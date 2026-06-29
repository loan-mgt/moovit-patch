# Moovit Patches

![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)

ReVanced patches for the [Moovit](https://moovitapp.com) transit app.

## Patches

| Patch | Description |
|---|---|
| **Remove ads** | Removes all ads — banners, interstitials, and map overlay ads. |
| **Unlock Moovit+** | Unlocks Moovit+ client-side features and skips the upgrade interstitials. |

## Supported versions

`com.tranzmate` — `5.194.0.1785`

## Building

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export GITHUB_ACTOR=your-gh-username
export GITHUB_TOKEN=ghp_xxx   # classic token with read:packages scope
./gradlew build
```

Output: `patches/build/libs/patches-*.jar`

## License

GPLv3. See [LICENSE](LICENSE).
