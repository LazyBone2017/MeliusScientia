# TODOs

## Radiation Poisoning
- first no visible effects, just "radiation"
- multiple levels of radiation, depending on contamination
  - level 1: when in nuked_biome
  - level 2: when in contaminated water or upgrade
  - level 3: when ingested contaminated material
- basic concept: for every level, there's a specific probability to get a specific set of effects
- effects activate after certain amount of time
### Levels
- level 1: 
  - 5 - 50% nausea, duration: 1 min, latency: 20 min, fatigue/weakness 2 min, damage: low
  - 10% poisoning
- level 2:
  - 50 - 100% nausea, duration: 3 min, latency: 10 - 20 min, fatigue/weakness 5 min, damage: moderate
  - 50% poisoning, (blood loss)
- level 3: 
  - 100% nausea, duration: 5 min, latency: 5 - 10 min, fatigue/weakness/nausea 7 min, damage: high
  - 100% poisoning, (blood loss)
  - walking ghost phase: duration 3 min, random positive effects, after that damage til death
### more info
- when player is in nuked_biome after 10 min of level 1, level is being upgraded
  - timer reset
- radiation effect is only applied when player has no radiation effect