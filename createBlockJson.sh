#!/bin/sh

MODID=meliusscientia
[ "$1" = "" ] && echo "Please provide the block name as argument" && exit 1
BLOCKNAME=$1
DIRNAME="$(dirname "$(readlink -f "$0")")"
cd "$DIRNAME"/src/main/resources/ || (echo "mod source directory not found" && exit 2)

echo "{
	\"variants\": {
		\"\": {
			\"model\": \"$MODID:block/$BLOCKNAME\"
		}
	}
}" > ./assets/$MODID/blockstates/$BLOCKNAME.json

echo "{
  \"parent\": \"block/cube_all\",
  \"textures\": {
    \"all\": \"$MODID:block/$BLOCKNAME\"
  }
}" > ./assets/$MODID/models/block/$BLOCKNAME.json

echo "{
	\"parent\": \"$MODID:block/$BLOCKNAME\"
}" > ./assets/$MODID/models/item/$BLOCKNAME.json

echo "{
	\"type\": \"minecraft:block\",
	\"pools\": [
		{
			\"rolls\": 1,
			\"entries\": [
				{
					\"type\": \"minecraft:item\",
					\"name\": \"$MODID:$BLOCKNAME\"
				}
			],
			\"conditions\": [
				{
					\"condition\": \"minecraft:survives_explosion\"
				}
			]
		}
	]
}" > ./data/$MODID/loot_tables/blocks/$BLOCKNAME.json