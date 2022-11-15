from random import randint

lst = []

print(1)

jumlah_mesin = 20
jumlah_perintah = 30

print(jumlah_mesin, end=" ")

for i in range(jumlah_mesin):
    bilangan_random = randint(0, jumlah_mesin)
    lst.append(bilangan_random)
    print(bilangan_random, end=" ")
print()
lst.sort()

jumlah_perintah = 30
print(jumlah_perintah)

count = 1

hasil = []

for i in range(jumlah_perintah):
    count = 0
    lower = randint(0, jumlah_mesin)
    upper = randint(lower, jumlah_mesin)
    print(f"LIHAT {lower} {upper}")
    
    for element in lst:
        if lower <= element <= upper:
            count += 1
    hasil.append(count)

print()
print(f"list = {lst}")
[print(i) for i in hasil]