import os
import json
from typing import List

def modID() -> str: return "flourishing_fields"
def vanillaID() -> str: return "minecraft"

def blockOfMod() -> str: return modID() + ":block/"
def blockOfVanilla() -> str: return vanillaID() + ":block/"

def blockstatesDir() -> str: return "generated/assets/" + modID() + "/blockstates"
def blockModelsDir() -> str: return "generated/assets/" + modID() + "/models/block"

def dumpFlowerBlockstate(flower: str) -> None:
    with open(blockstatesDir() + "/" + flower + ".json", "w") as file:
        json.dump({
            "variants": {
                "age=0": {"model": blockOfMod() + "flower_sprout"},
                "age=1": {"model": blockOfVanilla() + flower}
            }
        }, file, indent=2)
    return

def dumpTallFlowerBottomBlockstate(flower: str) -> None:
    with open(blockstatesDir() + "/" + flower + "_bottom.json", "w") as file:
        json.dump({
            "variants": {
                "age=0": {"model": blockOfMod() + "flower_sprout"},
                "age=1": {"model": blockOfMod() + flower + "_bottom_middle"},
                "age=2": {"model": blockOfVanilla() + flower + "_bottom"}
            }
        }, file, indent=2)
    return

def dumpTallFlowerTopBlockstate(flower: str) -> None:
    with open(blockstatesDir() + "/" + flower + "_top.json", "w") as file:
        json.dump({
            "variants": {
                "": {"model": blockOfVanilla() + flower + "_top"}
            }
        }, file, indent=2)
    return

def dumpFlowerModel(flower: str) -> None:
    with open(blockModelsDir() + "/" + flower + ".json", "w") as file:
        json.dump({
            "parent": vanillaID() + ":block/cross",
            "textures": {
                "cross": modID() + ":block/" + flower
            }
        }, file, indent=2)
    return

def shortFlowers() -> List[str]: return [
    "dandelion",
    "poppy",
    "blue_orchid",
    "allium",
    "azure_bluet",
    "red_tulip",
    "orange_tulip",
    "white_tulip",
    "pink_tulip",
    "oxeye_daisy",
    "cornflower",
    "lily_of_the_valley",
    "wither_rose"
]

def tallFlowers() -> List[str]: return [
    "sunflower",
    "lilac",
    "rose_bush",
    "peony"
]

def main() -> None:
    os.chdir(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

    if not os.path.exists(blockstatesDir()):
        os.makedirs(blockstatesDir())

    if not os.path.exists(blockModelsDir()):
        os.makedirs(blockModelsDir())

    dumpFlowerModel("flower_sprout")

    for f in shortFlowers():
        dumpFlowerBlockstate(f)

    for f in tallFlowers():
        dumpTallFlowerBottomBlockstate(f)
        dumpFlowerModel(f + "_bottom_middle")
        dumpTallFlowerTopBlockstate(f)

    return

if __name__ == "__main__": main()