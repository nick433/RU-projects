files = main.c sorted-list.c
exec = sl #the executable

build: $(files)
	gcc -c  $(files)
	ar -cvq libsl.a *.o
	gcc $(files) -o $(exec)

clean:
	rm -f $(exec) *.o
