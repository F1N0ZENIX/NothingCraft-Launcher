#!/usr/bin/env python3
"""Apply reproducible NothingCraft branding/runtime patches to pinned ZalithLauncher2 source.

Upstream source is fetched at the exact commit recorded in scripts/UPSTREAM_COMMIT.txt.
The upstream license and copyright headers are retained.
"""
from pathlib import Path
import re
import sys

if len(sys.argv) != 2:
    raise SystemExit("usage: patch_upstream.py <upstream-checkout>")

root = Path(sys.argv[1])
props_path = root / "ZalithLauncher/gradle.properties"
props = props_path.read_text(encoding="utf-8")

values = {
    "launcher_name": "NothingCraft",
    "launcher_app_name": "NothingCraft Launcher",
    "launcher_short_name": "NC",
    "url_home": "https://github.com/F1N0ZENIX/NothingCraft-Launcher",
    "launcher_version_code": "200047",
    "launcher_version_name": "1.0.2",
}
for key, value in values.items():
    pattern = rf"(?m)^{re.escape(key)}=.*$"
    line = f"{key}={value}"
    if re.search(pattern, props):
        props = re.sub(pattern, lambda _: line, props)
    else:
        props += f"\n{line}"
props_path.write_text(props, encoding="utf-8")

gradle_path = root / "ZalithLauncher/build.gradle.kts"
gradle = gradle_path.read_text(encoding="utf-8")
old_app_id = "applicationId = zalithPackageName"
if gradle.count(old_app_id) != 1:
    raise SystemExit("Expected exactly one upstream applicationId declaration; refusing unsafe patch")
gradle = gradle.replace(old_app_id, 'applicationId = "com.f1nozenix.nothingcraft"', 1)

# The upstream launcher uses a .v2 suffix. Remove only the defaultConfig suffix so
# the branded build has a stable package ID. Keep the debug-only .debug suffix.
old_suffix = '        applicationIdSuffix = ".v2"'
if gradle.count(old_suffix) != 1:
    raise SystemExit("Expected upstream .v2 application suffix; refusing unsafe patch")
gradle = gradle.replace(old_suffix, "", 1)

# Use the upstream's committed debug signing key for reproducible community/test
# builds. This key is public and must not be treated as a secure production key.
old_signing = '            signingConfig = signingConfigs.getByName("releaseBuild")'
if gradle.count(old_signing) != 1:
    raise SystemExit("Expected exactly one release signing config; refusing unsafe patch")
gradle = gradle.replace(
    old_signing,
    '            signingConfig = signingConfigs.getByName("debugBuild")',
    1,
)
gradle_path.write_text(gradle, encoding="utf-8")

# Add an explicit GPL-required notice to the actual launcher screen. Keep upstream
# copyright headers and the rest of the UI/launch engine intact.
activity_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/ui/activities/MainActivity.kt"
activity = activity_path.read_text(encoding="utf-8")
imports = [
    "import androidx.compose.foundation.background",
    "import androidx.compose.foundation.layout.Box",
    "import androidx.compose.foundation.layout.padding",
    "import androidx.compose.foundation.shape.RoundedCornerShape",
    "import androidx.compose.material3.Text",
    "import androidx.compose.ui.Alignment",
    "import androidx.compose.ui.graphics.Color",
    "import androidx.compose.ui.unit.dp",
    "import androidx.compose.ui.unit.sp",
]
package_line = "package com.movtery.zalithlauncher.ui.activities"
if package_line not in activity:
    raise SystemExit("MainActivity package declaration not found")
missing = [item for item in imports if item not in activity]
activity = activity.replace(package_line, package_line + "\n\n" + "\n".join(missing), 1)

opening = """                GuideHost(
                    guides.mainScreen,
                    nextTip = { NextTipLabel(it) }
                ) {
                    Background("""
replacement = """                GuideHost(
                    guides.mainScreen,
                    nextTip = { NextTipLabel(it) }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                    Background("""
if activity.count(opening) != 1:
    raise SystemExit("Main screen container anchor not found exactly once")
activity = activity.replace(opening, replacement, 1)

closing = """                    }
                }

                //显示赞助支持的小弹窗"""
replacement_closing = """                    }

                    Text(
                        text = "NOTHINGCRAFT • UNOFFICIAL MODIFIED VERSION",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp)
                            .background(Color.Black.copy(alpha = 0.86f), RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White,
                        fontSize = 9.sp,
                        maxLines = 1
                    )
                    }
                }

                //显示赞助支持的小弹窗"""
if activity.count(closing) != 1:
    raise SystemExit("Main screen closing anchor not found exactly once")
activity = activity.replace(closing, replacement_closing, 1)
activity_path.write_text(activity, encoding="utf-8")

print("Patched pinned ZalithLauncher2 source for NothingCraft 1.0.2 (arm64 release).")
