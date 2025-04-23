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
            content = content.replace("cube_all", "cross")
            content = content.replace("all", "cross")

            file.write(content)
            file.truncate()
    return

if __name__ == "__main__": main()