// CatalogNode.cxx
// Assignment 3
//
// Matthias Eder
// CSCI 2270

#include <iostream>
#include <string>
#include <algorithm>
#include "CatalogNode.h"
using namespace std;


CatalogItem::CatalogItem()
{
    setId("");
    setTitle("");
    setAuthor("");
}


CatalogItem::CatalogItem(const CatalogItem& src)
{
    *this = src;
}


CatalogItem::CatalogItem(const std::string& id, const std::string& title, const std::string& author)
{
    setId(id);
    setTitle(title);
    setAuthor(author);
}


void CatalogItem::operator = (const CatalogItem& src)
{
    // Prevent self-assignment.
    //
    if (this == &src)
	return;

    this->setId(src.getId());
    this->setTitle(src.getTitle());
    this->setAuthor(src.getAuthor());
}


bool operator < (const CatalogItem& lhs, const CatalogItem& rhs)
{
    string lhsId = lhs.getId();
    std::transform(lhsId.begin(), lhsId.end(), lhsId.begin(),static_cast<int(*)(int)>(std::toupper));

    string rhsId = rhs.getId();
    std::transform(rhsId.begin(), rhsId.end(), rhsId.begin(), static_cast<int(*)(int)>(std::toupper));

    return lhsId < rhsId;
}


ostream& operator << (ostream& out, const CatalogItem& item)
{
    out << item.getTitle() << "   " << item.getAuthor() << "   " << item.getId();
    return out;
}







CatalogNode::CatalogNode()
{
    m_data = CatalogItem();
    next = NULL;
    prev = NULL;
}


CatalogNode::CatalogNode(const CatalogItem& item, CatalogNode* n, CatalogNode* p)
{
    m_data = item;
    next = n;
    prev = p;
}


size_t CatalogNode::rsize()
{
    if (next == NULL)
    {
	return 1;
    }
    else
    {
	return 1 + next->rsize();
    }
}


ostream& operator << (ostream& out, const CatalogNode& node)
{
    out << node.data() << endl;
    return out;
}
