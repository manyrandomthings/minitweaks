# Mini Tweaks
Some small additions to carpet mod

Requires [fabric-carpet](https://github.com/gnembon/fabric-carpet)

# Mini Tweaks Settings
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
  
## disableGhastFire
Disable random fire from ghast fireballs  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `minitweaks`, `mobs`, `survival`  
  
## noFeatherFallingTrample
Prevents farmland from being trampled if you have feather falling on  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `minitweaks`, `survival`  
  
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
  
## phantomSpawningTime
Amount of ticks before Phantoms start having a chance to spawn  
* Type: `int`  
* Default value: `72000`  
* Suggested options: `72000`, `360000`, `720000`  
* Categories: `minitweaks`, `mobs`, `survival`  
* Additional notes:  
  * Must be a positive number  
  
## renewableDragonEgg
Dragon eggs will always be placed on the portal after defeating the dragon  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `minitweaks`, `mobs`, `survival`  
  
## slimeLooting
Bigger slimes spawn more smaller slimes when killed with looting  
Additional slimes can be up to as many levels of looting as you have (up to +3 with looting 3, etc)  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `minitweaks`, `mobs`, `survival`  
