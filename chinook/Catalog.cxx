// Catalog.cxx
// Assignment 3
//
// Matthias Eder
// CSCI 2270


#include <cstdlib>
#include <fstream>
#include <algorithm>
#include "Catalog.h"
using namespace std;


Catalog::Catalog()
{
    m_head = NULL;
}



Catalog::Catalog(const Catalog& src)
{
    *this = src;
}



Catalog::~Catalog()
{
    clear();
}



size_t Catalog::size()
{
    if (isEmpty())
    {
	return 0;
    }
    else
    {
	return this->head()->rsize();
    }
}



void Catalog::insert(const CatalogItem& item)
{
    CatalogNode* node = new CatalogNode(item);
    if (m_head == NULL)
    {
	m_head = node;
    }
    else
    {
	node->next = m_head;
	m_head->prev = node;
	m_head = node;
    }

}



CatalogItem Catalog::itemAt(const size_t index)
{
    CatalogNode* node = head();
    CatalogItem item;
    size_t current = 0;

    if (index >= 0 || index < this->size())
    {
	for (current = 0; current < index; ++current)
	{
	    node = node->next;
	}
	
	if (node)
	{
	    item = node->data();
	}
    }

    return item;
}



int Catalog::indexOf(const CatalogItem& item)
{
    int index = 0;
    string callno = item.getId();
    CatalogNode* current = NULL;

    for(current = this->head(); current != NULL; current = current->next)
    {
	if (current->data().getId().find(callno) != string::npos)
	{
	    return index;
	}
	++index;
    }

    return -1;
}



void Catalog::clear()
{
    CatalogNode* current;

    while (m_head != NULL)
    {
	current = m_head;
	m_head = m_head->next;
	delete current;
    }
}



CatalogNode* Catalog::findNode(const string& callno)
{
    CatalogNode* current = NULL;
    for(current = this->head(); current != NULL; current = current->next)
    {
	if (current->data().getId().find(callno) != string::npos)
	{
	    return current;
	}
    }

    // no matching node found
    return NULL;
}



Catalog Catalog::getRange(const string& callno, const size_t size)
{
    Catalog range;
    CatalogNode* current = NULL;
    size_t index;
    size_t length;

    current = findNode(callno);
    
    if (current)
    {
	// find the head pointer for the sub-list
	length = (size/2);
	for(index = 0; index < length; ++index)
	{
	    if (current->prev)
	    {
		current = current->prev;
	    }
	    else
	    {
		break;
	    }
	}


	// Now insert all nodes from the sub-list head to the node at size + 1.
	// This creates a new list with the target node in the center.
	length = index + (size/2) + 1;
	for (index = 0; index < length; ++index)
	{
	    if (current->next)
	    {
		range.insert(current->data());
		current = current->next;
	    }
	    else
	    {
		break;
	    }
	}
    }

    return range;
    
}



// convert all strings to upper case to make search case-insensitive
Catalog Catalog::searchAuthors(const string& str)
{
    Catalog results;
    CatalogNode* current;
    string search(str);
    string author;

    std::transform(search.begin(), search.end(), search.begin(), static_cast<int(*)(int)>(std::toupper));

    for(current = this->head(); current != NULL; current = current->next)
    {
	author = current->data().getAuthor();
	std::transform(author.begin(), author.end(), author.begin(), static_cast<int(*)(int)>(std::toupper));	

	if (author.find(search) != string::npos)
	{
	    results.insert(current->data());
	}
    }

    return results;
}



// convert all strings to upper case to make search case-insensitive
Catalog Catalog::searchTitles(const string& str)
{
    Catalog results;
    CatalogNode* current;
    string search(str);
    string title;

    std::transform(search.begin(), search.end(), search.begin(), static_cast<int(*)(int)>(std::toupper));

    for(current = this->head(); current != NULL; current = current->next)
    {
	title = current->data().getTitle();
	std::transform(title.begin(), title.end(), title.begin(), static_cast<int(*)(int)>(std::toupper));	

	if (title.find(search) != string::npos)
	{
	    results.insert(current->data());
	}
    }

    return results;
}



size_t Catalog::read(const string& path)
{
    Array<CatalogItem> items;

    size_t count = 0;
    fstream in;
    int cursor = 0;

    string buffer;
    string author;
    string title;
    string callno;

    in.open(path.c_str());
		
    if (in)
    {
	while (in.peek () != EOF)
	{
	    getline(in, buffer);

	    // parse the input string for the author name
	    cursor = buffer.find('"', 1);
	    author = cursor > 0 ? buffer.substr(1, cursor - 1) : "";

	    // discard the used part of the string and parse the title
	    buffer = buffer.erase(0, buffer.find('"', ++cursor));
	    cursor = buffer.find('"', 1);
	    title = cursor > 0 ? buffer.substr(1, cursor - 1) : "";

	    // discard the used part of the string and parse the call number
	    buffer = buffer.erase(0, buffer.find('"', ++cursor));
	    cursor = buffer.find('"', 1);
	    callno = cursor > 0 ? buffer.substr(1, cursor - 1) : "";

	    items += CatalogItem(callno, title, author);
	    ++count;
	}
	
	in.close();	
	items.sort();
	
	// Since the nodes will be inserted at the head of the list, read them from
	// the array in reverse to preserve the sort order.
	CatalogItem* i = items.begin();
	CatalogItem* n = items.end();
	while(n != i)
	{	    
	    --n;
	    insert(*n);
	}	
    }	
    else
    {
	cerr << "UNABLE TO READ INPUT FILE: " << path << endl;
    }

    return count;
}



void Catalog::operator = (const Catalog& src)
{
    CatalogNode* current;

    if (this->isEmpty() == false)
    {
	this->clear();
    }

    for (current = src.head(); current != NULL; current = current->next)
    {
	this->insert(current->data());
    }
}



std::ostream& operator << (std::ostream& out, Catalog& src)
{
    CatalogNode* current;
    size_t count = 0;

    for (current = src.head(); current != NULL; current = current->next)
    {
        out << "(" << ++count << ") " << *current;
    }

    return out;    
}



void Catalog::print(size_t pageSize)
{
    char response;
    CatalogNode* current;
    size_t count = 1;

    for (current = this->head(); current != NULL; current = current->next)
    {
	if (count % (pageSize + 1) == 0)
	{
	    cerr << "\nPress CTRL+D (End-Of-Line) to see more results." << endl;
	    cin >> response;
	    cin.clear();
	}
	cerr << "(" << count++ << ") " << *current;	
    }
}
