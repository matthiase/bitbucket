// main.cxx
// Assignment 3
//
// Matthias Eder
// CSCI 2270

#include <iostream>
#include <cstdlib>
#include "Catalog.h"

using namespace std;


char getAction();
string ltrim(const string& str);

int main (int argc, char** argv)
{
    char action = 0;
    string str;
    size_t index;
    Catalog catalog;
    Catalog results;
    Catalog previous;
    Catalog browser;
    CatalogItem bookmark;

    do {
	action = getAction();
	switch(action) {

	    case 'F':
		cin >> str;

		if (catalog.isEmpty() == false)
		    catalog.clear();

 		if (results.isEmpty() == false)
		    results.clear();

		if (browser.isEmpty() == false)
		    browser.clear();

		catalog.read(str);
		cerr << "Imported " << catalog.size() << " from records \"" << str << "\"" << endl;		
		str.clear();
		break;


	    case 'N':
		if (results.isEmpty() == false)
		    results.clear();

		if (browser.isEmpty() == false)
		    browser.clear();

		break;


	    case 'R':
		cerr << "\n\nSEARCH RESULTS..." << endl;

		if (browser.isEmpty() == false)
		    browser.clear();

		if (results.isEmpty())
		{
		    cerr << "No results found." << endl;
		}
		else
		{
		    //cout << results << endl;
		    results.print(20);
		}
		break;


	    case 'A':
		getline(cin, str);
		str = ltrim(str);

		if (browser.isEmpty() == false)
		    browser.clear();

		if (results.isEmpty())
		{
		    results = catalog.searchAuthors(str);
		}
		else
		{
		    previous.clear();
		    previous = results;
		    results = results.searchAuthors(str);
		}

		cerr << "\nFound " << results.size() << " records matching \"" << str << "\"" <<  endl;
		break;


	    case 'T':
		getline(cin, str);
		str = ltrim(str);

		if (browser.isEmpty() == false)
		    browser.clear();

		if (results.isEmpty())
		{
		    results = catalog.searchTitles(str);
		}
		else
		{
		    previous.clear();
		    previous = results;
		    results = results.searchTitles(str);
		}

		cerr << "\nFound " << results.size() << " records matching \"" << str << "\"" <<  endl;
		break;

		
	    case 'U':
		if (browser.isEmpty() == false)
		    browser.clear();

		results = previous;
		previous.clear();
		cerr << "\n\nReverted to previous results (" << results.size() << " records)" << endl;
		break;


	    case 'B':
		cin >> index;
		bookmark = results.itemAt(index - 1); // subtract 1 to turn into a zero based index
		browser = catalog.getRange(bookmark.getId(), 10);
		cerr << "\n\nBROWSING..." << endl << browser << endl;
		break;

		
	    case '+':
		if (browser.isEmpty() == false)
		{
		    index = catalog.indexOf(bookmark);
		    cerr << "Index: " << index << endl;
		    if (index >= 0 && index < catalog.size() - 1)
		    {
			bookmark = catalog.itemAt(index + 1);
			browser = catalog.getRange(bookmark.getId(), 10);
		    }
		    else
		    {
			cerr << "\nReached end of list." << endl;
		    }
		    cerr << "\n\nBROWSING..." << endl << browser << endl;		    
		}
		else
		{
		    cerr << "\nThis function may only be used when browsing." << endl;
		}

		break;


	    case '-':
		if (browser.isEmpty() == false)
		{
		    index = catalog.indexOf(bookmark);
		    cerr << "Index: " << index << endl;
		    if (index > 0 && index < catalog.size())
		    {
			bookmark = catalog.itemAt(index - 1);
			browser = catalog.getRange(bookmark.getId(), 10);
		    }
		    else
		    {
			cerr << "\nReached beginning of list." << endl;
		    }
		    cerr << "\n\nBROWSING..." << endl << browser << endl;		    			
		}
		else
		{
		    cerr << "\nThis function may only be used when browsing." << endl;
		}
		
		break;


	    case 'Q':
		cerr << "\nGood Bye" << endl;
		break;


	    default:
		cerr << "INVALID INPUT." << endl;
		cin.clear();		    
		cin.ignore(INT_MAX, '\n');
		break;
	}
    } while (action != 'Q');

	return EXIT_SUCCESS;
} 





char getAction()
{
    char action = 0;

    cerr << endl << "Please select one of the following:" << endl 
	 << " f: read catalog from a file" << endl
	 << " n: start new search" << endl
	 << " r: show search results" << endl
	 << " a: search for substring in author" << endl
	 << " t: search for substring in title" << endl
	 << " u: revert to previous results" << endl
	 << " b: browse result" << endl
	 << " +: move browser window one book to the right" << endl
	 << " -: move browser window one book to the left" << endl
	 << " q: exit" << endl;

    cin >> action;
    
    if (isalpha(action)) {
	if (islower(action)) {
	    action = toupper(action);
	}
    }

    return action;
}



string ltrim(const string& str)
{
    size_t size = 0;
    const char* cstr;
    string sret;

    cstr = str.c_str();
    while (cstr[size] != '\n')
    {
	if (cstr[size] != ' ')
	    break;

	++size;
    }

    if (size > 0)
	sret = str.substr(size, str.size() - size);
    else
	sret = str;

    return sret;
}
