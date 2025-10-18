# Flourishing Fields
A Minecraft fabric mod about flowers and bees.

## Bee Behaviour
When a bee collects nectar from a flower, there’s a small chance that the same type of flower will spawn on a block the bee flies over while returning to its beehive.
## Flower Behaviour
- **Flower Seeds:** all flowers now have their own seeds item
- **Planting:** seeds go on any block vanilla flowers accept
- **Flower Breakage**
  - With *Hand*: small chance to drop *1-2* seeds
  - With *Shears*: always drops the flower
- **Crop Breakage**
  - Immature: drops *one* seeds item
  - Mature with *Hand*: drops seeds
  - Mature with *Shears*: drops seeds & flower

## Optional TOML Customization
### File path: config/flourishing_fields.toml
```toml
# This (#) is a comment

# The chance of bees spreading a flower (0-100)
spread_chance = 5

# If 'true', bees will spread flowers indefinitely
unlimited_spread = false

# Flowers listed will not be spread by bees
spread_blacklist = [ # 
    "minecraft:poppy",
    "minecraft:rose_bush"
]
```

## Download
- [Modrinth](https://modrinth.com/mod/flourishing-fields/versions)
- [CurseForge](https://www.curseforge.com/members/q4niel/projects)
- [GitHub](https://github.com/q4niel/Flourishing-Fields/releases)

## **Credits**
- **Art Assets** created by [MaxWesterlund](https://github.com/MaxWesterlund).

## Feedback
Found a bug or got some suggestions, head over to [GitHub Issues](https://github.com/q4niel/Flourishing-Fields/issues).