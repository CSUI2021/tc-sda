import toml
from pathlib import Path

data = {
    "language": "python",
    "only_stdout": True,
}


def conv(base_dir: Path, inspect=False):
    inp_dir = base_dir / "in"
    out_dir = base_dir / "out"

    total = len(list(inp_dir.iterdir()))
    for i in range(total):
        fname = f"{i}.txt"
        inp_f = open(inp_dir / fname, "r")
        out_f = open(out_dir / fname, "r")

        data[fname + (" with Inspect" if inspect else "")] = {  # type: ignore
            "input": inp_f.read(),
            "output": out_f.read(),
            "subset": False,
        }

        inp_f.close()
        out_f.close()


conv(Path("tc"))
conv(Path("tc-inspect"), True)

with open("tc-sda-e.toml", "w") as f:
    toml.dump(data, f)
