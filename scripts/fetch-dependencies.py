"""Fetch official compile-only dependencies and verify their locked SHA-256."""
from pathlib import Path
import hashlib
import json
import urllib.request

ROOT = Path(__file__).resolve().parents[1]
for item in json.loads((ROOT / 'deps/lock.json').read_text()):
    target = ROOT / 'deps' / item['name']
    if target.exists() and hashlib.sha256(target.read_bytes()).hexdigest() == item['sha256']:
        print('Verified cached', target.name)
        continue
    request = urllib.request.Request(item['url'], headers={'User-Agent': 'Oculus-DH-Backport-build/1'})
    with urllib.request.urlopen(request, timeout=120) as response:
        data = response.read()
    if hashlib.sha256(data).hexdigest() != item['sha256']:
        raise SystemExit('Checksum mismatch: ' + target.name)
    temporary = target.with_suffix('.tmp')
    temporary.write_bytes(data)
    temporary.replace(target)
    print('Downloaded and verified', target.name)
