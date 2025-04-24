import os

def modelAsCross(content:str) -> str:
    content = content.replace("crop", "cross")
    content = content.replace("cube_all", "cross")
    content = content.replace("all", "cross")
    return content

def stage0AsSprout(content:str, filename:str) -> str:
    filename = filename[:-5]
    if not "_stage0" in filename: return content
    return content.replace(filename, "flower_sprout")

if __name__ == "__main__":
    os.chdir(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

    dir = "generated/assets/flourishing_fields/models/block"
    for filename in os.listdir(dir):
        fullPath = os.path.join(dir, filename)
        if not os.path.isfile(fullPath): continue

        with open(fullPath, "r+") as file:
            content = file.read()
            file.seek(0)

            content = modelAsCross(content)
            content = stage0AsSprout(content, filename)

            file.write(content)
            file.truncate()