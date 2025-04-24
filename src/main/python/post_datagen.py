import os
from typing import List, Callable

def getDirectory() -> str:
    return "generated/assets/flourishing_fields/models/block"

def readFile(file: str) -> str:
    with open(os.path.join(getDirectory(), file)) as f:
        return f.read()

def writeFile(file: str, data: str) -> None:
    with open(os.path.join(getDirectory(), file), "r+") as file:
        file.write(data)
        file.truncate()
    return

def replaceInFile(file: str, old: str, new: str) -> None:
    writeFile(file, readFile(file).replace(old, new))

def ofVanilla(name: str) -> str:
    for flower in [
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
        "wither_rose",
        "sunflower",
        "lilac",
        "rose_bush",
        "peony"
    ]:
        if flower in name:
            return "minecraft:block/" + flower
    return ""

def main() -> None:
    os.chdir(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))
    modId: str = "flourishing_fields:block/"
    tallMiddleStages: List[str] = []

    for name in os.listdir(getDirectory()):
        replaceInFile(name, "crop", "cross")
        replaceInFile(name, "cube_all", "cross")
        replaceInFile(name, "all", "cross")

        if "_stage0" in name:
            replaceInFile(name, name[:-5], "flower_sprout")

        if "_upper" in name:
            replaceInFile(name, modId + name[:-5], ofVanilla(name) + "_top")

        if "_stage2" in name:
            replaceInFile(name, modId + name[:-5], ofVanilla(name) + "_bottom")
            tallMiddleStages.append(name.replace("_stage2", "_stage1"))

    for name in os.listdir(getDirectory()):
        if not "_stage1" in name or name in tallMiddleStages: continue
        replaceInFile(name, modId + name[:-5], ofVanilla(name))

    return
if __name__ == "__main__": main()