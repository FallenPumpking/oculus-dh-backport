"""Create a binary, full corresponding source and portable tester bundle."""
from pathlib import Path
import hashlib
import json
import re
import shutil
import subprocess
import tomllib
import zipfile

ROOT = Path(__file__).resolve().parents[1]
version = re.search(r'^\s*mod_version\s*=\s*(\S+)', (ROOT/'gradle.properties').read_text(), re.M)[1]
name = f'oculus-mc1.18.2-{version}'
jar = ROOT/'build/libs'/f'{name}.jar'
required = {'LICENSE', 'NOTICE.md', 'THIRD_PARTY_NOTICES.md', 'META-INF/mods.toml',
            'META-INF/licenses/AGPL-3.0.txt', 'META-INF/licenses/GPL-3.0.txt',
            'META-INF/licenses/LGPL-3.0.txt', 'META-INF/licenses/Apache-2.0.txt',
            'META-INF/licenses/BSD-3-Clause.txt', 'META-INF/licenses/JOML-MIT.txt',
            'META-INF/licenses/Commons-Collections-NOTICE.txt'}
with zipfile.ZipFile(jar) as z:
    missing = required - set(z.namelist())
    if missing:
        raise SystemExit('Missing notices: ' + repr(missing))
    metadata = tomllib.loads(z.read('META-INF/mods.toml').decode())
    assert metadata['mods'][0]['version'] == version
    assert metadata['license'].startswith('AGPL-3.0-only')
    assert z.testzip() is None

out = ROOT/'dist'; out.mkdir(exist_ok=True)
binary = out/jar.name; shutil.copy2(jar, binary)
files = subprocess.check_output(['git', 'ls-files', '-z'], cwd=ROOT).decode().split('\0')
source = out/f'{name}-source.zip'
with zipfile.ZipFile(source, 'w', zipfile.ZIP_DEFLATED) as z:
    for filename in filter(None, files):
        p = ROOT/filename
        if p.is_file():
            z.write(p, f'{name}-source/{filename}')
with zipfile.ZipFile(source) as z:
    assert z.testzip() is None
    for p in ['build.gradle','gradlew','gradle/wrapper/gradle-wrapper.jar','scripts/fetch-dependencies.py','deps/lock.json','NOTICE.md']:
        assert f'{name}-source/{p}' in z.namelist(), p
instructions = f'''Oculus DH Backport {version} — alpha tester bundle

Install only {jar.name} into your copied instance's mods directory.
Remove/disable the existing Oculus JAR. Never install the source ZIP as a mod.
Requires Minecraft 1.18.2, Forge 40.3.11 and Embeddium 0.3.18.
For the advertised shader setup install Distant Horizons 3.3.3 separately.
Obtain a DH-compatible shader separately; Complementary Reimagined r5.3 was tested.
Disable Euphoria Patcher and Far Sight for this alpha's test configuration.
Do not combine with OptiFine, Iris, or another Oculus JAR.

Share this complete bundle, or provide the exact GitHub release/source link with
the JAR. Licensing notices and complete corresponding source are included.
Report results: https://github.com/FallenPumpking/oculus-dh-backport/issues
Source/releases: https://github.com/FallenPumpking/oculus-dh-backport/releases
This is an unofficial alpha; see docs/TESTING.md in the source for test limits.
'''
bundle=out/f'{name}-tester-bundle.zip'
with zipfile.ZipFile(bundle,'w',zipfile.ZIP_DEFLATED) as z:
    z.write(binary,binary.name);z.write(source,source.name)
    z.writestr('START-HERE.txt',instructions)
    for p in [ROOT/'LICENSE',ROOT/'NOTICE.md',ROOT/'THIRD_PARTY_NOTICES.md',*sorted((ROOT/'LICENSES').glob('*'))]:
        z.write(p,p.relative_to(ROOT))
with zipfile.ZipFile(bundle) as z: assert z.testzip() is None
commit=subprocess.check_output(['git','rev-parse','HEAD'],cwd=ROOT).decode().strip()
dirty=bool(subprocess.check_output(['git','status','--porcelain'],cwd=ROOT).strip())
manifest={'version':version,'source_commit':commit,'working_tree_dirty':dirty,
          'artifacts':{p.name:hashlib.sha256(p.read_bytes()).hexdigest() for p in [binary,source,bundle]}}
(out/f'{name}-manifest.json').write_text(json.dumps(manifest,indent=2)+'\n')
(out/'SHA256SUMS.txt').write_text(''.join(f'{h}  {n}\n' for n,h in manifest['artifacts'].items()))
print(json.dumps(manifest,indent=2))
if dirty: print('WARNING: working tree is dirty; commit and repackage before publishing.')
