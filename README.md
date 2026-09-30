# delay-sleep

A Fabric mod that delays the earliest time you can sleep in a bed by default. It can also be configured to allow sleeping earlier instead.

## Requirements

- Minecraft 26.3
- [Fabric Loader](https://fabricmc.net/use/installer) 0.19.5+
- [Fabric API](https://modrinth.com/mod/fabric-api)
- Java 25+

## Configuration

The `config/delay-sleep.json` file is created on first launch:

```json
{
  "minTickClear": 16000,
  "minTickRain": 13000
}
```

| Key            | Applies when         | Default | Vanilla |
| -------------- | -------------------- | ------- | ------- |
| `minTickClear` | Clear weather        | 16000   | 12542   |
| `minTickRain`  | Rain or thunderstorm | 13000   | 12010   |

Values range from `0` to `23999` (one full day) and are given in ticks since the
start of the day cycle (tick `0` = 6:00 AM). By default, `minTickClear` is
~10:00 PM and `minTickRain` ~7:00 PM.

Setting a value **higher** than the vanilla tick delays sleep, while a value
**lower** than the vanilla tick lets you sleep earlier.

> If you already have a `config/delay-sleep.json` from an older version, your saved values still take priority.
> Delete that file to pick up the defaults above.

## Building

```sh
./gradlew build
```

The resulting `.jar` is in `build/libs/`.

## License

[MIT](LICENSE)
