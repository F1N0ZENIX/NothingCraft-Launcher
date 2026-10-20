#!/usr/bin/env python3
"""Apply reproducible NothingCraft branding/theme patches to pinned ZalithLauncher2 source."""
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
    "launcher_version_code": "200048",
    "launcher_version_name": "1.0.3",
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
old_suffix = '        applicationIdSuffix = ".v2"'
if gradle.count(old_suffix) != 1:
    raise SystemExit("Expected upstream .v2 application suffix; refusing unsafe patch")
gradle = gradle.replace(old_suffix, "", 1)
old_signing = '            signingConfig = signingConfigs.getByName("releaseBuild")'
if gradle.count(old_signing) != 1:
    raise SystemExit("Expected exactly one release signing config; refusing unsafe patch")
gradle = gradle.replace(old_signing, '            signingConfig = signingConfigs.getByName("debugBuild")', 1)
gradle_path.write_text(gradle, encoding="utf-8")

# Default to a strict black/white AMOLED palette on fresh installs.
settings_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/setting/AllSettings.kt"
settings = settings_path.read_text(encoding="utf-8")
old_default = """        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ColorThemeType.DYNAMIC
        else ColorThemeType.EMBERMIRE"""
if settings.count(old_default) != 1:
    raise SystemExit("Default color theme anchor not found exactly once")
settings = settings.replace(old_default, "        ColorThemeType.URBAN_ASH", 1)
settings_path.write_text(settings, encoding="utf-8")

# Replace only the Urban Ash (sixth) swatch in each ColorTheme entry; other palettes remain intact.
color_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/ui/theme/Color.kt"
colors = color_path.read_text(encoding="utf-8")

def urban_ash_color(name):
    dark = name.endswith("Dark")
    mode = "Dark" if dark else "Light"
    base = name[:-len(mode)]
    if base == "scrim":
        return "Color(0xFF000000)"
    if base.startswith("outline"):
        return "Color(0xFF777777)" if base == "outline" else "Color(0xFF333333)"
    if base.startswith("error"):
        return "Color(0xFFFFB4AB)" if dark else "Color(0xFFBA1A1A)"
    if base.startswith("onError"):
        return "Color(0xFF690005)" if dark else "Color(0xFFFFFFFF)"
    if base == "background" or base == "surface":
        return "Color(0xFF000000)" if dark else "Color(0xFFFFFFFF)"
    if base.startswith("surfaceContainerLowest"):
        return "Color(0xFF050505)" if dark else "Color(0xFFFFFFFF)"
    if base.startswith("surfaceContainerLow"):
        return "Color(0xFF0A0A0A)" if dark else "Color(0xFFF7F7F7)"
    if base.startswith("surfaceContainerHigh"):
        return "Color(0xFF1A1A1A)" if dark else "Color(0xFFE5E5E5)"
    if base.startswith("surfaceContainerHighest"):
        return "Color(0xFF242424)" if dark else "Color(0xFFDDDDDD)"
    if base.startswith("surfaceContainer"):
        return "Color(0xFF111111)" if dark else "Color(0xFFEEEEEE)"
    if base == "surfaceDim":
        return "Color(0xFF000000)" if dark else "Color(0xFFDDDDDD)"
    if base == "surfaceBright":
        return "Color(0xFF242424)" if dark else "Color(0xFFFFFFFF)"
    if base == "surfaceVariant":
        return "Color(0xFF171717)" if dark else "Color(0xFFEEEEEE)"
    if base.startswith("on"):
        return "Color(0xFFFFFFFF)" if dark else "Color(0xFF000000)"
    if base in ("primary", "secondary", "tertiary"):
        return "Color(0xFFFFFFFF)" if dark else "Color(0xFF000000)"
    if base.endswith("Container"):
        return "Color(0xFF202020)" if dark else "Color(0xFFE5E5E5)"
    if base == "inverseSurface":
        return "Color(0xFFF5F5F5)" if dark else "Color(0xFF111111)"
    if base == "inversePrimary":
        return "Color(0xFFBDBDBD)" if dark else "Color(0xFF333333)"
    return "Color(0xFFBDBDBD)" if dark else "Color(0xFF333333)"

def patch_color_line(match):
    name, args = match.group(1), match.group(2)
    parts = args.split(", ")
    if len(parts) != 7:
        raise SystemExit(f"Unexpected ColorTheme argument count in {name}: {len(parts)}")
    parts[5] = urban_ash_color(name)
    return f"val {name} = ColorTheme(" + ", ".join(parts) + ")"

colors, count = re.subn(r"(?m)^val (\w+) = ColorTheme\((.*)\)$", patch_color_line, colors)
if count < 60:
    raise SystemExit(f"Expected many palette entries, patched only {count}; refusing partial theme patch")
color_path.write_text(colors, encoding="utf-8")

# Add a visible legal notice and the Nothing-style dot signature to the main screen.
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
                        text = "● ○ ●  NOTHINGCRAFT • UNOFFICIAL MODIFIED VERSION",
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

print("Patched NothingCraft 1.0.3: AMOLED monochrome theme, Nothing-style dots, arm64 launcher.")
