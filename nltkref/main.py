
from lib import Document

if __name__ == '__main__':
  document = Document.from_file('data/article.txt')
  print document.keywords()

  with open('data/index.html', 'r') as f:
   data =  f.read()
  document = Document.from_html(data, "//*[@id='LS-78']")
  print document.keywords()

  document = Document.from_url('http://www.gardening-experts.com/grow-okra/', "//*[contains(@class,'entry-content')]/p")
  print document.keywords() 
