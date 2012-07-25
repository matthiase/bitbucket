#from lxml import etree, html
import lxml.etree
import lxml.html

def clean(content, xpath=None):
  text = ''
  document = lxml.etree.HTML(content)
  if xpath:
    elements = document.xpath(xpath)
    if elements:
      fragment = "\n".join(map(lambda el: lxml.etree.tostring(el, method="html", pretty_print=True), elements))
      text = lxml.html.fromstring(fragment).text_content()
  else:
    text = lxml.html.fromstring(content).text_content()
  return text
  

