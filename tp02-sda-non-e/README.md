# hapus.py dan lihat.py
- File `hapus.py` berfungsi untuk menghasilkan query HAPUS saja dan menghasilkan output yang sesuai (brute force). 
- File `lihat.py` berfungsi untuk menghasilkan query LIHAT saja dan menghasilkan output yang sesuai (brute force).

> Batasan dapat langsung diubah pada file tersebut

# Membandingkan hasil
hasil dapat dibandingkan dengan memanfaatkan VSCode
```
C:.
├───.vscode
├───bin
├───lib
├───output
├───src
├───hapus.py
├───lihat.py
└───tc
    ├───in
    └───out
```

Lakukan hal berikut pada terminal VSCode.
```
py .\hapus.py > hapus-in.txt; cat .\hapus-in.txt | java .\src\TP2.java > hapus-out.txt; code -d .\hapus-in.txt .\hapus-out.txt
```

> Perintah di atas dilakukan pada sistem operasi windows. Untuk sistem operasi lain, ya cari sendiri caranya...