// CatalogNode.h
// Assignment 3
//
// Matthias Eder
// CSCI 2270



#ifndef CATALOG_ITEM_H
#define CATALOG_ITEM_H

#include <string>

class CatalogItem 
{
 public:
    //
    // CONSTRUCTORS
    //
    CatalogItem();
    CatalogItem(const CatalogItem& src);
    CatalogItem(const std::string& id, const std::string& title, const std::string& author);
    //
    // ACCESSORS/MUTATORS
    //
    std::string getId() const { return m_id; }
    void setId(std::string id) { m_id = id; }
    std::string getTitle() const { return m_title; }
    void setTitle(std::string title) { m_title = title; }
    std::string getAuthor() const { return m_author; }
    void setAuthor(std::string author) { m_author = author; }
    //
    // OPERATORS
    //
    void operator = (const CatalogItem& src);

 private:
    std::string m_id;
    std::string m_title;
    std::string m_author;

    friend bool operator < (const CatalogItem& lhs, const CatalogItem& rhs);
    friend std::ostream& operator << (std::ostream& out, const CatalogItem& item);

};

#endif




#ifndef CATALOG_NODE_H
#define CATALOG_NODE_H


class CatalogNode
{

 public:
    CatalogNode();
    CatalogNode(const CatalogItem& item, CatalogNode* n = NULL, CatalogNode* p = NULL);
    CatalogItem data() const { return m_data; }
//    CatalogNode* next() { return m_next; };
//    CatalogNode* prev() { return m_prev; };    
    CatalogNode* next;
    CatalogNode* prev;
    size_t rsize();

 private:
    CatalogItem m_data;


    friend std::ostream& operator << (std::ostream& out, const CatalogNode& node);

};

#endif
