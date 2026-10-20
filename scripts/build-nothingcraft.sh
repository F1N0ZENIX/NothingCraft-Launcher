#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
UPSTREAM_DIR="$ROOT/.work/ZalithLauncher2"
UPSTREAM_URL="https://github.com/ZalithLauncher/ZalithLauncher2.git"
UPSTREAM_COMMIT="$(tr -d '[:space:]' < "$ROOT/scripts/UPSTREAM_COMMIT.txt")"

mkdir -p "$ROOT/.work"
rm -rf "$UPSTREAM_DIR"
git clone --filter=blob:none --no-checkout "$UPSTREAM_URL" "$UPSTREAM_DIR"
git -C "$UPSTREAM_DIR" checkout --detach "$UPSTREAM_COMMIT"
git -C "$UPSTREAM_DIR" submodule update --init --recursive

python3 "$ROOT/scripts/patch_upstream.py" "$UPSTREAM_DIR"

cd "$UPSTREAM_DIR"
chmod +x gradlew
./gradlew --no-daemon :ZalithLauncher:assembleRelease -Darch=arm64

APK="$(find ZalithLauncher/build/outputs/apk/release -maxdepth 1 -type f -name '*.apk' -print -quit)"
if [ -z "$APK" ]; then
  echo "Release APK not found" >&2
  exit 1
fi
mkdir -p "$ROOT/dist"
cp "$APK" "$ROOT/dist/NothingCraft Launcher 1.0.4.apk"
"$ANDROID_HOME/build-tools/36.0.0/apksigner" verify "$ROOT/dist/NothingCraft Launcher 1.0.4.apk" || true
printf 'APK=%s\n' "$ROOT/dist/NothingCraft Launcher 1.0.4.apk"
