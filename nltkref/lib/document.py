import urllib2
from lib import text, html
from keywords import find_keywords


class Document:

  @staticmethod
  def from_file(fn):
    with open(fn, 'r') as f:
      text = f.read()
    return Document(text)

  
  @staticmethod
  def from_html(content, xpath=None):
    text = html.clean(content, xpath)
    return Document(text)


  @staticmethod
  def from_url(url, xpath=None):
    response = urllib2.urlopen(url)
    content = response.read()
    text = html.clean(content, xpath)
    return Document(text)


  def __init__(self, content):
    self._content = unicode(text.clean(content))


  def keywords(self):
    return find_keywords(self._content)
