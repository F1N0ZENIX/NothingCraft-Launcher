#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
UPSTREAM_DIR="$ROOT/.work/afterburner-optimized"
UPSTREAM_URL="https://github.com/HamdanProfessional/afterburner-optimized.git"
UPSTREAM_COMMIT="$(tr -d '[:space:]' < "$ROOT/scripts/AFTERBURNER_COMMIT.txt")"

mkdir -p "$ROOT/.work"
rm -rf "$UPSTREAM_DIR"
git clone --filter=blob:none --no-checkout "$UPSTREAM_URL" "$UPSTREAM_DIR"
git -C "$UPSTREAM_DIR" checkout --detach "$UPSTREAM_COMMIT"

python3 - "$UPSTREAM_DIR" <<'PY'
from pathlib import Path
import json
import re
import sys

root = Path(sys.argv[1])
props = root / "gradle.properties"
s = props.read_text(encoding="utf-8")
s, n = re.subn(r"(?m)^version=.*$", "version=1.0.1-nc2", s)
if n != 1:
    raise SystemExit("Could not set Snapdragon mod version")
props.write_text(s, encoding="utf-8")

metadata = [
    (root / "src/main/resources/fabric.mod.json",
     "NothingCraft Snapdragon Optimizer",
     "Performance optimizations and AMD FidelityFX Super Resolution 1 for Minecraft Java 26.3; tuned defaults for Android phones."),
    (root / "src/labs/resources/fabric.mod.json",
     "NothingCraft FSR Labs",
     "Experimental shader pipeline and AMD FSR 1 EASU+RCAS upscaling. GPU/renderer compatibility varies on Android.")
]
for path, name, description in metadata:
    data = json.loads(path.read_text(encoding="utf-8"))
    data["name"] = name
    data["description"] = description
    data["authors"] = ["F1N0ZENIX (NothingCraft fork)", "Hamdan (Afterburner upstream)"]
    path.write_text(json.dumps(data, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")

# The launcher controls FSR via -Dnothingcraft.fsr. The shader implementation stays
# in the mod so EASU + RCAS run in the correct GPU render-graph location.
world_scale = root / "src/labs/java/com/afterburner/labs/render/WorldScale.java"
source = world_scale.read_text(encoding="utf-8")
old_scale = "float scale = Shaderpacks.upscale().scale;"
new_scale = """String preset = System.getProperty("nothingcraft.fsr", "off").trim().toLowerCase(java.util.Locale.ROOT);
		float scale = switch (preset) {
			case "ultra_quality" -> 0.77F;
			case "quality" -> 0.67F;
			case "balanced" -> 0.59F;
			case "performance" -> 0.50F;
			default -> 1.0F;
		};"""
if source.count(old_scale) != 1:
    raise SystemExit("Could not locate FSR world-scale anchor")
world_scale.write_text(source.replace(old_scale, new_scale, 1), encoding="utf-8")
print("Prepared NothingCraft Snapdragon Optimizer with launcher-controlled FSR 1 and upstream worldgen/memory optimizations.")
PY

cd "$UPSTREAM_DIR"
chmod +x gradlew
./gradlew --no-daemon clean build
JAR="$(find build/libs -maxdepth 1 -type f -name '*.jar' ! -name '*sources*' ! -name '*javadoc*' -print | head -n 1)"
if [ -z "$JAR" ]; then
  echo "Fabric mod JAR not found" >&2
  find build -maxdepth 3 -type f -name '*.jar' -print >&2
  exit 1
fi
mkdir -p "$ROOT/dist"
cp "$JAR" "$ROOT/dist/NothingCraft-Snapdragon-Optimizer-1.0.1.jar"
printf 'MOD=%s\n' "$ROOT/dist/NothingCraft-Snapdragon-Optimizer-1.0.1.jar"
