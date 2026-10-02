# MIDPlay

![AppIcon](/res/Icon.png)

A demo music player for J2ME (Java ME) mobile devices — CLDC 1.1 / MIDP 2.0. Ships with mock sample data; no live streaming.

## Features

- **Mock/demo playback** — bundled sample data, no live streaming
- **Discovery** — browse by category and playlist, search songs / artists / albums
- **Playback** — seek, resume position, sleep timer
- **Volume** — in-app volume bar (D-pad / touch / menu), plus hardware volume-rocker support where the device forwards the keys (experimental, device-dependent)
- **Library** — favorites, playlists, recent history
- **Localization** — English, Vietnamese, Turkish, Polish, Hebrew

## Requirements

- J2ME device supporting MIDP 2.0 / CLDC 1.1

## Install

1. Download the latest `.jar` from the [Releases](https://github.com/RainStrom-3AM/MIDPlay/releases) page
2. Install on a J2ME-compatible device (or load in an emulator such as KEmulator)

## Build

macOS / Linux, or Windows via Git Bash:

```bash
./build.sh
```

Requires **JDK 8** (the last toolchain emitting CLDC-compatible bytecode). On Windows install it once with winget, then run `./build.sh` from Git Bash:

```bash
winget install EclipseAdoptium.Temurin.8.JDK
```

Output: `dist/MIDPlay.jar` + `dist/MIDPlay.jad`. See `build.sh` for the full pipeline (compile → package → ProGuard → JAD).

## Tech Stack

- Java ME (J2ME), MIDP 2.0 / CLDC 1.1
- Record Management System (RMS) for local storage
- Mock data layer (no external services)

## Contributing

**Code:** fork → feature branch → commit → push → open a Pull Request.

**Language:** duplicate `langs/en.json`, translate, and submit via PR or an `[Enhancement]` issue.

## License

MIT — see [LICENSE](LICENSE).
