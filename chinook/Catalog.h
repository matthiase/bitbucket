// Catalog.h
// Assignment 3
//
// Matthias Eder
// CSCI 2270




#ifndef ARRAY_H
#define ARRAY_H

#include <cstdlib>

template <class Item>
class Array
{
    
 public:
    //
    // TYPEDEFS AND CONSTANTS
    //
    static const int CAPACITY_MULTIPLIER = 2;
    static const size_t DEFAULT_CAPACITY = 200;

    //
    // CONSTRUCTORS AND DESTRUCTOR
    //
    Array();
    Array(size_t capacity);
    Array(const Array& src);
    ~Array();
    //
    // PUBLIC MEMBERS
    //    
    size_t getCapacity() const { return m_capacity; }
    size_t getSize() const { return m_size; }
    void sort();
    void clear();
    void resize(size_t capacity);
    void operator += (const Item& item);
    void operator = (const Array& src);
    Item* begin() { return m_items; }
    Item* end() { return m_items + m_size; }
    
    
 private:
    //
    // PRIVATE MEMBERS 
    //
    size_t m_capacity;
    size_t m_size;
    Item* m_items;
};

#endif




#ifndef CATALOG_H
#define CATALOG_H

#include <cstdlib>
#include <iostream>
#include <string>
#include "CatalogNode.h"

class Catalog
{

 public:
    Catalog();
    Catalog(const Catalog& src);
    ~Catalog();

    CatalogNode* head() const { return m_head; }
    void insert(const CatalogItem& item);
    CatalogItem itemAt(const size_t index);
    int indexOf(const CatalogItem& item);
    bool isEmpty() const { return (m_head == NULL); };
    void clear();
    size_t size();
    size_t read(const std::string& path);
    void print(size_t pageSize);
    Catalog searchAuthors(const std::string& str);
    Catalog searchTitles(const std::string& str);
    Catalog getRange(const std::string& callno, const size_t size); 

    void operator = (const Catalog& src);
    friend std::ostream& operator << (std::ostream& out, Catalog& src);

 private:
    CatalogNode* m_head;
    CatalogNode* findNode(const std::string& callno);

};

#endif




template <class Item>
const size_t Array<Item>::DEFAULT_CAPACITY;

template <class Item>
const int Array<Item>::CAPACITY_MULTIPLIER;


template <class Item>
Array<Item>::Array()
{
    m_capacity = DEFAULT_CAPACITY;
    m_size = 0;
    m_items = new Item[getCapacity()];
}



template <class Item>
Array<Item>::Array(size_t capacity)
{
    m_capacity = capacity;
    m_size = 0;
    m_items = new Item[getCapacity()];
}



template <class Item>
Array<Item>::Array(const Array<Item>& src)
{
    // Provide default values for the members.  Strictly speaking this is only necessary
    // for the items array.  If the items pointer is not allocated, the delete[] call in
    // the assignment operator will fail.
    
    m_capacity = DEFAULT_CAPACITY;
    m_size = 0;
    m_items = new Item[getCapacity()];
    
    // Now use the assignment operator to copy the values of the canvas argument to this
    // object.
    
    *this = src;
}



template <class Item>
Array<Item>::~Array()
{
    delete[] m_items;
}



template <class Item>
void Array<Item>::operator += (const Item& item)
{
    // Check if the array is full and resize it if necessary.
    if (m_size == m_capacity)
    {
	resize(m_capacity * CAPACITY_MULTIPLIER);
    }
    
    // Copy the new item into the array and increment the size.
    std::copy(&item, &item + 1, m_items + m_size++);
}




template <class Item>
void Array<Item>::operator = (const Array& src)
{
    // Prevent self-assignment.
    if (this == &src)
	return;
    
    // Free the memory occupied by the items array.  This step prevents a memory leak
    // by disallocating heap memory, before the items pointer is reassigned to point
    // to another array.
    
    delete[] m_items;
    
    // Copy all of the members of the argument to this canvas object.
    m_items = new Item[src.getCapacity()];
    std::copy(src.m_items, src.m_items + src.getSize(), this->m_items);
    this->m_capacity = src.getCapacity();
    this->m_size = src.getSize();
}



template <class Item>
void Array<Item>::clear()
{
    delete[] m_items;
    m_capacity = DEFAULT_CAPACITY;
    m_size = 0;
    
    m_items = new Item[DEFAULT_CAPACITY];
}



template <class Item>
void Array<Item>::sort()
{
    if (getSize() > 0)
    {
	std::sort(m_items, m_items + getSize());
    }
}



template <class Item>
void Array<Item>::resize(const size_t capacity)
{
    // Ensure that the capacity is valid (i.e. it is not equal to the current capacity
    // and it is not smaller that the size.
    //
    if (m_capacity == capacity)
	return;
    
    if (capacity < m_size)
	m_capacity = m_size;
    
    // Create and allocate an array on the heap that can hold the new capacity.  Copy
    // the items from the old (smaller) array into the new (larger) array.
    //
    Item* newItems = new Item[capacity];
    std::copy(m_items, m_items + m_size, newItems);
    
    // Free the memory occupied by the old array and set the items pointer to point to
    // the new array. Finally, adjust the capacity field.
    //
    delete[] m_items;
    m_items = newItems;
    m_capacity = capacity;
}
