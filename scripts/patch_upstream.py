#!/usr/bin/env python3
"""Apply reproducible NothingCraft launcher branding, icon and AMOLED theme patches."""
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
    "launcher_version_code": "200050",
    "launcher_version_name": "1.0.5",
}
for key, value in values.items():
    pattern = rf"(?m)^{re.escape(key)}=.*$"
    line = f"{key}={value}"
    props = re.sub(pattern, lambda _: line, props) if re.search(pattern, props) else props + f"\n{line}"
props_path.write_text(props, encoding="utf-8")

gradle_path = root / "ZalithLauncher/build.gradle.kts"
gradle = gradle_path.read_text(encoding="utf-8")
for old, new, label in [
    ("applicationId = zalithPackageName", 'applicationId = "com.f1nozenix.nothingcraft"', "applicationId"),
    ('        applicationIdSuffix = ".v2"', "", "applicationId suffix"),
    ('            signingConfig = signingConfigs.getByName("releaseBuild")',
     '            signingConfig = signingConfigs.getByName("debugBuild")', "release signing"),
]:
    if gradle.count(old) != 1:
        raise SystemExit(f"Expected exactly one upstream {label} anchor; refusing unsafe patch")
    gradle = gradle.replace(old, new, 1)
gradle_path.write_text(gradle, encoding="utf-8")

# A strict AMOLED-black palette with readable foregrounds for white buttons.
settings_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/setting/AllSettings.kt"
settings = settings_path.read_text(encoding="utf-8")
old_default = """        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ColorThemeType.DYNAMIC
        else ColorThemeType.EMBERMIRE"""
if settings.count(old_default) != 1:
    raise SystemExit("Default color theme anchor not found exactly once")
settings_path.write_text(settings.replace(old_default, "        ColorThemeType.URBAN_ASH", 1), encoding="utf-8")

color_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/ui/theme/Color.kt"
colors = color_path.read_text(encoding="utf-8")

def urban_ash_color(name):
    dark = name.endswith("Dark")
    mode = "Dark" if dark else "Light"
    base = name[:-len(mode)]
    if base == "scrim":
        return "Color(0xFF000000)"
    if base == "background" or base == "surface":
        return "Color(0xFF000000)" if dark else "Color(0xFFFFFFFF)"
    if base.startswith("surfaceContainerLowest"):
        return "Color(0xFF000000)" if dark else "Color(0xFFFFFFFF)"
    if base.startswith("surfaceContainerLow"):
        return "Color(0xFF080808)" if dark else "Color(0xFFF7F7F7)"
    if base.startswith("surfaceContainerHigh"):
        return "Color(0xFF171717)" if dark else "Color(0xFFE5E5E5)"
    if base.startswith("surfaceContainerHighest"):
        return "Color(0xFF222222)" if dark else "Color(0xFFDDDDDD)"
    if base.startswith("surfaceContainer"):
        return "Color(0xFF101010)" if dark else "Color(0xFFEEEEEE)"
    if base == "surfaceDim":
        return "Color(0xFF000000)" if dark else "Color(0xFFDDDDDD)"
    if base == "surfaceBright":
        return "Color(0xFF222222)" if dark else "Color(0xFFFFFFFF)"
    if base == "surfaceVariant":
        return "Color(0xFF151515)" if dark else "Color(0xFFEEEEEE)"
    if base.startswith("outline"):
        return "Color(0xFF777777)" if dark else "Color(0xFF666666)"
    if base.startswith("error"):
        return "Color(0xFFFFB4AB)" if dark else "Color(0xFFBA1A1A)"
    if base in ("onPrimary", "onSecondary", "onTertiary"):
        # Primary/secondary/tertiary are white in dark mode and black in light mode.
        return "Color(0xFF000000)" if dark else "Color(0xFFFFFFFF)"
    if base == "onError":
        return "Color(0xFF690005)" if dark else "Color(0xFFFFFFFF)"
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
    # Urban Ash is the sixth palette entry in each ColorTheme constructor.
    parts[5] = urban_ash_color(name)
    return f"val {name} = ColorTheme(" + ", ".join(parts) + ")"

colors, count = re.subn(r"(?m)^val (\w+) = ColorTheme\((.*)\)$", patch_color_line, colors)
if count < 60:
    raise SystemExit(f"Expected many palette entries, patched only {count}; refusing partial theme patch")
color_path.write_text(colors, encoding="utf-8")

# Replace the adaptive launcher icon with a crisp Nothing-inspired dot mark.
res = root / "ZalithLauncher/src/main/res"
foreground = """<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp" android:height="108dp"
    android:viewportWidth="108" android:viewportHeight="108">
    <path android:fillColor="#FFFFFFFF"
        android:pathData="M24,34 a6,6 0,1 0,12 0 a6,6 0,1 0,-12 0
            M48,34 a6,6 0,1 0,12 0 a6,6 0,1 0,-12 0
            M72,34 a6,6 0,1 0,12 0 a6,6 0,1 0,-12 0
            M24,58 a6,6 0,1 0,12 0 a6,6 0,1 0,-12 0
            M48,58 a6,6 0,1 0,12 0 a6,6 0,1 0,-12 0
            M72,58 a6,6 0,1 0,12 0 a6,6 0,1 0,-12 0
            M24,82 a3,3 0,1 0,6 0 a3,3 0,1 0,-6 0
            M51,82 a3,3 0,1 0,6 0 a3,3 0,1 0,-6 0
            M78,82 a3,3 0,1 0,6 0 a3,3 0,1 0,-6 0"/>
</vector>
"""
monochrome = foreground
(res / "drawable/ic_launcher_foreground.xml").write_text(foreground, encoding="utf-8")
(res / "drawable/ic_launcher_monochrome.xml").write_text(monochrome, encoding="utf-8")
(res / "values/ic_launcher_background.xml").write_text(
    '<?xml version="1.0" encoding="utf-8"?>\n<resources>\n    <color name="ic_launcher_background">#FF000000</color>\n</resources>\n',
    encoding="utf-8"
)

# Legal notice remains visible on the home screen.
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
activity_path.write_text(activity.replace(closing, replacement_closing, 1), encoding="utf-8")

# Add launcher-level FSR toggle and pass it to the game at startup.
settings_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/setting/AllSettings.kt"
settings = settings_path.read_text(encoding="utf-8")
anchor = '    val resolutionRatio = intSetting("resolutionRatio", 100, 25..300)'
if settings.count(anchor) != 1:
    raise SystemExit("Resolution setting anchor not found exactly once")
settings = settings.replace(anchor, anchor + '\n\n    /** NothingCraft: FSR 1 spatial upscaling in the compatible Afterburner renderer. */\n    val fsrEnabled = boolSetting("nothingCraftFsrEnabled", false)', 1)
settings_path.write_text(settings, encoding="utf-8")

renderer_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/ui/screens/content/settings/RendererSettingsScreen.kt"
renderer = renderer_path.read_text(encoding="utf-8")
anchor = "                        // 自定义分辨率\n                        AnimatedVisibility("
insert = """                        SwitchSettingsCard(
                            modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                            position = CardPosition.Bottom,
                            unit = AllSettings.fsrEnabled,
                            title = "NothingCraft FSR 1 Upscaling",
                            summary = "Reduces 3D world render resolution and upscales it with EASU + RCAS. Requires the NothingCraft Snapdragon Optimizer mod; menus stay sharp. Restart the game after changing."
                        )

                        // 自定义分辨率
                        AnimatedVisibility("""
if renderer.count(anchor) != 1:
    raise SystemExit("Renderer settings insertion anchor not found exactly once")
renderer_path.write_text(renderer.replace(anchor, insert, 1), encoding="utf-8")

launcher_path = root / "ZalithLauncher/src/main/java/com/movtery/zalithlauncher/game/launch/GameLauncher.kt"
launcher = launcher_path.read_text(encoding="utf-8")
old = '        val customArgs = version.getJvmArgs().takeIf { it.isNotBlank() } ?: AllSettings.jvmArgs.getValue()'
new = '''        val baseCustomArgs = version.getJvmArgs().takeIf { it.isNotBlank() } ?: AllSettings.jvmArgs.getValue()
        val withoutFsrOverride = baseCustomArgs.replace(Regex("""(?:^|\\s)-Dnothingcraft\\.fsr=\\S+"""), " ").trim()
        val customArgs = (withoutFsrOverride + " -Dnothingcraft.fsr=" +
            if (AllSettings.fsrEnabled.getValue()) "quality" else "off").trim()'''
if launcher.count(old) != 1:
    raise SystemExit("GameLauncher JVM args anchor not found exactly once")
launcher_path.write_text(launcher.replace(old, new, 1), encoding="utf-8")

print("Patched NothingCraft 1.0.5: AMOLED theme, icon, FSR launcher toggle.")
