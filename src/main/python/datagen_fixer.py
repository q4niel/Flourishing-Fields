import os

def main() -> None:
    os.chdir(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

    flowerBlockModel()

    return

def flowerBlockModel() -> None:
    dir = "generated/assets/flourishing_fields/models/block"
    for filename in os.listdir(dir):
        path = os.path.join(dir, filename)
        if not os.path.isfile(path): continue

        with open(path, "r+") as file:
            content = file.read()
            file.seek(0)

            content = content.replace("crop", "cross")
            if ("_stage1" in path):
                content = content.replace("flourishing_fields", "minecraft")
                content = content.replace("_stage1", "")

            file.write(content)
            file.truncate()
    return

if __name__ == "__main__": main()