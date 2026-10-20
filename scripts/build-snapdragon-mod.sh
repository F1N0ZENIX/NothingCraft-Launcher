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
s, n = re.subn(r"(?m)^version=.*$", "version=1.0.0-nc1", s)
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

# FSR 1 remains available in the mod's native in-game settings; don't force it on
# before the Android renderer has been tested on the target device.
print("Prepared NothingCraft Snapdragon Optimizer metadata; upstream mobile defaults and FSR 1 retained.")
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
cp "$JAR" "$ROOT/dist/NothingCraft-Snapdragon-Optimizer-1.0.0.jar"
printf 'MOD=%s\n' "$ROOT/dist/NothingCraft-Snapdragon-Optimizer-1.0.0.jar"
