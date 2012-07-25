# File: Makefile
# Assignment 3
#
# CSCI 2270
# Matthias Eder
#
main: main.o
	g++ -o main main.o Catalog.o CatalogNode.o 
#
main.o: main.cxx Catalog.o
	g++ -g -c -Wall main.cxx
#
Catalog.o: Catalog.h Catalog.cxx CatalogNode.o 
	g++ -g -c -Wall Catalog.cxx
#
CatalogNode.o: CatalogNode.h CatalogNode.cxx
	g++ -g -c -Wall CatalogNode.cxx
#
run: main
	./main
#
clean:
	rm -f *.o *~ main
