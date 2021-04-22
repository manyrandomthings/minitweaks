# MiniTweaks
Some small game mechanic changes, mainly for survival

Requires [fabric-carpet](https://github.com/gnembon/fabric-carpet)

# MiniTweaks Settings
## creeperBlockDamage
Set creeper explosion block damage type, regardless of mobGriefing gamerule  
default: uses default explosion  
none: no blocks broken  
break: all broken blocks are dropped (like tnt)  
destroy: broken blocks are sometimes dropped (like default creepers)  
* Type: `String`
* Default value: `default`
* Required options: `default`, `none`, `break`, `destroy`
* Categories: `minitweaks`, `mobs`, `survival`

## deathItemsDespawnMinutes
How many minutes it takes for a player's items to despawn after death  
-1 for infinte, 0 for instant despawn  
* Type: `int`
* Default value: `5`
* Suggested options: `5`, `10`, `15`, `30`, `-1`
* Categories: `minitweaks`, `survival`
* Additional notes:
  * You must choose a value from -1 (infinite) to 32

## disableBlazeFire
Disable fires made from blaze fireballs  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`

## disableGhastFire
Disable random fire from ghast fireballs  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`

## dispensersCureVillagers
Dispensers feed golden apples to zombie villagers with weakness  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `dispenser`, `survival`
* Additional notes:
  * Dispensers curing a villager does not lower the villager's prices due to gossips being player-specific
## dispensersDyeMobs
Dispensers can dye sheep (and shulkers if dyeableShulkers is enabled)  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `dispenser`

## dispensersNameMobs
Dispensers use name tags on mobs  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `dispenser`

## dispensersRepairGolems
Dispensers can repair Iron Golems with iron ingots  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `dispenser`

## dispensersUseCauldrons
Dispensers use cauldrons  
When facing into a cauldron, dispensers can fill/empty buckets and bottles,  
remove layers from banners, and undye leather armor or shulker boxes  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `survival`, `dispenser`

## dragonBlockDamage
Set dragon block damage breaking type, regardless of mobGriefing gamerule  
default: default block breaking  
none: no blocks are broken  
break: broken blocks are dropped  
destroy: broken blocks are destroyed and not dropped  
* Type: `String`
* Default value: `default`
* Required options: `default`, `none`, `break`, `destroy`
* Categories: `minitweaks`, `mobs`, `survival`

## dyeableShulkers
Shulkers can be dyed  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`

## ghastBlockDamage
Set ghast explosion block damage type, regardless of mobGriefing gamerule  
default: uses default explosion  
none: no blocks broken  
break: all broken blocks are dropped (like tnt)  
destroy: broken blocks are sometimes dropped (like default creepers)  
* Type: `String`
* Default value: `default`
* Required options: `default`, `none`, `break`, `destroy`
* Categories: `minitweaks`, `mobs`, `survival`

## infinityMendingStacking
Allows infinity and mending to stack on bows  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `enchantment`, `survival`
* Additional notes:
  * Like enchanting from 1.9-1.11

## maxPlayerXpDrop
Maximum amount of xp players drop on death  
* Type: `int`
* Default value: `100`
* Suggested options: `0`, `100`, `1000`, `10000`
* Categories: `minitweaks`, `survival`
* Additional notes:
  * Must be a positive number

## mobsDropNametag
Named mobs drop their name tag on death  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`

## morePaveableBlocks
More dirt-like blocks can be made into path blocks (from 1.17)  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `survival`, `backport`

## moveableWaterloggedBlocks
Waterlogged blocks stay waterlogged when moved with a piston  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `feature`
* Additional notes:
  * Water in moving waterlogged blocks doesn't render correctly

## noFeatherFallingTrample
Prevents farmland from being trampled if you have feather falling on  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `survival`

## noRepairCost
No additional cost for using an item in an anvil multiple times  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `survival`

## phantomSpawningTime
Amount of ticks before Phantoms start having a chance to spawn  
* Type: `int`
* Default value: `72000`
* Suggested options: `72000`, `360000`, `720000`
* Categories: `minitweaks`, `mobs`, `survival`
* Additional notes:
  * Must be a positive number

## protectionStacking
Allows all the different protection types to stack on the same piece of armor  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `enchantment`, `survival`
* Additional notes:
  * Like enchanting from 1.14-1.14.2

## quickHarvesting
Right click crops with a hoe to harvest and replant  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `survival`, `feature`

## renewableDragonEgg
Dragon eggs will always be placed on the portal after defeating the dragon  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`

## shaveSnowLayers
Snow layers can be shaved, removing one layer when right clicked with a shovel  
Works with silk touch as well  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `survival`, `feature`

## shulkerCloning
1.17 Shulker cloning  
"A shulker hitting a shulker with a shulker bullet can make a new shulker"  
Feature from 20w45a, subject to change  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`, `backport`

## shulkerPortalFix
Shulker portal teleportation fix  
Fixes [MC-139265](https://bugs.mojang.com/browse/MC-139265) / [MC-168900](https://bugs.mojang.com/browse/MC-168900), which makes shulkers use portals correctly  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`, `bugfix`

## slimeLooting
Bigger slimes spawn more smaller slimes when killed with looting  
Additional slimes can be up to as many levels of looting as you have (up to +3 with looting 3, etc)  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`

## vexesNerf
Vexes will start to die after the evoker that summoned them dies  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`, `survival`

## villagersExplodeBeds
Villagers cause explosions when trying to use beds in the nether or end, like players  
"But why?" Idk, it's just a funny idea I had  
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `minitweaks`, `mobs`
